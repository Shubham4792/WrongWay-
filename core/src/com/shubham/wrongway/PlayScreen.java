package com.shubham.wrongway;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by SHUBHAM PANDEY on 9/11/2016.
 */
public class PlayScreen extends ScreenAdapter {

    private ShapeRenderer renderer;
    private ExtendViewport viewport;
    private Player player;
    private Cars cars;
    private WrongWay game;
    private Texture road;
    private SpriteBatch batch;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;

    float srcY = 0;

    public PlayScreen(WrongWay game) {
        this.game = game;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Xerox Serif Wide Bold Italic.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter);
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        renderer.setAutoShapeType(true);
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        srcY = viewport.getWorldHeight();
        player = new Player(viewport, game);
        cars = new Cars(viewport);
        road = new Texture("road.png");
        road.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Gdx.input.setInputProcessor(player);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        player.init();
        cars.init();
    }

    @Override
    public void dispose() {
        cars.dispose();
        player.dispose();
        font.dispose();
        batch.dispose();
        generator.dispose();
        renderer.dispose();
    }

    @Override
    public void hide() {
    }

    @Override
    public void render(float delta) {
        player.update(delta);
        cars.update(delta);
        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        srcY -= 10;
        batch.begin();
        batch.draw(road, 0, 0, 0, (int) srcY, (int) viewport.getWorldWidth(), (int) viewport.getWorldHeight());
        font.setColor(Color.BLACK);
        font.draw(batch, Constants.SCORE + "" + Constants.COUNT, (int) viewport.getWorldWidth() - 300, (int) viewport.getWorldHeight() - 50);
        batch.end();
        cars.render(batch, renderer);
        player.render(batch, renderer);
        if (player.hitByCar(cars)) {
            player.vibrateOnCrash();
            game.saveScoreAndLaunchAd();
        }
    }
}
