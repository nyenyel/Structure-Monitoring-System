package com.example.structuremonitoringsystem.Excel;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
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
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

public class ExportDataToExcel {
    private Context context;
    private HSSFWorkbook wrkBk;
    HSSFSheet sheet;
    private String path;
    private int c = 0;

    public ExportDataToExcel(Context context) {
        this.context = context;
        this.path = Environment.getExternalStorageDirectory() + "/Download/data"+c+".xls";
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

    private void sheetRowData(HSSFRow row, int nthCell, String input) {
        HSSFCell cellValue = row.createCell(nthCell);
        cellValue.setCellValue(input);
    }

    private void saveData(HSSFWorkbook workbook, String filePath) {
        FileOutputStream fileOut = null;
        Log.e("inserted", "5");
        try {
            // Ensure external storage is writable
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                // Create a new file output stream
                Log.e("inserted", "6");
                fileOut = new FileOutputStream(new File(filePath));
                Log.e("inserted", "7");
                // Write the workbook content to the output stream
                workbook.write(fileOut);
                Log.e("inserted", "8");
                // Close the output stream
                fileOut.close();

                // Optional: Display a message indicating successful saving
                Log.e("Data Result", "Workbook saved successfully at: " + filePath);
            } else {
                // Handle the case where external storage is not writable
                Log.e("SaveData", "External storage not writable.");
            }
        } catch (IOException e) {
            Log.e("inserted", "9");
            e.printStackTrace();
        } finally {
            // Ensure the output stream is closed in case of an exception
            if (fileOut != null) {
                try {
                    Log.e("inserted", "10");
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("inserted", "11");
                }
            }
        }
    }

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private void requestWriteExternalStoragePermission(HSSFWorkbook workbook) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Log.e("inserted", "1");
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.e("inserted", "2");
                // Show an explanation to the user asynchronously if needed
                // You can show a dialog or a message explaining why you need this permission
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);
                Log.e("inserted", "3");
            }
        } else {

            // Permission already granted; proceed with the operation
            // Call your method for saving the Excel file here
            Log.e("inserted", "4");
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


    public void exportData(Cursor cursor, int num) {
        String COL_YEAR = "_year";
        String COL_MONTH ="_month";
        String COL_WEEK ="_week";
        String COL_DAY ="_day";
        String COL_TIME = "_time";
        String COL_XYZ_DATA = "xyz_data";
        String months[] = new String[]{
                "JAN","FEB","MAR","APR",
                "MAY","JUN","JUL","AUG",
                "SEP","OCT","NOV","DEC"
        };

        String xyzData[] = new String[]{
                "","","",
                "","","",

                "","","",
                "","","",
        };
        String time, week,xyzDatas;
        int year, month, day;
        int count = 0;
        try (HSSFWorkbook hssfWorkbook = new HSSFWorkbook()) {

            sheet = generateSheet(hssfWorkbook, num);

            count++;
            try {
                if (cursor != null && cursor.moveToFirst()){
                    do {

                        year = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COL_YEAR)));
                        month = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COL_MONTH)));
                        week = cursor.getString(cursor.getColumnIndexOrThrow(COL_WEEK));
                        day = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COL_DAY)));
                        time = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME));
                        xyzDatas = cursor.getString(cursor.getColumnIndexOrThrow(COL_XYZ_DATA));
                        String temp[] = xyzDatas.split(",");
                        // Iterate over both arrays simultaneously
                        for (int i = 0; i < temp.length && i < xyzData.length; i++) {
                            // Replace empty strings in xyzData with corresponding non-empty strings from temp
                            if (!temp[i].isEmpty()) {
                                xyzData[i] = temp[i];
                            }
                        }


                        if (count <= 60000-1) {
                            HSSFRow rowData = sheet.createRow(count);
                            sheetRowData(rowData, 0, year + months[month - 1] + day);
                            sheetRowData(rowData, 1, week);
                            sheetRowData(rowData, 2, time);
                            sheetRowData(rowData, 3, xyzData[0]);
                            sheetRowData(rowData, 4, xyzData[1]);
                            sheetRowData(rowData, 5, xyzData[2]);

                            sheetRowData(rowData, 7, year+months[month]+day);
                            sheetRowData(rowData, 8, week);
                            sheetRowData(rowData, 9, time);
                            sheetRowData(rowData, 10, xyzData[3]);
                            sheetRowData(rowData, 11, xyzData[4]);
                            sheetRowData(rowData, 12, xyzData[5]);
                        } else {
                            // Save the current workbook
                            // Return the remaining cursor data
                            num++;
                            sheet = generateSheet(hssfWorkbook, num);
                            count = 0;
                        }


//                        Log.e("data", temp[0] +temp[1] +temp[2] +temp[3] +temp[4] +temp[5] );
                        count++;

                    }while (cursor.moveToNext());
                }
            }finally {
                if (cursor != null){
                    cursor.close();}
            }

            wrkBk = hssfWorkbook;
            requestWriteExternalStoragePermission(wrkBk);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HSSFSheet generateSheet(HSSFWorkbook hssfWorkbook, int num) {

        Log.e("Sheet Number", "Sheet Number" + num );

        int rowCount = 0;
        HSSFSheet sheet = hssfWorkbook.createSheet("Data Log "+num);

        HSSFRow rowHeader = sheet.createRow(0);

        HSSFCell dataSaved = rowHeader.createCell(0);
        dataSaved.setCellValue("Date");

        HSSFCell weekSaved = rowHeader.createCell(1);
        weekSaved.setCellValue("Week");

        HSSFCell timeSaved = rowHeader.createCell(2);
        timeSaved.setCellValue("Time");

        HSSFCell xValueCell = rowHeader.createCell(3);
        xValueCell.setCellValue("X Axis");

        HSSFCell yValueCell = rowHeader.createCell(4);
        yValueCell.setCellValue("Y Axis");

        HSSFCell zValueCell = rowHeader.createCell(5);
        zValueCell.setCellValue("Z Axis");

        HSSFCell dataSaved2 = rowHeader.createCell(7);
        dataSaved2.setCellValue("Date");

        HSSFCell weekSaved2 = rowHeader.createCell(8);
        weekSaved2.setCellValue("Week");

        HSSFCell timeSaved2 = rowHeader.createCell(9);
        timeSaved2.setCellValue("Time");

        HSSFCell xValueCell2 = rowHeader.createCell(10);
        xValueCell2.setCellValue("X Axis");

        HSSFCell yValueCell2 = rowHeader.createCell(11);
        yValueCell2.setCellValue("Y Axis");

        HSSFCell zValueCell2 = rowHeader.createCell(12);
        zValueCell2.setCellValue("Z Axis");
        return sheet;
    }
    private Cursor getRemainingCursorData(Cursor cursor) {
        // Move the cursor to the next row if available
        if (cursor != null && !cursor.isClosed() && !cursor.isAfterLast()) {
            cursor.moveToNext();
        }

        return cursor;
    }
}
