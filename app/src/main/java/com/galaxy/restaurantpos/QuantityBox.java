package com.galaxy.restaurantpos;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class QuantityBox extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity_box);
        this.setTheme(R.style.dialogtheme);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_quantity_box, menu);
        return true;
    }
}
