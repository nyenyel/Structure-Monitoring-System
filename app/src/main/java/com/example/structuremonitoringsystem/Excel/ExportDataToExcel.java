package com.example.structuremonitoringsystem.Excel;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.data.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExportDataToExcel {
    private Context context;
    private HSSFWorkbook wrkBk;
    private String path;

    public ExportDataToExcel(Context context) {
        this.context = context;
        this.path = Environment.getExternalStorageDirectory() + "/Download/data.xls";
    }

    public void exportData(ArrayList<Entry> data) {
        try (HSSFWorkbook hssfWorkbook = new HSSFWorkbook()) {
            int count = 0;
            HSSFSheet sheet = hssfWorkbook.createSheet("Data Log");

            HSSFRow rowHeader = sheet.createRow(count);

            HSSFCell xValueCell = rowHeader.createCell(0);
            xValueCell.setCellValue("x");

            HSSFCell yValueCell = rowHeader.createCell(1);
            yValueCell.setCellValue("y");
            count++;

            for (Entry entry : data) {
                HSSFRow rowData = sheet.createRow(count);
                float xValue = entry.getX();
                float yValue = entry.getY();

                sheetRowData(rowData, 0, xValue);
                sheetRowData(rowData, 1, yValue);
                count++;
            }
            wrkBk = hssfWorkbook;
            requestWriteExternalStoragePermission(wrkBk);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("yValues", data + "");
    }

    private void sheetRowData(HSSFRow row, int nthCell, float input) {
        HSSFCell cellValue = row.createCell(nthCell);
        cellValue.setCellValue(input);
    }

    private void saveData(HSSFWorkbook workbook, String filePath) {
        FileOutputStream fileOut = null;
        try {
            // Ensure external storage is writable
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                // Create a new file output stream
                fileOut = new FileOutputStream(new File(filePath));

                // Write the workbook content to the output stream
                workbook.write(fileOut);

                // Close the output stream
                fileOut.close();

                // Optional: Display a message indicating successful saving
                Log.e("Data Result", "Workbook saved successfully at: " + filePath);
            } else {
                // Handle the case where external storage is not writable
                Log.e("SaveData", "External storage not writable.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Ensure the output stream is closed in case of an exception
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private void requestWriteExternalStoragePermission(HSSFWorkbook workbook) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user asynchronously if needed
                // You can show a dialog or a message explaining why you need this permission
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            // Permission already granted; proceed with the operation
            // Call your method for saving the Excel file here
            saveData(workbook, path);
        }
    }

    // Handle permission result (called from the corresponding Activity)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted; proceed with the operation
                // Call your method for saving the Excel file here
                saveData(wrkBk, path);
            } else {
                // Permission denied; handle the denied permission case
                // You might want to inform the user that the operation cannot be performed without the permission
            }
        }
    }

}
