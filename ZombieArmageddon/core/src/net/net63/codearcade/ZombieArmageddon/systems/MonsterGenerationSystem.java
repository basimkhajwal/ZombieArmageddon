package net.net63.codearcade.ZombieArmageddon.systems;

import java.util.Random;

import net.net63.codearcade.ZombieArmageddon.components.AnimationComponent;
import net.net63.codearcade.ZombieArmageddon.components.BoundsComponent;
import net.net63.codearcade.ZombieArmageddon.components.HealthComponent;
import net.net63.codearcade.ZombieArmageddon.components.MonsterComponent;
import net.net63.codearcade.ZombieArmageddon.components.PhysicsComponent;
import net.net63.codearcade.ZombieArmageddon.components.PlayerComponent;
import net.net63.codearcade.ZombieArmageddon.components.PositionComponent;
import net.net63.codearcade.ZombieArmageddon.components.RemoveComponent;
import net.net63.codearcade.ZombieArmageddon.components.RenderComponent;
import net.net63.codearcade.ZombieArmageddon.components.StateComponent;
import net.net63.codearcade.ZombieArmageddon.utils.Assets;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class MonsterGenerationSystem extends EntitySystem{
	
	private Random random;
	private Engine engine;
	
	private int maxMonsters;
	private int numberKilled;
	private int numMonsters;
	
	private float delta;
	
	public MonsterGenerationSystem(){
		super(Constants.SYSTEM_PRIORITIES.MONSTER_GENERATION);
		
		random = new Random(System.currentTimeMillis());
		
		maxMonsters = Constants.MAX_MONSTERS;
		numMonsters = 0;
		numberKilled = 0;
		
		delta = 0;
	}
	
	@Override
	public void addedToEngine(Engine engine){
		this.engine = engine;
	}
	
	public void randomMonster(){
		int y = random.nextInt(Constants.PLAYER_MAX_HEIGHT);
		int x = random.nextBoolean() ? -Constants.MONSTER_WIDTH - 20: Constants.SCREEN_WIDTH + 20;
		
		createMonster(x, y);
		numMonsters++;
		Assets.zombie.play(1f);
		
		delta = 0;
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
		
		if(numMonsters < maxMonsters && delta > 0.4f && random.nextDouble() < Constants.MONSTER_CHANCE){
			randomMonster();
		} else if(numMonsters < maxMonsters && delta > 4f){
			randomMonster();
		}
		
		delta += deltaTime;
	}
	
	public void monsterKilled(){
		numMonsters--;
		numberKilled++;
		
		if(numberKilled > Constants.MONSTER_THRESHHOLD){
			numberKilled = 0;
			maxMonsters++;
			maxMonsters = Math.min(maxMonsters, Constants.MAX_FINAL_MONSTERS);
		}
	}
	
	public Entity createMonster(int x, int y){
		Entity monster = new Entity();
		
		MonsterComponent monsterComponent = new MonsterComponent();
		AnimationComponent animation = new AnimationComponent();
		RenderComponent render = new RenderComponent();
		StateComponent state = new StateComponent();
		PositionComponent pos = new PositionComponent();
		BoundsComponent bounds = new BoundsComponent();
		PhysicsComponent physics = new PhysicsComponent();
		HealthComponent health = new HealthComponent();
		RemoveComponent remove = new RemoveComponent();
		
		health.health = Constants.ZOMBIE_HEALTH;
		health.maxHealth = Constants.ZOMBIE_HEALTH;
		health.regeneration = Constants.ZOMBIE_REGEN;
		health.draw = true;
		
		state.set(MonsterComponent.STATE_LEFT);
		
		pos.position.set(x, y);
		
		bounds.width = Constants.MONSTER_WIDTH;
		bounds.height = Constants.MONSTER_HEIGHT;
		
		Array<TextureRegion> leftWalking = new Array<TextureRegion>();
		
		for(String s: Constants.LEFT_WALKING){
			leftWalking.add(Assets.monster.findRegion(s));
		}
		
		animation.animations.put(MonsterComponent.STATE_MOVING_LEFT, new Animation(0.2f, leftWalking));
		animation.animations.put(MonsterComponent.STATE_LEFT, new Animation(1f, leftWalking.first()));
		
		Array<TextureRegion> rightWalking = new Array<TextureRegion>();
		
		for(String s: Constants.RIGHT_WALKING){
			rightWalking.add(Assets.monster.findRegion(s));
		}
		
		animation.animations.put(PlayerComponent.STATE_MOVING_RIGHT, new Animation(0.2f, rightWalking));
		animation.animations.put(PlayerComponent.STATE_RIGHT, new Animation(1f, rightWalking.first()));
		
		render.texture = leftWalking.first();
		render.z = Constants.MONSTER_Z;
		
		monster.add(health);
		monster.add(remove);
		monster.add(animation);
		monster.add(monsterComponent);
		monster.add(state);
		monster.add(pos);
		monster.add(bounds);
		monster.add(render);
		monster.add(physics);
		
		engine.addEntity(monster);
		
		return monster;
	}
	
}
