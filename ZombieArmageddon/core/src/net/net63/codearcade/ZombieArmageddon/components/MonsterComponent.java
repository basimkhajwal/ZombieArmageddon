package net.net63.codearcade.ZombieArmageddon.components;

import com.badlogic.ashley.core.Component;

public class MonsterComponent extends Component{
	public static final int STATE_LEFT = 0;
	public static final int STATE_RIGHT = 1;
	public static final int STATE_MOVING_LEFT = 2;
	public static final int STATE_MOVING_RIGHT = 3;
	
	public float delta;
}
