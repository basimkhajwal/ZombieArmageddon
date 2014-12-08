package net.net63.codearcade.ZombieArmageddon.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent extends Component{
	public static final int STATE_LEFT = 0;
	public static final int STATE_RIGHT = 1;
	public static final int STATE_MOVING_LEFT = 2;
	public static final int STATE_MOVING_RIGHT = 3;
	
	public int numBullets = 0;
	public float damageDelta = 0;
}
