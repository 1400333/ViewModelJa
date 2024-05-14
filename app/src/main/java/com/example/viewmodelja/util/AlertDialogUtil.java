package com.example.viewmodelja.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

import com.example.viewmodelja.R;

public class AlertDialogUtil {
    public static AlertDialog showListDialog(Context context, String[] arstrItem, DialogInterface.OnClickListener listener) {
        AlertDialog dlg = newListDialog(context, arstrItem, listener);

        showDialog(dlg);

        return dlg;
    }

    public static AlertDialog showAlertDialog(Context context, String strMsg) {
        AlertDialog dlg = newAlertDialog(context, strMsg);

        showDialog(dlg);

        return dlg;
    }

    public static AlertDialog showAlertDialog(Context context, String strMsg, DialogInterface.OnClickListener yeslistener) {
        AlertDialog dlg = newAlertDialog(context, strMsg, yeslistener);

        showDialog(dlg);

        return dlg;
    }

    public static AlertDialog showAlertDialog(Context context, String strTitle, String strMsg, DialogInterface.OnClickListener yeslistener) {
        AlertDialog dlg = newAlertDialog(context, strTitle, strMsg, yeslistener);

        showDialog(dlg);

        return dlg;
    }


    public static AlertDialog showAlertDialog(Context context, String strMsg, DialogInterface.OnClickListener yeslistener, String strYesText) {
        AlertDialog dlg = newAlertDialog(context, strMsg, yeslistener, strYesText);

        showDialog(dlg);

        return dlg;
    }

    public static AlertDialog showAlertDialog(Context context, String strMsg, DialogInterface.OnClickListener yeslistener, DialogInterface.OnClickListener nolistener) {
        AlertDialog dlg = newAlertDialog(context, strMsg, yeslistener, nolistener);

        showDialog(dlg);

        return dlg;
    }

    public static AlertDialog showAlertDialog(Context context, String strMsg, DialogInterface.OnClickListener yeslistener, String strYesText, DialogInterface.OnClickListener neutralListener, String strNeutralText, DialogInterface.OnClickListener nolistener, String strNoText) {
        AlertDialog dlg = newAlertDialog(context, strMsg, yeslistener, strYesText, neutralListener, strNeutralText, nolistener, strNoText, false);

        showDialog(dlg);

        return dlg;
    }

    public static AlertDialog showAlertDialog(Context context, String strTitle, String strMsg, DialogInterface.OnClickListener yeslistener, String strYesText, DialogInterface.OnClickListener neutralListener, String strNeutralText, DialogInterface.OnClickListener nolistener, String strNoText) {
        AlertDialog dlg = newAlertDialog(context, strTitle, strMsg, yeslistener, strYesText, neutralListener, strNeutralText, nolistener, strNoText, false);

        showDialog(dlg);

        return dlg;
    }

    public static AlertDialog showAlertDialog(Context context, String strMsg, DialogInterface.OnClickListener yeslistener, String strYesText, DialogInterface.OnClickListener nolistener, String strNoText) {
        AlertDialog dlg = newAlertDialog(context, strMsg, yeslistener, strYesText, nolistener, strNoText, false);

        showDialog(dlg);

        return dlg;
    }

    public static AlertDialog showAlertDialog(Context context, String strTitle, String strMsg, DialogInterface.OnClickListener yeslistener, String strYesText, DialogInterface.OnClickListener nolistener, String strNoText) {
        AlertDialog dlg = newAlertDialog(context, strTitle, strMsg, yeslistener, strYesText, nolistener, strNoText, false);

        showDialog(dlg);

        return dlg;
    }

    public static AlertDialog showAlertDialog(Context context, String strTitle, String strMsg, DialogInterface.OnClickListener yeslistener, DialogInterface.OnClickListener nolistener) {
        AlertDialog dlg = newAlertDialog(context, strTitle, strMsg, yeslistener, nolistener);

        showDialog(dlg);

        return dlg;
    }

    public static AlertDialog showAlertDialog(Context context, String strMsg, DialogInterface.OnClickListener yeslistener, String strYesText, DialogInterface.OnClickListener nolistener, String strNoText, boolean bCancelable) {
        AlertDialog dlg = newAlertDialog(context, strMsg, yeslistener, strYesText, nolistener, strNoText, bCancelable);

        showDialog(dlg);

        return dlg;
    }

