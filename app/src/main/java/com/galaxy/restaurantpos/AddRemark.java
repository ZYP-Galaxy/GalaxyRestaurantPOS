package com.galaxy.restaurantpos;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddRemark extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remark);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_remark, menu);
        return true;
    }
}
