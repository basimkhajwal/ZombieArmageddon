package net.net63.codearcade.ZombieArmageddon.systems;

import net.net63.codearcade.ZombieArmageddon.components.MonsterComponent;
import net.net63.codearcade.ZombieArmageddon.components.RemoveComponent;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class EntityRemovalSystem extends IteratingSystem{
	
	private ComponentMapper<RemoveComponent> removeMapper;
	private ComponentMapper<MonsterComponent> monsterMapper;
	//private ComponentMapper<BulletComponent> bulletMapper;
	
	private Engine engine;
	
	@SuppressWarnings("unchecked")
	public EntityRemovalSystem(){
		super(Family.getFor(RemoveComponent.class), Constants.SYSTEM_PRIORITIES.ENTITY_REMOVAL);
		
		removeMapper = ComponentMapper.getFor(RemoveComponent.class);
		monsterMapper = ComponentMapper.getFor(MonsterComponent.class);
		//bulletMapper = ComponentMapper.getFor(BulletComponent.class);
		
	}
	
	@Override
	public void addedToEngine(Engine engine){
		super.addedToEngine(engine);
		this.engine = engine;
		
	}
	
	@Override
	public void update(float deltaTime){
		if(getEntities() != null){
			super.update(deltaTime);
		}
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime){
		if(removeMapper.get(entity).remove){
			
			if(monsterMapper.has(entity)){
				engine.getSystem(MonsterGenerationSystem.class).monsterKilled();
			}
			
			/*if(bulletMapper.has(entity)){
				engine.getSystem(PlayerMovementSystem.class).bulletRemoved();
			}*/
			
			engine.removeEntity(entity);
		}
	}
	
}
