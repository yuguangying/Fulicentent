package ucai.cn.fulicenter.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ucai.cn.fulicenter.I;


public class DBOpenHelp extends SQLiteOpenHelper {

    private static final String FULICENTER_USER_TABLE_NAME = "CREATE TABLE "
            + UserDao.USER_TABLE_NAME + " ("
            + UserDao.USER_COLUMN_NAME + " TEXT PRIMARY KEY, "
            + UserDao.USER_COLUMN_NICK + " TEXT, "
            + UserDao.USER_COLUMN_AVATAR_ID + " INTEGER, "
            + UserDao.USER_COLUMN_AVATAR_TYPE + " INTEGER, "
            + UserDao.USER_COLUMN_AVATAR_PATH + " TEXT, "
            + UserDao.USER_COLUMN_AVATAR_SUFFIX + " TEXT, "
            + UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME + " TEXT);";
    private static DBOpenHelp instance;

    private static final int DB_VERSION = 1;

    public static DBOpenHelp getInstance(Context context) {
        if (instance == null) {
            instance = new DBOpenHelp(context.getApplicationContext());
        }
        return instance;
    }


    public DBOpenHelp(Context context) {
        super(context, getDBName(), null, DB_VERSION);
    }

    public static String getDBName() {
        return I.User.TABLE_NAME + "_demo.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FULICENTER_USER_TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void colseDB() {
        if (instance != null) {
            SQLiteDatabase db = instance.getWritableDatabase();
            db.close();
            instance = null;
        }
    }


}
