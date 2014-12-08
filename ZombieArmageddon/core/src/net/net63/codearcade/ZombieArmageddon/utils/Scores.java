package net.net63.codearcade.ZombieArmageddon.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Scores {
	
	public static int getScore(){
		try{
			return Gdx.app.getPreferences("score").getInteger("score");
		}catch(Exception e){
			return 0;
		}
	}
	
	public static boolean setScore(int score){
		int hScore = getScore();
		
		if(hScore <= score){
			Preferences prefs = Gdx.app.getPreferences("score");
			
			prefs.putInteger("score", score);
			
			prefs.flush();
			
			return true;
		}else{
			return false;
		}
	}
	
}
