package net.net63.codearcade.ZombieArmageddon.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PhysicsComponent extends Component {
	
	public Vector2 velocity = new Vector2();
	public Vector2 acceleration = new Vector2();
}
