package net.net63.codearcade.ZombieArmageddon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.net63.codearcade.ZombieArmageddon.GameMain;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = Constants.TITLE;
		config.width = Constants.SCREEN_WIDTH;
		config.height = Constants.SCREEN_HEIGHT;
		config.resizable = false;
		
		new LwjglApplication(new GameMain(), config);
	}
}
