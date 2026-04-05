package com.has_akh.flying_football;

import android.app.Activity;
import android.view.View;
import com.google.android.gms.ads.AdView;

public class AndroidAdService implements AdService {

    private final Activity activity;
    private final AdView adView;

    public AndroidAdService(Activity activity, AdView adView) {
        this.activity = activity;
        this.adView = adView;
    }

    @Override
    public void showBanner() {
        activity.runOnUiThread(() -> adView.setVisibility(View.VISIBLE));
    }

    @Override
    public void hideBanner() {
        activity.runOnUiThread(() -> adView.setVisibility(View.GONE));
    }
}