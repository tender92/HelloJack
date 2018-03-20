package com.tender.tools.utils.file;

import android.content.Context;
import android.text.TextUtils;

import com.tender.tools.TenderLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by boyu on 2018/3/6.
 */

public class FileCache {
    private static final String KEY_CACHE_DIRECTORY = "CACHE_DIRECTORY";

    public File cacheFileOnDisk(Context context, String fileName, byte[]data) {
        File cacheParent = checkCacheParentDirectory(context);
        if(cacheParent != null){
            return saveFile(cacheParent, fileName, data);
        }
        return null;
    }

    public File saveFile(File parent, String fileName, byte[]data){
        if(!TextUtils.isEmpty(fileName)) {
            File file = new File(parent, fileName);
            saveBytesToFile(data, file);
            return file;
        }
        return null;
    }

    public FileCache() {
    }

    private void saveBytesToFile(byte[]data, File destFile){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destFile);
            fos.write(data);
           TenderLog.i("save " + destFile.getName() + " successfully!");
        } catch (IOException e) {
            TenderLog.e(e.getMessage());
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    TenderLog.e(e.getMessage());
                }
            }
        }
    }

    public File getCacheFileByUrl(Context context, String fileName){
        File cacheParent = checkCacheParentDirectory(context);
        if(!TextUtils.isEmpty(fileName) && cacheParent != null && cacheParent.isDirectory()){
            return new File(cacheParent,fileName);
        }
        return null;
    }

    private File checkCacheParentDirectory(Context context){
        File cacheFileDir = new File(context.getCacheDir(), KEY_CACHE_DIRECTORY);
        if (!cacheFileDir.exists()) {
            if (cacheFileDir.mkdirs()) {
                return cacheFileDir;
            }
        } else {
            return cacheFileDir;
        }
        return null;
    }
}
