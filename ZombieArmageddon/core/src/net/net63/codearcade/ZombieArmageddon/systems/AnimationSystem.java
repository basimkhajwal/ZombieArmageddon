package net.net63.codearcade.ZombieArmageddon.systems;

import net.net63.codearcade.ZombieArmageddon.components.AnimationComponent;
import net.net63.codearcade.ZombieArmageddon.components.RenderComponent;
import net.net63.codearcade.ZombieArmageddon.components.StateComponent;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationSystem extends IteratingSystem{
	
	private ComponentMapper<AnimationComponent> animationMapper;
	private ComponentMapper<RenderComponent> renderMapper;
	private ComponentMapper<StateComponent> stateMapper;
	
	@SuppressWarnings("unchecked")
	public AnimationSystem(){
		super(Family.getFor(AnimationComponent.class, RenderComponent.class, StateComponent.class), Constants.SYSTEM_PRIORITIES.ANIMATION);
	
		animationMapper = ComponentMapper.getFor(AnimationComponent.class);
		renderMapper = ComponentMapper.getFor(RenderComponent.class);
		stateMapper = ComponentMapper.getFor(StateComponent.class);
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime){
		
		//Get all the required components from the mappers
		AnimationComponent animationComponent = animationMapper.get(entity);
		RenderComponent renderComponent = renderMapper.get(entity);
		StateComponent stateComponent = stateMapper.get(entity);
		
		//Get the current animation
		Animation animation = animationComponent.animations.get(stateComponent.get());
		
		//If not null then set the current texture to the current time of the animation
		if(animation != null){
			renderComponent.texture = animation.getKeyFrame(stateComponent.time);
		}
		
		//Update the time of the state
		stateComponent.time += deltaTime;
		
		//Reset the animation once it is completed
		if(animation.isAnimationFinished(stateComponent.time)){
			stateComponent.time = 0;
		}
	}
	
}
