package com.shubham.wrongway;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by SHUBHAM PANDEY on 9/11/2016.
 */
public class Player extends InputAdapter {
    private final WrongWay game;
    private ExtendViewport viewport;
    private Vector2 position;
    private Rectangle playerBounds;
    private Texture player;

    private static final float ACCELEROMETER_SENSITIVITY = 0.5f;
    private static final float ACCELERATION_OF_GRAVITY = 9.8f;


    public Player(ExtendViewport viewport, WrongWay game) {
        this.viewport = viewport;
        this.game = game;
        player = new Texture("player.png");
        init();
    }

    public void init() {
        position = new Vector2(viewport.getWorldWidth() / 2, 0);
        playerBounds = new Rectangle(position.x, position.y, Constants.CAR_WIDTH, Constants.CAR_HEIGHT);
    }

    public void update(float delta) {
        float accY = Gdx.input.getAccelerometerX();
        float accelerationY = -100 * accY / (ACCELEROMETER_SENSITIVITY * ACCELERATION_OF_GRAVITY);
        if (Math.abs(accY) > 0.3f) {
            position.x += delta * accelerationY * 10.0f;
        }
        playerBounds.setPosition(position.x, position.y);
        collideWithWalls(viewport.getWorldWidth());
    }

    private void collideWithWalls(float viewportWidth) {
        if (position.x < 0) {
            position.x = 0;
            vibrateOnCrash();
            game.saveScoreAndLaunchAd();
        }
        if (position.x + Constants.CAR_WIDTH > viewportWidth) {
            position.x = viewportWidth - Constants.CAR_WIDTH;
            vibrateOnCrash();
            game.saveScoreAndLaunchAd();
        }
    }

    public boolean hitByCar(Cars cars) {
        boolean isHit = false;

        for (Car car : cars.carList) {
            if (playerBounds.overlaps(car.getCarBounds())) {
                isHit = true;
            }
        }
        return isHit;
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        batch.begin();
        batch.draw(player, position.x, position.y, Constants.CAR_WIDTH, Constants.CAR_HEIGHT);
        batch.end();
       /* renderer.begin();
        renderer.set(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.rect(position.x, position.y, Car.CAR_WIDTH, Car.CAR_HEIGHT);
        renderer.end();*/
    }

    public void dispose() {
        player.dispose();
    }

    public void vibrateOnCrash() {
        Gdx.input.vibrate(Constants.VIBRATE_DURATION);
    }
}

