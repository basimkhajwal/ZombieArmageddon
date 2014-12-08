package net.net63.codearcade.ZombieArmageddon.systems;

import net.net63.codearcade.ZombieArmageddon.components.BoundsComponent;
import net.net63.codearcade.ZombieArmageddon.components.BulletComponent;
import net.net63.codearcade.ZombieArmageddon.components.HealthComponent;
import net.net63.codearcade.ZombieArmageddon.components.MonsterComponent;
import net.net63.codearcade.ZombieArmageddon.components.PlayerComponent;
import net.net63.codearcade.ZombieArmageddon.components.PositionComponent;
import net.net63.codearcade.ZombieArmageddon.components.RemoveComponent;
import net.net63.codearcade.ZombieArmageddon.components.StateComponent;
import net.net63.codearcade.ZombieArmageddon.utils.Assets;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BulletCollisionSystem extends EntitySystem{
	
	private Family bulletFamily;
	private Family monsterFamily;
	private Family playerFamily;
	
	private ImmutableArray<Entity> bullets;
	private ImmutableArray<Entity> monsters;
	private ImmutableArray<Entity> players;
	
	private ComponentMapper<PositionComponent> positionMapper;
	private ComponentMapper<BoundsComponent> boundsMapper;
	private ComponentMapper<RemoveComponent> removingMapper;
	private ComponentMapper<BulletComponent> bulletMapper;
	private ComponentMapper<HealthComponent> healthMapper;
	
	@SuppressWarnings("unchecked")
	public BulletCollisionSystem(){
		super(Constants.SYSTEM_PRIORITIES.BULLET_COLLISION);
		
		bulletFamily = Family.getFor(BulletComponent.class);
		monsterFamily = Family.getFor(MonsterComponent.class);
		playerFamily = Family.getFor(PlayerComponent.class);
		
		positionMapper = ComponentMapper.getFor(PositionComponent.class);
		boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
		removingMapper = ComponentMapper.getFor(RemoveComponent.class);
		healthMapper = ComponentMapper.getFor(HealthComponent.class);
		bulletMapper = ComponentMapper.getFor(BulletComponent.class);
	}
	
	
	
	@Override
	public void addedToEngine(Engine engine){
		bullets = engine.getEntitiesFor(bulletFamily);
		monsters = engine.getEntitiesFor(monsterFamily);
		players = engine.getEntitiesFor(playerFamily);
	}
	
	private Rectangle getBounds(Entity entity){
		Vector2 pos = positionMapper.get(entity).position;
		BoundsComponent b = boundsMapper.get(entity);
		
		return new Rectangle(pos.x, pos.y, b.width, b.height);
	}
	
	@Override
	public void update(float deltaTime){
		Rectangle screen = new Rectangle(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		for(Object o: bullets.toArray(Entity.class)){
			Entity bullet = (Entity) o;
			Rectangle bounds = getBounds(bullet);
			
			if(!screen.contains(bounds)){
				removingMapper.get(bullet).remove = true;
				continue;
			}
			
			for(Object m: monsters.toArray(Entity.class)){
				Entity monster = (Entity) m;
				Rectangle mBounds = getBounds(monster);
				
				if(mBounds.overlaps(bounds)){
					healthMapper.get(monster).health -= bulletMapper.get(bullet).damage;
					Assets.zombieHurt.play(1f);
					removingMapper.get(bullet).remove = true;
					break;
				}
			}
			
			
		}
		
		Entity player = players.peek();
		
		Rectangle playerBoundsRectangle = getBounds(player);
		PlayerComponent delta = player.getComponent(PlayerComponent.class);
		for(Object m: monsters.toArray(Entity.class)){
			Entity monster = (Entity) m;
			
			Rectangle mBounds = getBounds(monster);
			
			if(mBounds.overlaps(playerBoundsRectangle) && delta.damageDelta > 0.7f){
				healthMapper.get(player).health -= 30;
				delta.damageDelta = 0;
				int state = monster.getComponent(StateComponent.class).get();
				
				int direction = 30;
				
				if(state == MonsterComponent.STATE_LEFT || state == MonsterComponent.STATE_MOVING_LEFT){
					direction *= -1;
				}
				
				positionMapper.get(player).position.add(direction, 0);
				Assets.hurt.play(1f);
				break;
			}
		}
		delta.damageDelta += deltaTime;
	}
}
