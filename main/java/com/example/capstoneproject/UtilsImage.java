package com.example.capstoneproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UtilsImage {
    public static Bitmap getImage(byte[] img)
    {
        return BitmapFactory.decodeByteArray(img,0, img.length);
    }
    public static byte[] getBytes(InputStream ip) throws IOException
    {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize=1024;
        byte buffer[]=new byte[bufferSize];
        int len=0;
        while((len=ip.read(buffer))!= -1)
        {
            byteBuffer.write(buffer,0,len);
        }
        return byteBuffer.toByteArray();
    }
}
