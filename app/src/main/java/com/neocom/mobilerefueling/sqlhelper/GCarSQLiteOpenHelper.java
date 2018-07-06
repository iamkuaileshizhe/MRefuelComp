package com.neocom.mobilerefueling.sqlhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.neocom.mobilerefueling.gen.DaoMaster;
import com.neocom.mobilerefueling.gen.GCarListBeanDao;

/**
 * Created by admin on 2017/9/4.
 */

public class GCarSQLiteOpenHelper extends DaoMaster.OpenHelper {
    public GCarSQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public GCarSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);

        //        if (oldVersion == newVersion) {
//            Log.e("onUpgrade", "数据库是最新版本,无需升级" );
//            return;
//        }
//        Log.e("onUpgrade", "数据库从版本" + oldVersion + "升级到版本" + newVersion);
//        switch (oldVersion) {
//            case 1:
//                String sql = "";
//                db.execSQL(sql);
//            case 2:
//            default:
//                break;
//        }

        //将所有生成的dao类传入
//        MigrationHelper.migrate(db,GCarListBean.class);

        MigrationHelper.migrate(db, GCarListBeanDao.class);

    }
}
