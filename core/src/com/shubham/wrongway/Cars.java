package com.shubham.wrongway;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by SHUBHAM PANDEY on 9/11/2016.
 */
public class Cars {
    public DelayedRemovalArray<Car> carList;
    Viewport viewport;
    private int leftMargin = 20;
    private int carGap = 115;

    public Cars(ExtendViewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        carList = new DelayedRemovalArray<Car>(false, 50);
    }

    public void update(float delta) {
        if (MathUtils.random() < delta * Constants.SPAWNS_PER_SEC) {
            if (carList.size > 1) {
                setNewCarPosition();
            } else {
                int laneNumber = new Random().nextInt(4);
                makeNewCar(laneNumber, new Vector2(leftMargin + laneNumber * carGap, viewport.getWorldHeight() + Constants.CAR_HEIGHT));
            }
        }

        for (Car car : carList) {
            car.update(delta);
        }

        carList.begin();
        for (int i = 0; i < carList.size; i++) {
            if (carList.get(i).position.y < -Constants.CAR_HEIGHT) {
                carList.removeIndex(i);
                Constants.COUNT++;
            }
        }
        carList.end();
    }

    private void setNewCarPosition() {
        int laneNumber = new Random().nextInt(4);
        float newY = carList.get(carList.size - 1).position.y + 2 * Constants.CAR_HEIGHT + new Random().nextInt((int) Constants.CAR_HEIGHT);
        Vector2 newCarPosition;
        if (laneNumber == carList.get(carList.size - 1).getLaneNumber() && newY > viewport.getWorldHeight() + Constants.CAR_HEIGHT) {
            newCarPosition = new Vector2(leftMargin + laneNumber * carGap, newY);
        } else {
            newCarPosition = new Vector2(leftMargin + laneNumber * carGap, viewport.getWorldHeight() + Constants.CAR_HEIGHT);
        }
        makeNewCar(laneNumber, newCarPosition);
    }

    private void makeNewCar(int laneNumber, Vector2 newCarPosition) {
        Car car = new Car(newCarPosition);
        car.setLaneNumber(laneNumber);
        if (!checkOverlap(car)) {
            carList.add(car);
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        for (Car car : carList) {
            car.render(batch, renderer);
        }
    }

    public boolean checkOverlap(Car car) {
        for (Car c : carList) {
            if (car.getCarBounds().overlaps(c.getCarBounds())) {
                return true;
            }
        }
        return false;
    }

    public void dispose() {
        for (Car c : carList) {
            c.dispose();
        }
    }
}
