package net.net63.codearcade.ZombieArmageddon.screens;

import net.net63.codearcade.ZombieArmageddon.GameMain;
import net.net63.codearcade.ZombieArmageddon.systems.AnimationSystem;
import net.net63.codearcade.ZombieArmageddon.systems.BulletCollisionSystem;
import net.net63.codearcade.ZombieArmageddon.systems.EntityPositioningSystem;
import net.net63.codearcade.ZombieArmageddon.systems.EntityRemovalSystem;
import net.net63.codearcade.ZombieArmageddon.systems.HealthSystem;
import net.net63.codearcade.ZombieArmageddon.systems.MonsterGenerationSystem;
import net.net63.codearcade.ZombieArmageddon.systems.MonsterMovementSystem;
import net.net63.codearcade.ZombieArmageddon.systems.PhysicsSystem;
import net.net63.codearcade.ZombieArmageddon.systems.PlayerMovementSystem;
import net.net63.codearcade.ZombieArmageddon.systems.RenderingSystem;
import net.net63.codearcade.ZombieArmageddon.utils.Assets;
import net.net63.codearcade.ZombieArmageddon.utils.Constants;
import net.net63.codearcade.ZombieArmageddon.utils.WorldUtils;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen extends AbstractScreen{
	
	private Engine engine;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Stage stage;
	private WorldUtils worldUtils;
	
	private float totalTime;
	
	public GameScreen(GameMain game){
		super(game);
		
		stage = new Stage();
		stage.setViewport(new ScreenViewport());
		
		engine = new Engine();
		
		camera = new OrthographicCamera();
		
		batch = new SpriteBatch();
		
		//Add the systems
		engine.addSystem(new RenderingSystem(camera));
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new PlayerMovementSystem());
		engine.addSystem(new MonsterGenerationSystem());
		engine.addSystem(new PhysicsSystem());
		engine.addSystem(new MonsterMovementSystem());
		engine.addSystem(new EntityPositioningSystem());
		engine.addSystem(new EntityRemovalSystem());
		engine.addSystem(new HealthSystem(camera));
		engine.addSystem(new BulletCollisionSystem());
		
		worldUtils = new WorldUtils(engine);
		
		worldUtils.create();
		
	}
	
	@Override
	public void resize(int width, int height){
		super.resize(width, height);
		camera.setToOrtho(false);
		camera.update();
		
		stage.getViewport().update(width, height);
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
		
		engine.update(delta);
		
		int minutes = (int) totalTime / 60;
		int seconds = (int) (totalTime % 60);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		Assets.fonts[Assets.FONT_FIFTY].setColor(Color.MAROON);
		Assets.fonts[Assets.FONT_FIFTY].draw(batch, ((minutes == 0)? "": minutes + "min ") + seconds, 400, Constants.SCREEN_HEIGHT - 40);
		
		batch.end();
		
		totalTime += delta;
		
		if(engine.getSystem(HealthSystem.class).isGameOver()){
			engine.removeAllEntities();
			ImmutableArray<EntitySystem> sys = engine.getSystems();
			for(int i = 0; i < sys.size(); i++){
				engine.removeSystem(sys.get(i));
			}
			
			game.setScreen(new GameOverScreen(game, (int) totalTime));
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.P)){
			Assets.select.play(1f);
			game.setScreen(new PauseScreen(game, this));
		}
	}

}
