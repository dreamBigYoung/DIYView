package com.example.bigyoung.diyview.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 巫晨 on 2017/4/12.
 */
public class MyToast {

    public static void show(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

}
