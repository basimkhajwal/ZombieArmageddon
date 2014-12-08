package net.net63.codearcade.ZombieArmageddon.utils;

public class Constants {
	
	//Debug purposes
	public static final String LOG = "Zombie Armageddon";
	public static final boolean DEV_MODE = true;
	
	//Screen constants
	public static final String TITLE = "Zombie Armageddon";
	
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	public static final int LOGO_X = 87;
	public static final int LOGO_Y = 300;
	public static final int LOGO_WIDTH = 625;
	public static final int LOGO_HEIGHT = 261;
	
	public static final int PLAYER_WIDTH = (int) (42*1.5);
	public static final int PLAYER_HEIGHT = (int) (64*1.5);
	public static final int PLAYER_MAX_HEIGHT = 400;
	public static final int PLAYER_SPEED = 175;
	
	public static final int MONSTER_WIDTH = 25 * 2;
	public static final int MONSTER_HEIGHT = 55 * 2;
	public static final float MONSTER_SPEED = 75f;
	
	public static final int DISTANCE_THRESHHOLD = 10;
	
	public static final int BULLET_SPEED = 300;
	public static final int BULLET_DAMAGE = 10;
	
	
	//Priorities in which systems are run
	public static class SYSTEM_PRIORITIES{
		public static final int PHSYICS = 5;
		public static final int MOVEMENT = 6;
		public static final int MONSTER_GENERATION = 7;
		public static final int MONSTER_MOVEMENT = 8;
		public static final int BULLET_COLLISION = 9;
		public static final int ENTITY_POSITIONING = 10;
		public static final int ANIMATION = 11;
		public static final int ENTITY_REMOVAL = 12;
		public static final int RENDERING = 13;
		public static final int HEALTH = 14;
	}
	
	public static final int MAX_MONSTERS = 3;
	public static final int MAX_FINAL_MONSTERS = 10;
	public static final int MONSTER_THRESHHOLD = 7;
	public static final float MONSTER_CHANCE = 1f / 100f;
	
	public static final int PLAYER_REGEN = 20;
	public static final int ZOMBIE_REGEN = 5;
	
	public static final int BACKGROUND_Z = 0;
	public static final int MIST_Z = 100;
	public static final int MONSTER_Z = 3;
	public static final int PLAYER_Z = 5;
	public static final int BULLET_Z = 50;
	
	public static final int MAX_BULLETS = 7;
	public static final float BULLET_TIME_REGEN = 0.7f;
	
	public static final int BULLET_WIDTH = 4;
	public static final int BULLET_HEIGHT = 7;
	
	public static final int ZOMBIE_HEALTH = 40;
	public static final int PLAYER_HEALTH = 200;
	
	public static final String[] RIGHT_WALKING = new String[]{"left-walking", "left-walking-1"};
	public static final String[] LEFT_WALKING = new String[]{"right-walking", "right-walking-1"};
	
	public static final String[] BULLET_ANIMATION = new String[]{"bullet", "bullet-1", "bullet-2", "bullet-3", "bullet-4", "bullet-5", "bullet-6"};
}
