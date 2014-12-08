package net.net63.codearcade.ZombieArmageddon.systems;

import net.net63.codearcade.ZombieArmageddon.components.PhysicsComponent;
import net.net63.codearcade.ZombieArmageddon.components.PositionComponent;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class PhysicsSystem extends IteratingSystem{
	
	private ComponentMapper<PositionComponent> positionMapper;
	private ComponentMapper<PhysicsComponent> physicsMapper;
	
	@SuppressWarnings("unchecked")
	public PhysicsSystem(){
		super(Family.getFor(PositionComponent.class, PhysicsComponent.class), Constants.SYSTEM_PRIORITIES.PHSYICS);
		
		positionMapper = ComponentMapper.getFor(PositionComponent.class);
		physicsMapper = ComponentMapper.getFor(PhysicsComponent.class);
	}
	
	
	@Override
	public void processEntity(Entity entity, float deltaTime){
		PhysicsComponent physics = physicsMapper.get(entity);
		
		physics.velocity.add(physics.acceleration.cpy().scl(deltaTime));
		
		positionMapper.get(entity).position.add(physics.velocity.cpy().scl(deltaTime));
	}
}
