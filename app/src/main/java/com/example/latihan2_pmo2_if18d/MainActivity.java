package com.example.latihan2_pmo2_if18d;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;


public class MainActivity extends AppCompatActivity {

    public boolean checkPermission (String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check==PackageManager.PERMISSION_GRANTED);
    }

    EditText namafile;
    EditText isidata;
    Button btn_Read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        namafile = (EditText) findViewById(R.id.namefile);
        isidata = (EditText) findViewById(R.id.data);
        btn_Read = (Button) findViewById(R.id.btnRead);
        btn_Read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, read_data.class);
                startActivity(i);
            }
        });

    }

    private boolean isExternalStorageWriteable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.i("Status","Oke Data Dapat Dibuat");
            return true;

        }else{
            return false;
        }
    }
    public void writedata (Context context){
        if(isExternalStorageWriteable() && checkPermission (Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            File datatext = new File (context.getExternalFilesDir(null), namafile.getText().toString());

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(datatext);
                fileOutputStream.write(isidata.getText().toString().getBytes(Charset.forName("UTF-8")));
                Toast.makeText(context, String.format("Penulisan Data di %s Berhasil", namafile.getText().toString()), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(context, String.format("Penulisan Data di %s Gagal", namafile.getText().toString()), Toast.LENGTH_SHORT).show();
        }
    }

    public void BuatData (View view){
        writedata(getApplicationContext());
    }
}
