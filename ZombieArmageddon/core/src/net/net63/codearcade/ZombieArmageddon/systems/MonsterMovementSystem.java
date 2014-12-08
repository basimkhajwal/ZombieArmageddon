package net.net63.codearcade.ZombieArmageddon.systems;

import net.net63.codearcade.ZombieArmageddon.components.MonsterComponent;
import net.net63.codearcade.ZombieArmageddon.components.PhysicsComponent;
import net.net63.codearcade.ZombieArmageddon.components.PlayerComponent;
import net.net63.codearcade.ZombieArmageddon.components.PositionComponent;
import net.net63.codearcade.ZombieArmageddon.components.StateComponent;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

public class MonsterMovementSystem extends IteratingSystem{
	
	private Vector2 playerPosition;
	private Engine engine;
	
	private ComponentMapper<PositionComponent> positionMapper;
	private ComponentMapper<PhysicsComponent> physicsMapper;
	private ComponentMapper<StateComponent> stateMapper;
	
	@SuppressWarnings("unchecked")
	public MonsterMovementSystem(){
		super(Family.getFor(MonsterComponent.class, PositionComponent.class, PhysicsComponent.class), Constants.SYSTEM_PRIORITIES.MONSTER_MOVEMENT);
		
		positionMapper = ComponentMapper.getFor(PositionComponent.class);
		physicsMapper = ComponentMapper.getFor(PhysicsComponent.class);
		stateMapper = ComponentMapper.getFor(StateComponent.class);
	}
	
	@Override
	public void addedToEngine(Engine engine){
		super.addedToEngine(engine);
		
		this.engine = engine;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(float delta){
		playerPosition = positionMapper.get(engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).first()).position.cpy();
		
		super.update(delta);
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime){
		Vector2 position = positionMapper.get(entity).position.cpy();
		
		if(position.dst(playerPosition) > Constants.DISTANCE_THRESHHOLD){
			physicsMapper.get(entity).velocity = playerPosition.cpy().sub(position).nor().scl(Constants.MONSTER_SPEED);
		}else{
			physicsMapper.get(entity).velocity.setZero();
		}
		
		StateComponent state = stateMapper.get(entity);
		
		if(position.x + Constants.DISTANCE_THRESHHOLD < playerPosition.x){
			if(state.get() != MonsterComponent.STATE_MOVING_RIGHT){
				state.set(MonsterComponent.STATE_MOVING_RIGHT);
			}
		}else if(position.x - Constants.DISTANCE_THRESHHOLD > playerPosition.x){
			if(state.get() != MonsterComponent.STATE_MOVING_LEFT){
				state.set(MonsterComponent.STATE_MOVING_LEFT);
			}
		}else{
			if(state.get() == MonsterComponent.STATE_MOVING_RIGHT){
				state.set(MonsterComponent.STATE_RIGHT);
			}else if(state.get() == MonsterComponent.STATE_MOVING_LEFT){
				state.set(MonsterComponent.STATE_LEFT);
			}
		}
	}
	
}