    private static void showDialog(AlertDialog dlg) {
        try {
            dlg.show();

            // make sure window layoutparam is match_parent
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dlg.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            dlg.getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static AlertDialog newListDialog(Context context, String[] items, DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                .setTitle("")
                .setItems(items, listener)
                .create();
    }

    private static AlertDialog newAlertDialog(Context context, String strMsg) {
        return newAlertDialog(context, strMsg, defaultClick);
    }

    private static AlertDialog newAlertDialog(Context context, String strMsg, DialogInterface.OnClickListener yeslistener) {
        return new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.app_name).toString())
                .setMessage(strMsg)
                .setPositiveButton(context.getString(R.string.dialog_ok), yeslistener)
                .setCancelable(false)
                .create();
    }

    private static AlertDialog newAlertDialog(Context context, String strMsg, DialogInterface.OnClickListener yeslistener, String strYesText) {
        return new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.app_name).toString())
                .setMessage(strMsg)
                .setPositiveButton(strYesText, yeslistener)
                .setCancelable(false)
                .create();
    }

    private static AlertDialog newAlertDialog(Context context, String strMsg, DialogInterface.OnClickListener yeslistener, DialogInterface.OnClickListener nolistener) {
        return new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.app_name).toString())
                .setMessage(strMsg)
                .setPositiveButton(context.getString(R.string.dialog_ok), yeslistener)
                .setNegativeButton(context.getString(R.string.dialog_cancel), nolistener)
                .setCancelable(false)
                .create();
    }

    private static AlertDialog newAlertDialog(Context context, String strTitle, String strMsg, DialogInterface.OnClickListener yeslistener, DialogInterface.OnClickListener nolistener) {
        return new AlertDialog.Builder(context)
                .setTitle(strTitle)
                .setMessage(strMsg)
                .setPositiveButton(context.getString(R.string.dialog_ok), yeslistener)
                .setNegativeButton(context.getString(R.string.dialog_cancel), nolistener)
                .setCancelable(false)
                .create();
    }


    private static AlertDialog newAlertDialog(Context context, String strTitle, String strMsg, DialogInterface.OnClickListener yeslistener) {
        return new AlertDialog.Builder(context)
                .setTitle(strTitle)
                .setMessage(strMsg)
                .setPositiveButton(context.getString(R.string.dialog_ok), yeslistener)
                .setCancelable(false)
                .create();
    }

    private static AlertDialog newAlertDialog(Context context, String strMsg, DialogInterface.OnClickListener yeslistener, String strYesText, DialogInterface.OnClickListener nolistener, String strNoText, boolean bCancelable) {
        return new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.app_name).toString())
                .setMessage(strMsg)
                .setPositiveButton(strYesText, yeslistener)
                .setNegativeButton(strNoText, nolistener)
                .setCancelable(bCancelable)
                .create();
    }

    private static AlertDialog newAlertDialog(Context context, String strTitle, String strMsg, DialogInterface.OnClickListener yeslistener, String strYesText, DialogInterface.OnClickListener nolistener, String strNoText, boolean bCancelable) {
        return new AlertDialog.Builder(context)
                .setTitle(strTitle)
                .setMessage(strMsg)
                .setPositiveButton(strYesText, yeslistener)
                .setNegativeButton(strNoText, nolistener)
                .setCancelable(bCancelable)
                .create();
    }

    private static AlertDialog newAlertDialog(Context context, String strTitle, String strMsg, DialogInterface.OnClickListener yeslistener, String strYesText, DialogInterface.OnClickListener neutralListener, String strNeutralText, DialogInterface.OnClickListener nolistener, String strNoText, boolean bCancelable) {
        return new AlertDialog.Builder(context)
                .setTitle(strTitle)
                .setMessage(strMsg)
                .setPositiveButton(strYesText, yeslistener)
                .setNegativeButton(strNoText, nolistener)
                .setNeutralButton(strNeutralText, neutralListener)
                .setCancelable(bCancelable)
                .create();
    }

    private static AlertDialog newAlertDialog(Context context, String strMsg, DialogInterface.OnClickListener yeslistener, String strYesText, DialogInterface.OnClickListener neutralListener, String strNeutralText, DialogInterface.OnClickListener nolistener, String strNoText, boolean bCancelable) {
        return new AlertDialog.Builder(context)
                .setTitle(context.getResources().getText(R.string.app_name).toString())
                .setMessage(strMsg)
                .setPositiveButton(strYesText, yeslistener)
                .setNegativeButton(strNoText, nolistener)
                .setNeutralButton(strNeutralText, neutralListener)
                .setCancelable(bCancelable)
                .create();
    }

    private static DialogInterface.OnClickListener defaultClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };
}
