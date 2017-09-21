package com.shubham.wrongway;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class WrongWay extends Game {
    private PlayScreen playScreen;
    private PauseScreen pauseScreen;
    private StartScreen startScreen;
    protected AdController adController;

    public WrongWay(AdController adController) {
        this.adController = adController;
    }

    @Override
    public void create() {
        showStartScreen();
    }

    public void showPauseScreen() {
        pauseScreen = new PauseScreen(this);
        setScreen(pauseScreen);
        removePlayScreen();
        removeStartScreen();
    }

    @Override
    public void dispose() {
      super.dispose();
        removePlayScreen();
        removeStartScreen();
        removePauseScreen();
    }

    private void removePlayScreen() {
        if (playScreen != null) {
            playScreen.dispose();
            playScreen = null;
        }
    }

    private void removeStartScreen() {
        if (startScreen != null) {
            startScreen.dispose();
            startScreen = null;
        }
    }

    public void showPlayScreen() {
        Constants.COUNT = 0;
        playScreen = new PlayScreen(this);
        setScreen(playScreen);
        removeStartScreen();
        removePauseScreen();
    }

    private void removePauseScreen() {
        if (pauseScreen != null) {
            pauseScreen.dispose();
            pauseScreen = null;
        }
    }

    @Override
    public void render() {
        super.render();
    }

    protected void saveScoreAndLaunchAd() {
        saveScore(Constants.COUNT);
        showPauseScreen();
        if (adController.isWifiConnected() && adController.isAdLoaded()) {
            adController.showAd(new Runnable() {
                @Override
                public void run() {
                    showPauseScreen();
                }
            });
        } else {
           // showPauseScreen();
        }
    }

    public void saveScore(int currentScore) {
        if (currentScore > getHighestScore()) {
            Preferences prefs = Gdx.app.getPreferences("My Preferences");
            prefs.putInteger("HighScore", currentScore);
            prefs.flush();
        }
    }

    public int getHighestScore() {
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        return prefs.getInteger("HighScore", 0);
    }

    public void showStartScreen() {
        startScreen = new StartScreen(this);
        setScreen(startScreen);
        Constants.COUNT = 0;
        removePauseScreen();
        removePlayScreen();
    }
}
