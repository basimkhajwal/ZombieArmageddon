package net.net63.codearcade.ZombieArmageddon.systems;

import java.util.Arrays;
import java.util.Comparator;

import net.net63.codearcade.ZombieArmageddon.components.PhysicsComponent;
import net.net63.codearcade.ZombieArmageddon.components.PositionComponent;
import net.net63.codearcade.ZombieArmageddon.components.RenderComponent;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class EntityPositioningSystem extends IteratingSystem{
	
	private ComponentMapper<RenderComponent> renderMapper;
	private ComponentMapper<PositionComponent> positionMapper;
	
	private Comparator<Entity> comparator;
	
	@SuppressWarnings("unchecked")
	public EntityPositioningSystem(){
		super(Family.getFor(PositionComponent.class, PhysicsComponent.class, RenderComponent.class), Constants.SYSTEM_PRIORITIES.ENTITY_POSITIONING);
		
		positionMapper = ComponentMapper.getFor(PositionComponent.class);
		renderMapper = ComponentMapper.getFor(RenderComponent.class);
		
		comparator = new Comparator<Entity>() {
			
			@Override
			public int compare(Entity a, Entity b) {
				return (int) Math.signum(positionMapper.get(b).position.y - positionMapper.get(a).position.y);
			}
		};
	}
	
	@Override
	public void update(float deltaTime){
		Entity[] entities = getEntities().toArray(Entity.class);
		
		Arrays.sort(entities, comparator);
		
		int Z = Constants.PLAYER_Z;
		
		for(Entity e: entities){
			renderMapper.get(e).z = Z;
			
			Z++;
		}
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime){
		
	}
	
}
