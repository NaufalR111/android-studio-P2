package com.example.latihan2_pmo2_if18d;


import android.content.Context;
import android.os.Environment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class read_data extends AppCompatActivity {

    EditText nameFile;
    EditText isiData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);
        nameFile = (EditText)findViewById(R.id.nameFile);
        isiData = (EditText)findViewById(R.id.Data);
    }
    private boolean isExternalStorageReadable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
        Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())){
            Log.i("Status","Oke, Data Bisa Terbaca");
            return true;
        }else{
            return false;
        }

    }
    public void readData (Context context){
        if(isExternalStorageReadable()){
            StringBuilder stringBuilder = new StringBuilder();
            try{
                File dataText = new File(context.getExternalFilesDir(null), nameFile.getText().toString());
                FileInputStream fileInputStream = new FileInputStream(dataText);

                if (fileInputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
                    List<String> lines = new ArrayList<String>();
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line = reader.readLine();
                    while (line != null) {
                        lines.add(line);
                        line = reader.readLine();
                    }
                    isiData.setText(TextUtils.join("\n", lines));
                    Toast.makeText(context, String.format("Baca Data di %s Berhasil", nameFile.getText().toString()), Toast.LENGTH_SHORT).show();
                }
                } catch (Exception e ) {
                    e.printStackTrace();

                    isiData.setText("");
                }
        }else{
            Toast.makeText(context, String.format("Baca Data dari file %s Gagal", nameFile.getText().toString()), Toast.LENGTH_SHORT).show();
        }
    }
    public void bacafile (View view){
        readData(getApplicationContext());
    }
}