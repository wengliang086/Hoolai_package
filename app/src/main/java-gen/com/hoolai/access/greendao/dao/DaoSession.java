package com.hoolai.access.greendao.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.hoolai.access.greendao.bean.DbUser;

import com.hoolai.access.greendao.dao.DbUserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dbUserDaoConfig;

    private final DbUserDao dbUserDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dbUserDaoConfig = daoConfigMap.get(DbUserDao.class).clone();
        dbUserDaoConfig.initIdentityScope(type);

        dbUserDao = new DbUserDao(dbUserDaoConfig, this);

        registerDao(DbUser.class, dbUserDao);
    }
    
    public void clear() {
        dbUserDaoConfig.getIdentityScope().clear();
    }

    public DbUserDao getDbUserDao() {
        return dbUserDao;
    }

}