package com.nipunbirla.boxloader;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    BoxLoaderView mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoader = (BoxLoaderView) findViewById(R.id.blv);

        mLoader.setSpeed(20);
    }
}
