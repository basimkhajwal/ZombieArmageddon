package net.net63.codearcade.ZombieArmageddon.systems;

import net.net63.codearcade.ZombieArmageddon.components.BoundsComponent;
import net.net63.codearcade.ZombieArmageddon.components.HealthComponent;
import net.net63.codearcade.ZombieArmageddon.components.PlayerComponent;
import net.net63.codearcade.ZombieArmageddon.components.PositionComponent;
import net.net63.codearcade.ZombieArmageddon.components.RemoveComponent;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class HealthSystem extends IteratingSystem{
	
	private ComponentMapper<HealthComponent> healthMapper;
	private ComponentMapper<RemoveComponent> removeMapper;
	private ComponentMapper<BoundsComponent> boundMapper;
	private ComponentMapper<PositionComponent> positionMapper;
	private ComponentMapper<PlayerComponent> playerMapper;
	
	private Color redLeft, redRight;
	
	private ShapeRenderer renderer;
	private OrthographicCamera camera;
	
	private boolean gameOver;
	
	@SuppressWarnings("unchecked")
	public HealthSystem(OrthographicCamera camera){
		super(Family.getFor(HealthComponent.class, RemoveComponent.class), Constants.SYSTEM_PRIORITIES.HEALTH);
		
		healthMapper = ComponentMapper.getFor(HealthComponent.class);
		removeMapper = ComponentMapper.getFor(RemoveComponent.class);
		boundMapper = ComponentMapper.getFor(BoundsComponent.class);
		positionMapper = ComponentMapper.getFor(PositionComponent.class);
		playerMapper = ComponentMapper.getFor(PlayerComponent.class);
		
		this.camera = camera;
		renderer = new ShapeRenderer();
		
		redLeft = new Color(153f/255f, 0, 0, 1);
		redRight = new Color(0.8f, 0.2f, 0.2f, 1f);
		
		gameOver = false;
	}
	
	@Override
	public void removedFromEngine(Engine engine){
		super.removedFromEngine(engine);
		
		renderer.dispose();
	}
	
	@Override
	public void update(float deltaTime){
		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeType.Filled);
		
		super.update(deltaTime);

		renderer.end();
	}
	
	public boolean isGameOver(){
		return gameOver;
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime){

		if(healthMapper.get(entity).health < 0){
			removeMapper.get(entity).remove = true;
			
			if(playerMapper.has(entity)){
				gameOver = true;
			}
			
		}else if(healthMapper.get(entity).draw){
			HealthComponent health = healthMapper.get(entity);
			Vector2 pos = positionMapper.get(entity).position;
			
			float percent = ((health.health * 1.0f) / (health.maxHealth * 1.0f));
			
			BoundsComponent bounds = boundMapper.get(entity);
			
			renderer.setColor(Color.BLACK);
			renderer.rect(pos.x, pos.y + bounds.height + 2, bounds.width, 5);
			
			renderer.rect(pos.x, pos.y + bounds.height + 2, bounds.width * percent, 3, redLeft, redRight, redRight, redLeft);
			
			if(health.delta > 1f){
				health.health += health.regeneration;
				health.health = Math.min(health.health, health.maxHealth);
				health.delta = 0;
			}else{
				health.delta += deltaTime;
			}
		}
	}
}
