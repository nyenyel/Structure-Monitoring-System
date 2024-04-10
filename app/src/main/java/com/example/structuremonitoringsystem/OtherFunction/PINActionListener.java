package com.example.structuremonitoringsystem.OtherFunction;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;

import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDefaultSettings;
import com.example.structuremonitoringsystem.R;
import com.example.structuremonitoringsystem.RealtimeMonitoring;
import com.example.structuremonitoringsystem.SelectDefaultBluetooth;
import com.example.structuremonitoringsystem.Settings;

public class PINActionListener {



    Context context;
    View view;
    Popups popups;
    Class aClass;
    TextView instruction;
    ImageView circle;
    String currentPIN = "";
    BluetoothManager bluetoothManager;
    private int tries = 0;
    private int counter = 0;
    private int code = 0;
    private String newPIN = "";

    private String instructions[] = new String[]{
            "Please enter current PIN code",
            "Please enter new PIN code",
            "Please retype the PIN code"
    };

    public PINActionListener(Context context, View view) {
        popups = new Popups(context);
        this.context = context;
        this.view = view;
    }

    public PINActionListener(Context context, View view, int code) {
        popups = new Popups(context);
        this.context = context;
        this.view = view;
        this.code = code;
    }




    public void createPIN(String input, android.bluetooth.BluetoothAdapter bluetoothAdapter){

//      android.bluetooth.BluetoothAdapter bluetoothAdapter   = bluetoothManager.getAdapter();

        if(currentPIN.length() < 4 ) {
            currentPIN = currentPIN + input;
        }

        if(currentPIN.length() == 4){
            DatabaseDefaultSettings settings = new DatabaseDefaultSettings(context);
            boolean pinIsCorrect = settings.PINIsCorrect(currentPIN);
            if (code == 69) {
                if(pinIsCorrect){
                    if (!bluetoothAdapter.isEnabled()) {
                        for(int x = 0; x< 4; x++){
                            backspace();
                            circle = getCircleToFill(currentPIN);
                            fillVisibility(circle, View.INVISIBLE);
                        }
                        popups.bluetoothFailed();

                    }else {
                        Intent intent = new Intent(context, RealtimeMonitoring.class);
                        context.startActivity(intent);
                    }
                    return;
                }else {
                    popups.incorrectPINPopupWindow();
                }
            }
            if(counter == 0){
                if (pinIsCorrect){
                    instruction = view.findViewById(R.id.instruction);
                    instruction.setText(instructions[1]);
                    counter++;
                }
                else{
                    tries++;
                    popups.incorrectPINPopupWindow();
                }
            } else if (counter == 1) {
                newPIN = currentPIN;
                instruction = view.findViewById(R.id.instruction);
                instruction.setText(instructions[2]);
                counter++;
            }else{
                if(currentPIN.equals(newPIN)){
                    settings.changePIN(currentPIN);
                    Intent intent = new Intent(context, Settings.class);
                    Toast.makeText(context, "PIN code is updated", Toast.LENGTH_LONG).show();
                    context.startActivity(intent);
                }else {
                    popups.incorrectPINPopupWindow();
                }
            }
            for(int x = 0; x< 4; x++){
                backspace();
                circle = getCircleToFill(currentPIN);
                fillVisibility(circle, View.INVISIBLE);
            }

        }

    }
    public void backspace() {
        if(!currentPIN.isEmpty()){
            currentPIN = currentPIN.substring(0, currentPIN.length() - 1);
        }
    }

    public void fillVisibility(ImageView circleFill, int visibility){
        circleFill.setVisibility(visibility);
    }

    public ImageView getCircleToFill(String pin){
        int charLength = pin.length();
        ImageView circle;
        if (charLength==0){
            circle = view.findViewById(R.id.pinOneFill);
        }
        else if (charLength==1){
            circle = view.findViewById(R.id.pinTwoFill);
        }
        else if (charLength==2){
            circle = view.findViewById(R.id.pinThreeFill);
        }else {
            circle = view.findViewById(R.id.pinFourFill);
        }
        return circle;
    }

    public String getCurrentPIN() {
        return currentPIN;
    }

}
