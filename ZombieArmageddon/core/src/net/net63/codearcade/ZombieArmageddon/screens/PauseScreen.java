package net.net63.codearcade.ZombieArmageddon.screens;

import net.net63.codearcade.ZombieArmageddon.GameMain;
import net.net63.codearcade.ZombieArmageddon.utils.Assets;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseScreen extends AbstractScreen{
	private GameScreen gameScreen;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	
	public PauseScreen(GameMain game, GameScreen gameScreen){
		super(game);
		
		this.gameScreen = gameScreen;
		
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(Assets.pause, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		
		batch.end();
		
		if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Keys.ANY_KEY)){
			Assets.select.play(1f);
			game.setScreen(gameScreen);
		}
	}
}
