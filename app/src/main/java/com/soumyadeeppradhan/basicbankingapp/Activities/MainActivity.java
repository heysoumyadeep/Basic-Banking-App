package com.soumyadeeppradhan.basicbankingapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.soumyadeeppradhan.basicbankingapp.R;

public class MainActivity extends Activity {

    private TextView devName, appName;
    private ImageView appLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appLogo = findViewById(R.id.appLogo);
        devName = findViewById(R.id.developerName);
        appName = findViewById(R.id.appName);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startEnterAnimation();
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), BankHolderList.class));
                finish();
            }
        }, 3000);
    }

    private void startEnterAnimation() {
        appName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        appLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.topdown));
        devName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));

        appLogo.setVisibility(View.VISIBLE);
        devName.setVisibility(View.VISIBLE);
        appName.setVisibility(View.VISIBLE);
    }

}

