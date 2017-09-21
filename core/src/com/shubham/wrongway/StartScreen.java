package com.shubham.wrongway;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by SHUBHAM PANDEY on 9/11/2016.
 */
public class StartScreen extends InputAdapter implements Screen {

    private final WrongWay game;
    private BitmapFont font;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private Texture play;
    private ShapeRenderer renderer;
    private Texture title;
    private Vector2 startButton;
    private Texture player;
    private Vector2 velocityCar;
    private Vector2 positionCar;
    private Vector2 positionPlayer;
    private Vector2 velocityPlayer;
    private Texture car;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public StartScreen(WrongWay game) {
        this.game = game;
    }

    @Override
    public void show() {
        init();
    }

    private void init() {
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        play = new Texture("play.png");
        title = new Texture("wrong.png");
        player = new Texture("player.png");
        car = new Texture("car.png");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Xerox Serif Wide Bold Italic.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter);
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        moveCars(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        drawAssets();
    }

    private void drawAssets() {
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(play, viewport.getWorldWidth() / 2 - Constants.RADIUS, viewport.getWorldHeight() / 2 - Constants.RADIUS, 2 * Constants.RADIUS, 2 * Constants.RADIUS);
        batch.draw(title, viewport.getWorldWidth() / 2 - 180, viewport.getWorldHeight() - 250, 350, 250);
        startButton = new Vector2(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2);
        font.setColor(Color.BLUE);
        font.draw(batch, Constants.HIGH_SCORE + "" + game.getHighestScore(), viewport.getWorldWidth() / 2 - 1.5f * Constants.RADIUS, 1.5f * Constants.RADIUS);
        font.draw(batch, Constants.PLAY, (viewport.getWorldWidth() / 2) - 50, (viewport.getWorldHeight() / 2) - 1.1f * Constants.RADIUS);
        batch.draw(player, positionPlayer.x, positionPlayer.y, Constants.CAR_WIDTH, Constants.CAR_HEIGHT);
        batch.draw(car, positionCar.x, positionCar.y, Constants.CAR_WIDTH, Constants.CAR_HEIGHT);
        batch.end();
    }

    private void moveCars(float delta) {
        velocityCar.y -= Constants.VELOCITY_Y_DEC;
        positionCar.mulAdd(velocityCar, delta);
        velocityPlayer.y += Constants.VELOCITY_Y_DEC;
        positionPlayer.mulAdd(velocityPlayer, delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        positionCar = new Vector2(0, viewport.getWorldHeight() + Constants.CAR_HEIGHT);
        positionPlayer = new Vector2(viewport.getWorldWidth() - Constants.CAR_WIDTH, -Constants.CAR_HEIGHT);
        velocityPlayer = new Vector2(0, 10.0f);
        velocityCar = new Vector2(0, 10.0f);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        play.dispose();
        player.dispose();
        car.dispose();
        title.dispose();
        renderer.dispose();
        font.dispose();
        batch.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));
        if (worldTouch.dst(startButton) < Constants.RADIUS) {
            game.showPlayScreen();
        }
        return true;
    }
}
