package net.net63.codearcade.ZombieArmageddon.screens;

import net.net63.codearcade.ZombieArmageddon.GameMain;
import net.net63.codearcade.ZombieArmageddon.utils.Assets;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends AbstractScreen{
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Stage stage;
	
	public MenuScreen(GameMain game) {
		super(game);
		
		stage = new Stage();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void hide(){
		super.hide();
		
		batch.dispose();
		stage.dispose();
	}
	
	@Override
	public void resize(int width, int height){
		super.resize(width, height);
		
		camera.setToOrtho(false);
		camera.update();
		
		stage.getViewport().update(width, height);
		
		TextButton startGame = new TextButton("Start Game", Assets.skin);
		
		startGame.setWidth(400f);
		startGame.setHeight(80f);
		
		startGame.setPosition(200f, 80f);
		
		startGame.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				super.touchUp(event, x, y, pointer, button);
				Assets.select.play(1f);
				game.setScreen(new ControlsScreen(game));
			}
		});
		startGame.setDisabled(false);
		
		stage.addActor(startGame);
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		batch.draw(Assets.backgroundFuzzy, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		batch.draw(Assets.logo, Constants.LOGO_X, Constants.LOGO_Y, Constants.LOGO_WIDTH, Constants.LOGO_HEIGHT);
		
		Assets.fonts[Assets.FONT_FORTY].setColor(Color.NAVY);
		Assets.fonts[Assets.FONT_FORTY].draw(batch, "Survive for as long as you can", 200, 40);
		
		batch.end();
		
		stage.act(delta);
		stage.draw();
	}
	

}
