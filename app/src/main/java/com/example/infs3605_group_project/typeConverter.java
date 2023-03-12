package com.example.infs3605_group_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class typeConverter {

    /* This is a type converter class that tells the Room database how to convert the
     "eventStartDate" type to a format that can be stored in the database.
     */

        @TypeConverter
        public static Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public static Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }

    /* Creates a converter that can convert between the image format and the binary
    format used by the database.
     */

    @TypeConverter
    public static byte[] fromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @TypeConverter
    public static Bitmap toBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}


