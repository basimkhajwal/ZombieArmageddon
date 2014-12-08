package net.net63.codearcade.ZombieArmageddon.systems;

import net.net63.codearcade.ZombieArmageddon.components.AnimationComponent;
import net.net63.codearcade.ZombieArmageddon.components.BoundsComponent;
import net.net63.codearcade.ZombieArmageddon.components.BulletComponent;
import net.net63.codearcade.ZombieArmageddon.components.PhysicsComponent;
import net.net63.codearcade.ZombieArmageddon.components.PlayerComponent;
import net.net63.codearcade.ZombieArmageddon.components.PositionComponent;
import net.net63.codearcade.ZombieArmageddon.components.RemoveComponent;
import net.net63.codearcade.ZombieArmageddon.components.RenderComponent;
import net.net63.codearcade.ZombieArmageddon.components.StateComponent;
import net.net63.codearcade.ZombieArmageddon.utils.Assets;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class PlayerMovementSystem extends IteratingSystem {

	private ComponentMapper<PositionComponent> positionMapper;
	private ComponentMapper<PhysicsComponent> physicsMapper;
	private ComponentMapper<StateComponent> stateMapper;
	private ComponentMapper<PlayerComponent> playerMapper;
	
	private Engine engine;
	
	private float bulletTime, reloadTime;
	private int bulletsToRemove;
	
	@SuppressWarnings("unchecked")
	public PlayerMovementSystem() {
		super(Family.getFor(PlayerComponent.class), Constants.SYSTEM_PRIORITIES.MOVEMENT);

		positionMapper = ComponentMapper.getFor(PositionComponent.class);
		physicsMapper = ComponentMapper.getFor(PhysicsComponent.class);
		stateMapper = ComponentMapper.getFor(StateComponent.class);
		playerMapper = ComponentMapper.getFor(PlayerComponent.class);
		
		bulletTime = 0;
		bulletsToRemove = 0;
		reloadTime = 0;
	}
	
	@Override
	public void addedToEngine(Engine engine){
		super.addedToEngine(engine);
		
		this.engine = engine;
	}

	@Override
	public void processEntity(Entity player, float deltaTime) {
		StateComponent state = stateMapper.get(player);
		Vector2 velocity = physicsMapper.get(player).velocity;
		// Update the game
		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
			velocity.y = Constants.PLAYER_SPEED;
			
			if(state.get() == PlayerComponent.STATE_LEFT){
				state.set(PlayerComponent.STATE_MOVING_LEFT);
			}else if(state.get() == PlayerComponent.STATE_RIGHT){
				state.set(PlayerComponent.STATE_MOVING_RIGHT);
			}
			
		} else if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
			velocity.y = -1 * Constants.PLAYER_SPEED;
			
			if(state.get() == PlayerComponent.STATE_LEFT){
				state.set(PlayerComponent.STATE_MOVING_LEFT);
			}else if(state.get() == PlayerComponent.STATE_RIGHT){
				state.set(PlayerComponent.STATE_MOVING_RIGHT);
			}
		} else {
			velocity.y = 0;
		}

		if ( Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A) ) {
			velocity.x = -1 * Constants.PLAYER_SPEED;
			
			if(state.get() != PlayerComponent.STATE_MOVING_LEFT){
				state.set(PlayerComponent.STATE_MOVING_LEFT);
			}
			
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D) ) {
			velocity.x = Constants.PLAYER_SPEED;
			
			if(state.get() != PlayerComponent.STATE_MOVING_RIGHT){
				state.set(PlayerComponent.STATE_MOVING_RIGHT);
			}
			
		} else {
			velocity.x = 0;
			
			if(velocity.y == 0){
				if(state.get() == PlayerComponent.STATE_MOVING_RIGHT){
					state.set(PlayerComponent.STATE_RIGHT);
				}else if(state.get() == PlayerComponent.STATE_MOVING_LEFT){
					state.set(PlayerComponent.STATE_LEFT);
				}
			}
		}
		//Keep the velocity same, even when going diagonally
		velocity.clamp(Constants.PLAYER_SPEED, Constants.PLAYER_SPEED);

		// Keep player in bounds
		Vector2 pos = positionMapper.get(player).position;
		
		pos.x = Math.max(0, Math.min(pos.x, Constants.SCREEN_WIDTH - Constants.PLAYER_WIDTH));
		pos.y = Math.max(0, Math.min(pos.y, Constants.PLAYER_MAX_HEIGHT));
		
		
		//Check for bullet firing
		PlayerComponent pc = playerMapper.get(player);
		
		pc.numBullets -= bulletsToRemove;
		
		if((Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.justTouched()) && bulletTime > 0.1f && pc.numBullets < Constants.MAX_BULLETS){
			bulletTime = 0;
			pc.numBullets++;
			
			int direction = Constants.BULLET_SPEED;
			
			if(state.get() == PlayerComponent.STATE_LEFT || state.get() == PlayerComponent.STATE_MOVING_LEFT){
				direction *= -1;
			}
			Assets.shoot.play(1f);
			createBullet((int) pos.x + Constants.PLAYER_WIDTH / 2,(int) pos.y + 30, direction, Constants.BULLET_DAMAGE);
		}
		
		else if(reloadTime > 0.3f){
			pc.numBullets--;
			pc.numBullets = Math.max(0, pc.numBullets);
			reloadTime = 0;
		}
		
		bulletTime += deltaTime;
		reloadTime += deltaTime;
	}
	
	public void bulletRemoved(){
		bulletsToRemove++;
	}
	
	public Entity createBullet(int x, int y, int direction, int damage){
		Entity bullet = new Entity();
		
		BulletComponent bulletComponent = new BulletComponent();
		PositionComponent pos = new PositionComponent();
		RenderComponent render = new RenderComponent();
		StateComponent state = new StateComponent();
		AnimationComponent anim = new AnimationComponent();
		BoundsComponent bounds = new BoundsComponent();
		RemoveComponent remove = new RemoveComponent();
		PhysicsComponent physics = new PhysicsComponent();
		
		state.set(0);
		
		pos.position.set(x, y);
		
		physics.velocity.set(direction, 0);
		
		bounds.width = Constants.BULLET_WIDTH;
		bounds.height = Constants.BULLET_HEIGHT;
		
		anim.animations.put(0, new Animation(0.2f, Assets.bullet.getRegions()));
		
		render.texture = Assets.bullet.getRegions().first();
		render.z = Constants.BULLET_Z;
		
		bulletComponent.damage = damage;
		
		bullet.add(pos);
		bullet.add(render);
		bullet.add(state);
		bullet.add(anim);
		bullet.add(bounds);
		bullet.add(remove);
		bullet.add(physics);
		bullet.add(bulletComponent);
		
		engine.addEntity(bullet);
		
		return bullet;
	}

}
