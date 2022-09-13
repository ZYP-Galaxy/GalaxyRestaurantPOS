package com.galaxy.restaurantpos;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.anggastudio.printama.Printama;

public class VoucherDetailActivity extends AppCompatActivity {

    public static Bitmap printbm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_detail);

    }

    public void PrintTest(View view) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame);
        View linearLayout = findViewById(R.id.root_view);
//        Printama print = new Printama(this, GlobalClass.btprinter_name);
//        print.connect(new Printama.OnConnected() {
//            @Override
//            public void onConnected(Printama printama) {
//                print.printFromView(linearLayout);
//                print.close();
//            }
//        });

        Printama print = new Printama(this,GlobalClass.btprinter_name);
        print.connect(new Printama.OnConnected() {
            @Override
            public void onConnected(Printama printama) {
                //print.printFromView(linearLayout);
                print.printImage(printbm);
            }
        });


    }
}