package net.net63.codearcade.ZombieArmageddon.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	
	private static final int[] FONT_SIZES = new int[]{10, 20, 30, 50, 100};
	
	public static final int FONT_TEN = 0;
	public static final int FONT_TWENTY = 1;
	public static final int FONT_FORTY = 2;
	public static final int FONT_FIFTY = 3;
	public static final int FONT_HUNDRED = 4;
	
	private static final String BACKGROUND = "textures/background.png";
	private static final String BACKGROUND_FUZZY = "textures/background-fuzzy.png";
	private static final String MIST = "textures/mist.png";
	private static final String LOGO = "textures/logo.png";
	private static final String PLAYER_PACK = "packs/player.atlas";
	private static final String MONSTER_PACK = "packs/monster.atlas";
	private static final String FONT = "fonts/ARCADECLASSIC.TTF";
	private static final String UI_SKIN = "skin/uiskin.atlas";
	private static final String SKIN = "skin/uiskin.json";
	private static final String BULLET_ATLAS = "packs/bullet.atlas";
	private static final String PAUSE = "textures/paused.png";
	private static final String CONTROLS = "textures/controls.png";
	private static final String GAME_OVER = "textures/gameover.png";
	private static final String MUSIC = "sounds/music.mp3";
	private static final String HURT = "sounds/hurt.wav";
	private static final String ZOMBIE_HURT = "sounds/hit-zombie.wav";
	private static final String SELECT = "sounds/select.wav";
	private static final String ZOMBIE = "sounds/zombie.wav";
	private static final String SHOOT = "sounds/shoot.wav";
	
	private static AssetManager manager = new AssetManager();
	
	public static TextureRegion background;
	public static TextureRegion backgroundFuzzy;
	public static TextureRegion logo;
	public static TextureRegion mist;
	public static TextureRegion gameOver;
	public static TextureRegion pause;
	public static TextureRegion controls;
	
	public static TextureAtlas player;
	public static TextureAtlas monster;
	public static TextureAtlas bullet;
	
	public static BitmapFont[] fonts;
	
	public static Music music;
	public static Sound select;
	public static Sound zombieHurt;
	public static Sound shoot;
	public static Sound hurt;
	public static Sound zombie;
	
	public static Skin skin;
	
	@SuppressWarnings("deprecation")
	public static void load(){
		
		FileHandleResolver resolver = new InternalFileHandleResolver();
		
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		
		manager.load(BACKGROUND, Texture.class);
		manager.load(BACKGROUND_FUZZY, Texture.class);
		manager.load(MIST, Texture.class);
		manager.load(PLAYER_PACK, TextureAtlas.class);
		manager.load(MONSTER_PACK, TextureAtlas.class);
		manager.load(LOGO, Texture.class);
		manager.load(FONT, FreeTypeFontGenerator.class);
		manager.load(UI_SKIN, TextureAtlas.class);
		manager.load(BULLET_ATLAS, TextureAtlas.class);
		manager.load(GAME_OVER, Texture.class);
		manager.load(MUSIC, Music.class);
		manager.load(SELECT, Sound.class);
		manager.load(ZOMBIE_HURT, Sound.class);
		manager.load(SHOOT, Sound.class);
		manager.load(HURT, Sound.class);
		manager.load(ZOMBIE, Sound.class);
		manager.load(PAUSE, Texture.class);
		manager.load(CONTROLS, Texture.class);
		
		manager.finishLoading();
		
		background = new TextureRegion(manager.get(BACKGROUND, Texture.class));
		backgroundFuzzy = new TextureRegion(manager.get(BACKGROUND_FUZZY, Texture.class));
		mist = new TextureRegion(manager.get(MIST, Texture.class));
		player = manager.get(PLAYER_PACK, TextureAtlas.class);
		monster = manager.get(MONSTER_PACK, TextureAtlas.class);
		logo = new TextureRegion(manager.get(LOGO, Texture.class));
		bullet = manager.get(BULLET_ATLAS, TextureAtlas.class);
		gameOver = new TextureRegion(manager.get(GAME_OVER, Texture.class));
		music = manager.get(MUSIC, Music.class);
		select = manager.get(SELECT, Sound.class);
		zombieHurt = manager.get(ZOMBIE_HURT, Sound.class);
		shoot = manager.get(SHOOT, Sound.class);
		hurt = manager.get(HURT, Sound.class);
		zombie = manager.get(ZOMBIE, Sound.class);
		pause = new TextureRegion(manager.get(PAUSE, Texture.class));
		controls = new TextureRegion(manager.get(CONTROLS, Texture.class));
		
		FreeTypeFontGenerator generator = manager.get(FONT, FreeTypeFontGenerator.class);
		
		fonts = new BitmapFont[FONT_SIZES.length];
		
		for(int i = 0; i < FONT_SIZES.length; i++){
			fonts[i] = generator.generateFont(FONT_SIZES[i]);
		}
		
		manager.unload(FONT);
		
		skin = new Skin();
		
		skin.add("default-font", fonts[FONT_FIFTY]);
		
		skin.addRegions(manager.get(UI_SKIN, TextureAtlas.class));
		skin.load(Gdx.files.internal(SKIN));
	}
	
	public static void dispose(){
		manager.dispose();
		
		for(BitmapFont f: fonts){
			f.dispose();
		}
		
	}
}
