package com.galaxy.restaurantpos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class GalaxyService extends Service {

    final DatabaseHelper dbhelper = new DatabaseHelper(this);



    @Override
    public IBinder onBind(Intent intent) {
        //Toast.makeText(this,"This is from GalaxyService onBind",Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this,"This is from GalaxyService onStartCmd",Toast.LENGTH_SHORT).show();
        Log.d("ClearFromRecentService", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this,"This is from GalaxyService onDestroy",Toast.LENGTH_SHORT).show();
        Log.d("ClearFromRecentService", "Service Destroyed");
        if (GlobalClass.CheckConnection(this)) {
            Json_class jsclass = new Json_class();
            jsclass.getString(dbhelper
                    .getServiceURL()
                    + "/Data/ClearLoginUser?userid="
                    + java.net.URLEncoder.encode(String.valueOf(LoginActivity.pos)));
        }
        super.onDestroy();

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("ClearFromRecentService", "END");
        //Code here
        Toast.makeText(this,"This is from GalaxyService onTaskRemoved",Toast.LENGTH_SHORT).show();
        if (GlobalClass.CheckConnection(this)) {
            Json_class jsclass = new Json_class();
            jsclass.getString(dbhelper
                    .getServiceURL()
                    + "/Data/ClearLoginUser?userid="
                    + java.net.URLEncoder.encode(String.valueOf(LoginActivity.pos)));
        }
        stopSelf();
    }
}
