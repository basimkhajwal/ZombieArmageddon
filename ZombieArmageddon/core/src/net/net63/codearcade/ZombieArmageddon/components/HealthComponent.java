package net.net63.codearcade.ZombieArmageddon.components;

import com.badlogic.ashley.core.Component;

public class HealthComponent extends Component{
	
	public int health;
	public int maxHealth;
	
	public boolean draw;
	public int regeneration = 0;
	
	public float delta;
}
