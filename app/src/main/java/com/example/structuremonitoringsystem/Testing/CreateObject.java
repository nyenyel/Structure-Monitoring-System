package com.example.structuremonitoringsystem.Testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDefaultSettings;
import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.example.structuremonitoringsystem.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class CreateObject extends AppCompatActivity {

    private DatabaseObjectSize databaseObjectSize;
    private DatabaseDefaultSettings pin;
    private AppCompatButton createObj , getData, cPIN, uPIN, chkPIN;
    private EditText height,width,thickness,tempName;

    private NavigationView navbarView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private static ArrayList<String> template_name, _id;
    private static ArrayList<Float> _height, _width, _thickness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing_create_object);

        databaseObjectSize = new DatabaseObjectSize(CreateObject.this);
        pin = new DatabaseDefaultSettings(CreateObject.this);

        template_name = new ArrayList<>();
        _id = new ArrayList<>();
        _width = new ArrayList<>();
        _height = new ArrayList<>();
        _thickness = new ArrayList<>();

        createObj = (AppCompatButton) findViewById(R.id.createObj);
        getData = (AppCompatButton) findViewById(R.id.getData);

        cPIN = (AppCompatButton) findViewById(R.id.createPIN);
        uPIN = (AppCompatButton) findViewById(R.id.updatePIN);
        chkPIN = (AppCompatButton) findViewById(R.id.checkPIN);

        tempName = (EditText) findViewById(R.id.tempName);
        height = (EditText) findViewById(R.id.height);
        width = (EditText) findViewById(R.id.width);
        thickness = (EditText) findViewById(R.id.thickness);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navbarView = (NavigationView) findViewById(R.id.navbarView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        NavigationBar navigationBar = new NavigationBar(drawerLayout, navbarView, toolbar);
        navigationBar.setNavbar(this);

//        storeData();

        createObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String getTemplateName = tempName.getText().toString();
                    float getHeight = Float.parseFloat(height.getText().toString());
                    float getWidth = Float.parseFloat(width.getText().toString());
                    float getThickness = Float.parseFloat(thickness.getText().toString());

                    Intent intent = new Intent(CreateObject.this, MainActivity.class);
                    intent.putExtra("height", getHeight);
                    intent.putExtra("width", getWidth);
                    intent.putExtra("thickness", getThickness);


                    databaseObjectSize.addTemplate(getTemplateName, getWidth, getHeight, getThickness);
//                    databaseObjectSize.updateTemplateData("2",getTemplateName, getWidth, getHeight, getThickness);
//                    databaseObjectSize.deleteTemplate("1");

                    height.setText("");
                    width.setText("");
                    thickness.setText("");
                    startActivity(intent);

                }catch (NumberFormatException e){
                    Log.e("Error", "Please Enter Data");
                }

            }
        });

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                storeSingleData("2");
                Intent intent = new Intent(CreateObject.this, LineChartView.class);
                startActivity(intent);
            }
        });

        cPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getPIN = tempName.getText().toString();
                pin.createPIN(getPIN);
            }
        });

        uPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getPIN = tempName.getText().toString();
                pin.seedDefaultData();
            }
        });

        chkPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getPIN = tempName.getText().toString();
                if(pin.PINIsCorrect(getPIN)){
                    Log.e("PIN Check", "PIN correct");
                }else {Log.e("PIN Check", "PIN incorrect");}
            }
        });
    }

    private void storeData(){
        Cursor cursor = databaseObjectSize.readAllData();
        if(cursor.getCount() == 0){
            Log.e("Database Status", "Empty");
        }else{
            while (cursor.moveToNext()){
                _id.add(cursor.getString(0));
                template_name.add(cursor.getString(1));
                _height.add(Float.parseFloat(cursor.getString(2)));
                _width.add(Float.parseFloat(cursor.getString(3)));
                _thickness.add(Float.parseFloat(cursor.getString(4)));
            }
        }
    }

    private void storeSingleData(String id){
        Cursor cursor = databaseObjectSize.getSpecificDataById(id);
        if(cursor.getCount() == 0){
            Log.e("Database Status", "Empty");
        }else{
            while (cursor.moveToNext()){
                _id.add(cursor.getString(0));
                template_name.add(cursor.getString(1));
                _height.add(Float.parseFloat(cursor.getString(2)));
                _width.add(Float.parseFloat(cursor.getString(3)));
                _thickness.add(Float.parseFloat(cursor.getString(4)));
            }
        }
    }
}
