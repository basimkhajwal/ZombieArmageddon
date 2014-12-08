package net.net63.codearcade.ZombieArmageddon;

import net.net63.codearcade.ZombieArmageddon.screens.MenuScreen;
import net.net63.codearcade.ZombieArmageddon.utils.Assets;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;

public class GameMain extends Game {
	
	private static FPSLogger fpsLogger;
	
	
	@Override
	public void create () {
		fpsLogger = new FPSLogger();
		
		Assets.load();
		
		super.setScreen(new MenuScreen(this));
		
		Assets.music.setVolume(0.3f);
		Assets.music.setLooping(true);
		Assets.music.play();
	}

	@Override
	public void pause(){
		super.pause();
		
	}
	
	@Override
	public void resume(){
		super.resume();
		
	}
	
	@Override
	public void dispose(){
		super.dispose();
		Assets.dispose();
	}
	
	@Override
	public void render () {
		super.render();
		
		if(Constants.DEV_MODE){
			fpsLogger.log();
		}
		
	}
}
