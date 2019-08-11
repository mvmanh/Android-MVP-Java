package com.mvm.modelviewpresenter.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

public class ActivityUtils {

    public static void openActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    public static <T extends Parcelable> void openActivityExtra(Context context, Class<?> activity,
        String key, T extra) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(key, extra);
        context.startActivity(intent);
    }
}
