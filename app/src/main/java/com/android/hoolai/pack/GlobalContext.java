package com.android.hoolai.pack;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.hoolai.pack.utils.LogUtil;
import com.hoolai.access.greendao.dao.DaoMaster;
import com.hoolai.access.greendao.dao.DaoSession;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public final class GlobalContext extends Application {

    public static final String TAG = "Hoolai_package";
    private static Context globalContext;
    public static GlobalContext application;
    // 数据库相关
    private static DaoSession daoSession;
    private SQLiteDatabase db;
    // 所以Activity列表（退出时使用）
    private List<Activity> activityList = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        globalContext = getApplicationContext();
        /**
         * 仅仅是缓存Application的Context，不耗时
         */
        FileDownloader.init(globalContext);

        setupDatabase();
    }

    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static Context getGlobalContext() {
        return globalContext;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : activityList) {
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            LogUtil.e(e.getMessage(), e);
        } finally {
            System.exit(0);
        }
    }
}
