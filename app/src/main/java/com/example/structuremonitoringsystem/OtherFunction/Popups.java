package com.example.structuremonitoringsystem.OtherFunction;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDefaultSettings;
import com.example.structuremonitoringsystem.R;

public class Popups {

    Context context;
    Dialog dialog;
    TextView cancelBtn;
    AppCompatButton confirmBtn;
    RelativeLayout cancelBtnOutside;
    public Popups(Context context) {
        this.context = context;
    }

    public void saveConfirmationPopupWindow(DatabaseDefaultSettings defaultSettings, String behavior, String type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_save_changes);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(context, R.drawable.bg_black_transparent_10));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.slide_up; //Setting the animations to dialog
        dialog.show();

        cancelBtn = dialog.findViewById(R.id.cancelBtn);
        confirmBtn = dialog.findViewById(R.id.confirmBtn);
        cancelBtnOutside = dialog.findViewById(R.id.cancelBtnTwo);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });

        cancelBtnOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultSettings.updateLoggingBehavior(behavior);
                defaultSettings.updateLoggingType(type);
                dialog.cancel();

            }
        });

    }

    public void resetConfirmationPopupWindow(DatabaseDefaultSettings defaultSettings){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_reset_settings);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(context, R.drawable.bg_black_transparent_10));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.slide_up; //Setting the animations to dialog
        dialog.show();

        cancelBtn = dialog.findViewById(R.id.cancelBtn);
        confirmBtn = dialog.findViewById(R.id.confirmBtn);
        cancelBtnOutside = dialog.findViewById(R.id.cancelBtnTwo);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });

        cancelBtnOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultSettings.resetToDefaultData();
                dialog.cancel();

            }
        });

    }


    public void incorrectPINPopupWindow(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_incorrect_pin);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(context, R.drawable.bg_black_transparent_10));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.fade_in; //Setting the animations to dialog
        dialog.show();

        cancelBtn = dialog.findViewById(R.id.cancelBtn);
        cancelBtnOutside = dialog.findViewById(R.id.cancelBtnTwo);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });

        cancelBtnOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });

    }

    public void bluetoothFailed(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_bt_failed);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(context, R.drawable.bg_black_transparent_10));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.fade_in; //Setting the animations to dialog
        dialog.show();

        cancelBtn = dialog.findViewById(R.id.cancelBtn);
        cancelBtnOutside = dialog.findViewById(R.id.cancelBtnTwo);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });

        cancelBtnOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });

    }

    public void selectSensor(SensorSelectionListener listener) {
        RelativeLayout accelBtn, gyroBtn;
        TextView accel, gyro;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_sensor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(context, R.drawable.bg_black_transparent_10));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.fade_in; //Setting the animations to dialog
        dialog.show();

        cancelBtnOutside = dialog.findViewById(R.id.cancelBtnTwo);
        accel = dialog.findViewById(R.id.accelText);
        gyro = dialog.findViewById(R.id.gyroTxt);

        accelBtn = dialog.findViewById(R.id.accelBtn);
        gyroBtn = dialog.findViewById(R.id.gyroBtn);

        cancelBtnOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        accelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(accel.getText().toString().trim());
                dialog.dismiss();
            }
        });

        gyroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(gyro.getText().toString().trim());
                dialog.dismiss();
            }
        });
    }

    public interface SensorSelectionListener {
        void onSensorSelected(String sensor);
    }


}
