package net.net63.codearcade.ZombieArmageddon.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

//Texture packer class to pack textures in a pack for later use, not run alongside the game
public class MyPacker {
	
	private static final String FOLDER = "../android/assets/";
	
	public static void main(String[] args){
		
		Settings settings = new Settings();
		
		//Set the max dimensions of the outputted texture image
		settings.maxWidth = 1<<15;
		settings.maxHeight = 1<<15;
		
		//Pack the texture pack from the source folder
		TexturePacker.process(settings, FOLDER + "source/player", FOLDER + "packs", "player");
		TexturePacker.process(settings, FOLDER + "source/monster", FOLDER + "packs", "monster");
		TexturePacker.process(settings, FOLDER + "source/bullet", FOLDER + "packs", "bullet");
	}
	
}
