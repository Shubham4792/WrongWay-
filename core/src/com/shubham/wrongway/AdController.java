package com.shubham.wrongway;

/**
 * Created by SHUBHAM PANDEY on 9/11/2016.
 */
public interface AdController {
    void showAd(Runnable runnable);

    boolean isWifiConnected();

    boolean isAdLoaded();
}

