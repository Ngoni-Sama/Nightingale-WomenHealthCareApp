package com.ashiqur.nightingale.ashiqur_util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import com.ashiqur.nightingale.R;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;

import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class UiUtil
{

    public static void initSimpleSpinner(Spinner sp,Context context,int stringArrayRes)
    {
        ArrayAdapter<CharSequence> spinnerRideAdapter = ArrayAdapter.createFromResource(context, stringArrayRes, R.layout.support_simple_spinner_dropdown_item);
        spinnerRideAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(spinnerRideAdapter);

    }
    public static void showToast(Context context,String message,int duration)
    {
        Toast.makeText(context,message,duration).show();
    }
    public static void showSnackBar(View view,String message,int duration)
    {
        if (   Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
        Snackbar.make(view,message, Snackbar.LENGTH_LONG).show();

        else showToast(view.getContext(),message,duration);
    }
    public static void showSnackBar(View view,String message,int duration,String btnText,View.OnClickListener task)
    {
        if (   Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
        Snackbar.make(view,message, Snackbar.LENGTH_LONG).setAction(btnText,task).show();

        else showToast(view.getContext(),message,duration);
    }
    public static View.OnClickListener DO_NOTHING=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    public static void hideSoftKeyboardFromActivity(Activity activity){
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    public static void hideSoftKeyboardFromFragment(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        try
        {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }catch (NullPointerException nptr){showToast(context,"CANT HIDE KEYBOARD",Toast.LENGTH_SHORT);}
    }

    public static void showAlertDialog(final Context context,final String title,final String msg,final String btnText, final int btnTextColor,final int btnBgColor)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, btnText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(btnTextColor));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(context.getResources().getColor(btnBgColor));
            }
        });
        alertDialog.show();

    }
    /**@param task -Task performed on Button Dialog Click**/
    public static void showAlertDialog(final Context context,final String title,final String msg,final String btnText, final int btnTextColor,DialogInterface.OnClickListener task)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, btnText, task);
        alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(btnTextColor));
            }
        });
        alertDialog.show();

    }

}
