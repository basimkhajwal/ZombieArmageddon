package net.net63.codearcade.ZombieArmageddon.utils;

import net.net63.codearcade.ZombieArmageddon.components.AnimationComponent;
import net.net63.codearcade.ZombieArmageddon.components.BoundsComponent;
import net.net63.codearcade.ZombieArmageddon.components.HealthComponent;
import net.net63.codearcade.ZombieArmageddon.components.PhysicsComponent;
import net.net63.codearcade.ZombieArmageddon.components.PlayerComponent;
import net.net63.codearcade.ZombieArmageddon.components.PositionComponent;
import net.net63.codearcade.ZombieArmageddon.components.RemoveComponent;
import net.net63.codearcade.ZombieArmageddon.components.RenderComponent;
import net.net63.codearcade.ZombieArmageddon.components.StateComponent;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class WorldUtils {
	
	private Engine engine;
	
	public WorldUtils(Engine engine){
		this.engine = engine;
		
	}
	
	public void create(){
		createBackground();
		createMist();
		createPlayer();
	}
	
	public Entity createPlayer(){
		Entity player = new Entity();
		
		PlayerComponent playerComponent = new PlayerComponent();
		AnimationComponent animation = new AnimationComponent();
		RenderComponent render = new RenderComponent();
		StateComponent state = new StateComponent();
		PositionComponent pos = new PositionComponent();
		BoundsComponent bounds = new BoundsComponent();
		PhysicsComponent physics = new PhysicsComponent();
		HealthComponent health = new HealthComponent();
		RemoveComponent remove = new RemoveComponent();
		
		health.draw = true;
		health.health = Constants.PLAYER_HEALTH;
		health.maxHealth = Constants.PLAYER_HEALTH;
		health.regeneration = Constants.PLAYER_REGEN;
		
		state.set(PlayerComponent.STATE_LEFT);
		
		pos.position = new Vector2(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2);
		
		bounds.width = Constants.PLAYER_WIDTH;
		bounds.height = Constants.PLAYER_HEIGHT;
		
		physics.velocity = new Vector2();
		physics.acceleration = new Vector2();
		
		Array<TextureRegion> leftWalking = new Array<TextureRegion>();
		
		for(String s: Constants.LEFT_WALKING){
			leftWalking.add(Assets.player.findRegion(s));
		}
		
		animation.animations.put(PlayerComponent.STATE_MOVING_LEFT, new Animation(0.2f, leftWalking));
		animation.animations.put(PlayerComponent.STATE_LEFT, new Animation(1f, leftWalking.first()));
		
		Array<TextureRegion> rightWalking = new Array<TextureRegion>();
		
		for(String s: Constants.RIGHT_WALKING){
			rightWalking.add(Assets.player.findRegion(s));
		}
		
		animation.animations.put(PlayerComponent.STATE_MOVING_RIGHT, new Animation(0.2f, rightWalking));
		animation.animations.put(PlayerComponent.STATE_RIGHT, new Animation(1f, rightWalking.first()));
		
		render.texture = leftWalking.first();
		
		player.add(remove);
		player.add(animation);
		player.add(playerComponent);
		player.add(state);
		player.add(pos);
		player.add(bounds);
		player.add(render);
		player.add(physics);
		player.add(health);
		
		engine.addEntity(player);
		
		return player;
	}
	
	public Entity createMist(){
		Entity mist = new Entity();
		
		PositionComponent pos = new PositionComponent();
		RenderComponent render = new RenderComponent();
		BoundsComponent bounds = new BoundsComponent();
		
		pos.position = new Vector2(0, -50);
		
		render.texture = Assets.mist;
		render.z = Constants.MIST_Z;
		
		bounds.width = Constants.SCREEN_WIDTH;
		bounds.height = Constants.SCREEN_HEIGHT;
		
		mist.add(pos);
		mist.add(render);
		mist.add(bounds);
		
		engine.addEntity(mist);
		
		return mist;
	}
	
	public Entity createBackground(){
		Entity background = new Entity();
		
		PositionComponent pos = new PositionComponent();
		RenderComponent render = new RenderComponent();
		BoundsComponent bounds = new BoundsComponent();
		
		pos.position = new Vector2(0, 0);
		
		render.texture = Assets.background;
		render.z = Constants.BACKGROUND_Z;
		
		bounds.width = Constants.SCREEN_WIDTH;
		bounds.height = Constants.SCREEN_HEIGHT;
		
		background.add(pos);
		background.add(render);
		background.add(bounds);
		
		engine.addEntity(background);
				
		return background;
	}
}

