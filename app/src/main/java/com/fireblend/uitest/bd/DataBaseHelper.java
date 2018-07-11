package com.fireblend.uitest.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fireblend.uitest.entities.Contact;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private Dao<Contact, Integer> mUserDao = null;
    private static int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, "ormlite.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Contact.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Contact.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Contact, Integer> getUserDao() throws SQLException {
        if (mUserDao == null) {
            mUserDao = getDao(Contact.class);
        }
        return mUserDao;
    }

    @Override
    public void close() {
        mUserDao = null;
        super.close();
    }
}
