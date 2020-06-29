
package com.mp.android.apps.monke.monkeybook.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.mp.android.apps.MyApplication;
import com.mp.android.apps.R;

import java.util.HashMap;
import java.util.Map;

public class NetworkUtil {
    private static final Map<Integer, String> errorMap = new HashMap<>();

    public static final int SUCCESS = 10000;
    public static final int ERROR_CODE_NONET = 10001;
    public static final int ERROR_CODE_OUTTIME = 10002;
    public static final int ERROR_CODE_ANALY = 10003;

    static {
        errorMap.put(ERROR_CODE_NONET, MyApplication.getInstance().getString(R.string.net_error_10001));
        errorMap.put(ERROR_CODE_OUTTIME, MyApplication.getInstance().getString(R.string.net_error_10002));
        errorMap.put(ERROR_CODE_ANALY, MyApplication.getInstance().getString(R.string.net_error_10003));
    }

    public static String getErrorTip(int code) {
        return errorMap.get(code);
    }

    public static boolean isNetWorkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
