package com.example.smartvest;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyTextReader {
    
    public String readTextFile(Context context, int resId) {
        String result = "";
        InputStream txtResource = context.getResources().openRawResource(resId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = txtResource.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = txtResource.read();
            }
            result = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            txtResource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.trim();
    }
}
