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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by SHUBHAM PANDEY on 9/11/2016.
 */
public class PauseScreen extends InputAdapter implements Screen {
    WrongWay game;
    private Vector2 resumeButton;
    private ExtendViewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture retry;
    private Texture crash;
    private FreeTypeFontGenerator generator;
    private Texture shock;
    private Texture wrecked;

    public PauseScreen(WrongWay game) {
        this.game = game;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Xerox Serif Wide Bold Italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        retry = new Texture("retry.png");
        shock = new Texture("shock.png");
        crash = new Texture("crashed.png");
        wrecked = new Texture("wrecked.png");
        resumeButton = new Vector2();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(retry, viewport.getWorldWidth() / 2 - Constants.RADIUS / 2, viewport.getWorldHeight() / 2, Constants.RADIUS, Constants.RADIUS);
        batch.draw(shock, 0, 0, 2 * Constants.RADIUS, 2 * Constants.RADIUS);
        batch.draw(crash, viewport.getWorldWidth() / 2, 0, 2.5f * Constants.RADIUS, 2.5f * Constants.RADIUS);
        batch.draw(wrecked, Constants.RADIUS / 2, viewport.getWorldHeight() / 2 - 2 * Constants.RADIUS, 4 * Constants.RADIUS, 2 * Constants.RADIUS);
        font.setColor(Color.BLACK);
        font.draw(batch, Constants.YOUR_SCORE + "" + Constants.COUNT, 20, viewport.getWorldHeight() - Constants.RADIUS);
        font.draw(batch, Constants.HIGH_SCORE + "" + game.getHighestScore(), 20, viewport.getWorldHeight() - 2 * Constants.RADIUS);
        font.setColor(Color.ROYAL);
        font.draw(batch, Constants.RETRY, (viewport.getWorldWidth() / 2) - 60, (viewport.getWorldHeight() / 2) + Constants.RADIUS + 20);
        batch.end();
        resumeButton.x = viewport.getWorldWidth() / 2;
        resumeButton.y = viewport.getWorldHeight() / 2 + Constants.RADIUS / 2;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        retry.dispose();
        crash.dispose();
        shock.dispose();
        wrecked.dispose();
        batch.dispose();
        font.dispose();
        generator.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));
        if (worldTouch != null && worldTouch.dst(resumeButton) < Constants.RADIUS / 2) {
            game.showPlayScreen();
        }
        return true;
    }
}

