package com.deitel.recycleviewtest.util;

import android.os.Environment;

public class FileUtils {
    public static String getDownloadDir() {
        return Environment.getExternalStorageDirectory().getPath() + "/RecycleViewTest/";
    }
}
