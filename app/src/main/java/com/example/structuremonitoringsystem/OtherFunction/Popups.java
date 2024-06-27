package com.example.structuremonitoringsystem.OtherFunction;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDefaultSettings;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDevice;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
import com.example.structuremonitoringsystem.R;

public class Popups {

    Context context;
    Dialog dialog;
    TextView cancelBtn;
    AppCompatButton confirmBtn;
    RelativeLayout cancelBtnOutside;
    EditText thresholdInp;
    EditText tempName, height, width, thickness;
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



    public void editThreshold(DatabaseDefaultSettings defaultSettings){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_threshold);
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
        thresholdInp = dialog.findViewById(R.id.thresholdInp);

        String currentThreshold = defaultSettings.getThreshold();

        thresholdInp.setText(currentThreshold);
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
                defaultSettings.setThreshold(thresholdInp.getText().toString().trim());
                dialog.cancel();

            }
        });

    }


    public void renameDevice(DatabaseDevice databaseDevice, String id, String devName){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_rename_device);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(context, R.drawable.bg_black_transparent_10));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.slide_up; //Setting the animations to dialog
        dialog.show();

        EditText renameInp;
        TextView currentName;

        cancelBtn = dialog.findViewById(R.id.cancelBtn);
        confirmBtn = dialog.findViewById(R.id.confirmBtn);
        cancelBtnOutside = dialog.findViewById(R.id.cancelBtnTwo);

        currentName = dialog.findViewById(R.id.currentName);
        renameInp = dialog.findViewById(R.id.newDevNameInp);

        currentName.setText(devName);
        renameInp.setText(devName);
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

                databaseDevice.renameDevice(id, renameInp.getText().toString());
                dialog.cancel();

            }
        });

    }


    public void editTemplate(DatabaseObjectSize db,String id, String name, String h, String w, String t){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_changing_object_size);
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

        tempName = dialog.findViewById(R.id.tempName);
        height = dialog.findViewById(R.id.height);
        width = dialog.findViewById(R.id.width);
        thickness = dialog.findViewById(R.id.thickness);

        tempName.setText(name);
        height.setText(h);
        width.setText(w);
        thickness.setText(t);

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
                String newName= tempName.getText().toString();
                float newHeight = Float.parseFloat(height.getText().toString());
                float newWidth = Float.parseFloat(width.getText().toString());
                float newThickness = Float.parseFloat(thickness.getText().toString());
                db.updateTemplateData(id, newName, newWidth, newHeight, newThickness);
                dialog.cancel();

            }
        });

    }


    public void deleteTemplateWarning(DatabaseObjectSize db, String id){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_delete_template_warning);
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
                db.deleteTemplate(id);
                dialog.cancel();

            }
        });

    }

    public AlertDialog loadingScreen(){
        // Define a ProgressBar
        ProgressBar progressBar = new ProgressBar(context);

        // Define an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(progressBar);
        builder.setTitle("Connecting");
        builder.setMessage("Please wait...");
        builder.setCancelable(false);

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        return dialog;
    }

    public void selectDay(SensorSelectionListener listener) {
        TextView one, two, three, four, five, six, seven,
                eight, nine, ten, eleven,twelve, thirteen, fourteen,
                fifteen, sixteen,seventeen,eighteen,nineteen,twenty,twentyOne,
                twentyTwo, twentyThree,twentyFour, twentyFive,twentySix, twentySeven, twentyEight,
                twentyNine, thirty, thirtyOne;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_picker_day);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(context, R.drawable.bg_black_transparent_10));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.fade_in; //Setting the animations to dialog
        dialog.show();

        cancelBtnOutside = dialog.findViewById(R.id.cancelBtnTwo);

        one = dialog.findViewById(R.id.one);
        two= dialog.findViewById(R.id.two);
        three= dialog.findViewById(R.id.three);
        four= dialog.findViewById(R.id.four);
        five= dialog.findViewById(R.id.five);
        six= dialog.findViewById(R.id.six);
        seven= dialog.findViewById(R.id.seven);
        eight= dialog.findViewById(R.id.eight);
        nine= dialog.findViewById(R.id.nine);
        ten= dialog.findViewById(R.id.ten);
        eleven= dialog.findViewById(R.id.eleven);
        twelve= dialog.findViewById(R.id.twelve);
        thirteen= dialog.findViewById(R.id.thirteen);
        fourteen= dialog.findViewById(R.id.fourteen);
        fifteen= dialog.findViewById(R.id.fifteen);
        sixteen= dialog.findViewById(R.id.sixteen);
        seventeen= dialog.findViewById(R.id.seventeen);
        eighteen= dialog.findViewById(R.id.eighteen);
        nineteen= dialog.findViewById(R.id.nineteen);
        twenty= dialog.findViewById(R.id.twenty);
        twentyOne= dialog.findViewById(R.id.twentyOne);
        twentyTwo= dialog.findViewById(R.id.twentyTwo);
        twentyThree= dialog.findViewById(R.id.twentyThree);
        twentyFour= dialog.findViewById(R.id.twentyFour);
        twentyFive= dialog.findViewById(R.id.twentyFive);
        twentySix= dialog.findViewById(R.id.twentySix);
        twentySeven= dialog.findViewById(R.id.twentySeven);
        twentyEight= dialog.findViewById(R.id.twentyEight);
        twentyNine= dialog.findViewById(R.id.twentyNine);
        thirty= dialog.findViewById(R.id.thirty);
        thirtyOne= dialog.findViewById(R.id.thirtyOne);



        cancelBtnOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(one.getText().toString().trim());
                dialog.dismiss();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(two.getText().toString().trim());
                dialog.dismiss();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(three.getText().toString().trim());
                dialog.dismiss();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(four.getText().toString().trim());
                dialog.dismiss();
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(five.getText().toString().trim());
                dialog.dismiss();
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(six.getText().toString().trim());
                dialog.dismiss();
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(seven.getText().toString().trim());
                dialog.dismiss();
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(eight.getText().toString().trim());
                dialog.dismiss();
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(nine.getText().toString().trim());
                dialog.dismiss();
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(ten.getText().toString().trim());
                dialog.dismiss();
            }
        });
        eleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(eleven.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twelve.getText().toString().trim());
                dialog.dismiss();
            }
        });
        thirteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(thirteen.getText().toString().trim());
                dialog.dismiss();
            }
        });
        fourteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(fourteen.getText().toString().trim());
                dialog.dismiss();
            }
        });
        fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(fifteen.getText().toString().trim());
                dialog.dismiss();
            }
        });
        sixteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(sixteen.getText().toString().trim());
                dialog.dismiss();
            }
        });
        seventeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(seventeen.getText().toString().trim());
                dialog.dismiss();
            }
        });
        eighteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(eighteen.getText().toString().trim());
                dialog.dismiss();
            }
        });
        nineteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(nineteen.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twenty.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twentyOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twentyOne.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twentyTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twentyTwo.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twentyThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twentyThree.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twentyFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twentyFour.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twentyFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twentyFive.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twentySix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twentySix.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twentySeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twentySeven.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twentyEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twentyEight.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twentyNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twentyNine.getText().toString().trim());
                dialog.dismiss();
            }
        });
        thirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(thirty.getText().toString().trim());
                dialog.dismiss();
            }
        });
        thirtyOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(thirtyOne.getText().toString().trim());
                dialog.dismiss();
            }
        });
    }


    public void selectMonth(SensorSelectionListener listener) {
        TextView one, two, three, four, five, six, seven,
                eight, nine, ten, eleven,twelve;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_picker_month);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(context, R.drawable.bg_black_transparent_10));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.fade_in; //Setting the animations to dialog
        dialog.show();

        cancelBtnOutside = dialog.findViewById(R.id.cancelBtnTwo);

        one = dialog.findViewById(R.id.jan);
        two= dialog.findViewById(R.id.feb);
        three= dialog.findViewById(R.id.mar);
        four= dialog.findViewById(R.id.apr);
        five= dialog.findViewById(R.id.may);
        six= dialog.findViewById(R.id.jun);
        seven= dialog.findViewById(R.id.jul);
        eight= dialog.findViewById(R.id.aug);
        nine= dialog.findViewById(R.id.sep);
        ten= dialog.findViewById(R.id.oct);
        eleven= dialog.findViewById(R.id.nov);
        twelve= dialog.findViewById(R.id.dec);

        cancelBtnOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(one.getText().toString().trim());
                dialog.dismiss();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(two.getText().toString().trim());
                dialog.dismiss();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(three.getText().toString().trim());
                dialog.dismiss();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(four.getText().toString().trim());
                dialog.dismiss();
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(five.getText().toString().trim());
                dialog.dismiss();
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(six.getText().toString().trim());
                dialog.dismiss();
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(seven.getText().toString().trim());
                dialog.dismiss();
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(eight.getText().toString().trim());
                dialog.dismiss();
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(nine.getText().toString().trim());
                dialog.dismiss();
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(ten.getText().toString().trim());
                dialog.dismiss();
            }
        });
        eleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(eleven.getText().toString().trim());
                dialog.dismiss();
            }
        });
        twelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(twelve.getText().toString().trim());
                dialog.dismiss();
            }
        });

    }

    public void selectWeek(SensorSelectionListener listener) {
        TextView one, two, three, four, five;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_picker_week);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(context, R.drawable.bg_black_transparent_10));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.fade_in; //Setting the animations to dialog
        dialog.show();

        cancelBtnOutside = dialog.findViewById(R.id.cancelBtnTwo);

        one = dialog.findViewById(R.id.weekOne);
        two= dialog.findViewById(R.id.weekTwo);
        three= dialog.findViewById(R.id.weekThree);
        four= dialog.findViewById(R.id.weekFour);
        five= dialog.findViewById(R.id.weekFive);


        cancelBtnOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(one.getText().toString().trim());
                dialog.dismiss();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(two.getText().toString().trim());
                dialog.dismiss();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(three.getText().toString().trim());
                dialog.dismiss();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(four.getText().toString().trim());
                dialog.dismiss();
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSensorSelected(five.getText().toString().trim());
                dialog.dismiss();
            }
        });

    }
    public interface SensorSelectionListener {
        void onSensorSelected(String sensor);
    }

}
