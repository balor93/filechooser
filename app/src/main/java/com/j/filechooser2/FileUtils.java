package com.j.filechooser2;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import java.net.URISyntaxException;

public class FileUtils {

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");

                if (cursor.moveToFirst()) {
                    Log.d("DDD", "Display Name: " + cursor.getString(column_index));
                    if(cursor.getString(column_index)==null){
                       //to get filepath in th download
                        final String id = DocumentsContract.getDocumentId(uri);
                        final Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));


                        cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
                        column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        if(cursor.getString(column_index)==null){
                           //toast
                        }
                    }
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                Log.d("FILEUTILS","Error "+e);
                Toast.makeText(context,"Error getpath fileutils "+e,Toast.LENGTH_SHORT).show();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }





}
