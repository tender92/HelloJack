package com.tender.hellojack.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.tender.tools.TenderLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boyu on 2018/2/2.
 */

public class LocalDicHelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "sample_oxford";
    private static final int DATABASE_VERSION = 1;
    public static final String COL_WORD = "word";

    public LocalDicHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static List<String> getLocalDic(Context context) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase reader = new LocalDicHelper(context).getReadableDatabase();
        reader.beginTransaction();
        try {
            Cursor c = reader.query(LocalDicHelper.DATABASE_NAME, new String[]{COL_WORD},
                    null, null, null, null, null);
            while (c.moveToNext()) {
                list.add(c.getString(c.getColumnIndex(LocalDicHelper.COL_WORD)));
            }
            reader.setTransactionSuccessful();
        } catch (Exception e) {
            TenderLog.e(e.getMessage());
        } finally {
            reader.endTransaction();
            reader.close();
        }
        return list;
    }
}
