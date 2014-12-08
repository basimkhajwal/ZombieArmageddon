package net.net63.codearcade.ZombieArmageddon.systems;

import java.util.Arrays;
import java.util.Comparator;

import net.net63.codearcade.ZombieArmageddon.components.BoundsComponent;
import net.net63.codearcade.ZombieArmageddon.components.PositionComponent;
import net.net63.codearcade.ZombieArmageddon.components.RenderComponent;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class RenderingSystem extends EntitySystem{
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private ComponentMapper<PositionComponent> positionMapper;
	private ComponentMapper<BoundsComponent> boundsMapper;
	private ComponentMapper<RenderComponent> renderMapper;
	
	private ImmutableArray<Entity> entities;
	private Comparator<Entity> comparator;
	
	public RenderingSystem(OrthographicCamera camera){
		super(Constants.SYSTEM_PRIORITIES.RENDERING);
		
		this.camera = camera;
		batch = new SpriteBatch();
		
		positionMapper = ComponentMapper.getFor(PositionComponent.class);
		boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
		renderMapper = ComponentMapper.getFor(RenderComponent.class);
		
		//Comparator to put them in the right z-order
		comparator = new Comparator<Entity>() {
			
			@Override
			public int compare(Entity a, Entity b) {
				
				return (int) Math.signum(renderMapper.get(a).z - renderMapper.get(b).z);
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine){
		//Get entities that have a texture component and a render component and either a body or bounds component
		entities = engine.getEntitiesFor(Family.getFor(RenderComponent.class, PositionComponent.class, BoundsComponent.class));
	}
	
	@Override
	public void removedFromEngine(Engine engine){
		super.removedFromEngine(engine);
		//Clear all resources
		batch.dispose();
	}
	
	@Override
	public void update(float deltaTime){
		//Set the scaled projection matrix
		batch.setProjectionMatrix(camera.combined);
		
		//Get the entities as an array
		Entity[] sortedEntities = entities.toArray(Entity.class);
		
		//Sort them in z-order
		Arrays.sort(sortedEntities, comparator);
		
		batch.begin();
		
		//Iterate through all the entities and process them
		for(Entity e: sortedEntities){
			processEntity(e, deltaTime);
		}
		
		batch.end();
	}
	
	public void processEntity(Entity entity, float deltaTime){
		
		//Get the components of the entity
		TextureRegion texture = renderMapper.get(entity).texture;
		BoundsComponent bounds = boundsMapper.get(entity);
		Vector2 position = positionMapper.get(entity).position;
		
		batch.draw(texture, position.x, position.y, bounds.width, bounds.height);		
	}
}
