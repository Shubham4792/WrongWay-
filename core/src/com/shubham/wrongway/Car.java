package com.shubham.wrongway;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by SHUBHAM PANDEY on 9/11/2016.
 */

public class Car {
    Vector2 position;
    Vector2 velocity;
    private Rectangle carBounds;
    private Texture car;
    private int laneNumber;

    public Car(Vector2 position) {
        car = new Texture("car.png");
        this.position = position;
        this.velocity = new Vector2(0, 10.0f);
        carBounds = new Rectangle(position.x, position.y, Constants.CAR_WIDTH, Constants.CAR_HEIGHT);
    }

    public void update(float delta) {
        velocity.y -= Constants.VELOCITY_Y_DEC;
        position.mulAdd(velocity, delta);
        carBounds.setPosition(position.x, position.y);
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        batch.begin();
        batch.draw(car, position.x, position.y, Constants.CAR_WIDTH, Constants.CAR_HEIGHT);
        batch.end();
        /*renderer.begin();
        renderer.set(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.rect(position.x, position.y, Car.CAR_WIDTH, Car.CAR_HEIGHT);
        renderer.end();*/
    }

    public Rectangle getCarBounds() {
        return carBounds;
    }

    public int getLaneNumber() {
        return laneNumber;
    }

    public void setLaneNumber(int laneNumber) {
        this.laneNumber = laneNumber;
    }

    public void dispose(){
        car.dispose();
    }
}

