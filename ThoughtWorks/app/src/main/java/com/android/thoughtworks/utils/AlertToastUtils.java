package com.android.thoughtworks.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * AlertToastUtils
 */
public class AlertToastUtils {

    private static ProgressDialog mProgressDialog;

    public static void createToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void showProgressDialog(Context context, String msg) {
        if (context == null) return;

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public static void stopProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}