package com.tender.tools.utils.file;

import android.content.res.AssetManager;

import com.tender.tools.TenderLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by boyu on 2017/11/7.
 */

public class FileUtil {

    /**
     * 拷贝assets资源文件
     * @param assets
     * @param source
     * @param dest
     * @param isCover 是否覆盖已存在的目标文件
     */
    public static void copyFromAssets(AssetManager assets, String source, String dest, boolean isCover) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = assets.open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                TenderLog.e(e.getMessage());
            } catch (IOException e) {
                TenderLog.e(e.getMessage());
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        TenderLog.e(e.getMessage());
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    TenderLog.e(e.getMessage());
                }
            }
        }
    }
}
