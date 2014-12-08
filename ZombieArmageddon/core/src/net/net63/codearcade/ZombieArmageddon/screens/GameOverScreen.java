package net.net63.codearcade.ZombieArmageddon.screens;

import net.net63.codearcade.ZombieArmageddon.GameMain;
import net.net63.codearcade.ZombieArmageddon.utils.Assets;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;
import net.net63.codearcade.ZombieArmageddon.utils.Scores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOverScreen extends AbstractScreen{

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private int score;
	private boolean highScore;
	private int high;
	private Stage stage;
	
	public GameOverScreen(GameMain game, int score) {
		super(game);
		
		this.score = score;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		
		batch = new SpriteBatch();
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		if(Scores.setScore(score)){
			highScore = true;
		}else{
			high = Scores.getScore();
		}
	}
	
	@Override
	public void resize(int width, int height){
		super.resize(width, height);
		
		stage.getViewport().update(width, height);
		
		TextButton startGame = new TextButton("Play Again", Assets.skin);
		
		startGame.setWidth(400f);
		startGame.setHeight(80f);
		
		startGame.setPosition(200f, 40f);
		
		startGame.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				super.touchUp(event, x, y, pointer, button);
				Assets.select.play(1f);
				game.setScreen(new GameScreen(game));
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
		
		batch.draw(Assets.gameOver, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		
		Assets.fonts[Assets.FONT_FIFTY].setColor(Color.MAROON);
		Assets.fonts[Assets.FONT_FIFTY].draw(batch, "Score  " + score, 380, 315);
		
		Assets.fonts[Assets.FONT_FORTY].setColor(Color.BLUE);
		if(highScore){
			Assets.fonts[Assets.FONT_FORTY].draw(batch, "New High Score!", 360, 270);
		}else{
			Assets.fonts[Assets.FONT_FORTY].draw(batch, "High Score  " + high, 360, 270);
		}
		
		
		batch.end();
		
		stage.act(delta);
		stage.draw();
	}

}
