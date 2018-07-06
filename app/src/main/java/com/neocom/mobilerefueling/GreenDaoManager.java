package com.neocom.mobilerefueling;


import com.neocom.mobilerefueling.gen.DaoMaster;
import com.neocom.mobilerefueling.gen.DaoSession;
import com.neocom.mobilerefueling.sqlhelper.GCarSQLiteOpenHelper;
import com.neocom.mobilerefueling.utils.UIUtils;

import org.greenrobot.greendao.database.Database;

/**
 * Created by kaifa on 2017/6/28.
 */

public class GreenDaoManager {
    private static final String DB_NAME = "greendao";

    private static GreenDaoManager mInstance;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    private GreenDaoManager() {
        if (mInstance == null) {
            GCarSQLiteOpenHelper helper = new GCarSQLiteOpenHelper(UIUtils.getContext(), DB_NAME, null);
            Database db = helper.getWritableDb();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

}
