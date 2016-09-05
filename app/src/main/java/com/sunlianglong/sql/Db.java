package com.sunlianglong.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sun liang long on 2016/3/24.
 */
public class Db extends SQLiteOpenHelper {


    public Db(Context context) {
        //CursorFactory factory  ：从数据库中查询出结果的对象，相当于指针，一行一行向下移动，读取
        super(context,"db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库内部结构，比如：表
        db.execSQL("CREATE TABLE user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT DEFAULT \"\"," +
                "sex TEXT DEFAULT \"\")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //升级数据库版本

    }
}
