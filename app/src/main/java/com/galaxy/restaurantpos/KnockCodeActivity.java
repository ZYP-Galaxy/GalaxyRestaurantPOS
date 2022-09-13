package com.galaxy.restaurantpos;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class KnockCodeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knock_code);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_knock_code, menu);
        return true;
    }
}
