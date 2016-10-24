package ucai.cn.fulicenter.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ucai.cn.fulicenter.bean.UserAvatar;

/**
 * Created by Administrator on 2016/10/24.
 */
public class DBMenager {
    private static DBMenager dBmenager = new DBMenager();
    private DBOpenHelp dbOpenHelp;


    public synchronized void colseDB() {
        if (dbOpenHelp != null) {
            dbOpenHelp.colseDB();
        }

    }

    public boolean saveUser(UserAvatar user) {
        SQLiteDatabase db = dbOpenHelp.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserDao.USER_COLUMN_NAME, user.getMuserName());
        cv.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
        cv.put(UserDao.USER_COLUMN_AVATAR_ID, user.getMavatarId());
        cv.put(UserDao.USER_COLUMN_AVATAR_PATH, user.getMavatarPath());
        cv.put(UserDao.USER_COLUMN_AVATAR_TYPE, user.getMavatarType());
        cv.put(UserDao.USER_COLUMN_AVATAR_SUFFIX, user.getMavatarSuffix());
        cv.put(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME, user.getMavatarLastUpdateTime());
        if (db.isOpen()) {
            return db.replace(UserDao.USER_TABLE_NAME, null, cv) != -1;
        }
        return false;
    }

    public static synchronized DBMenager getInstance() {
        return dBmenager;
    }

    void onInit(Context context) {
        dbOpenHelp = new DBOpenHelp(context);
    }

    public synchronized UserAvatar getUser(String username) {
        SQLiteDatabase db = dbOpenHelp.getWritableDatabase();
        String sql = "select * from " + UserDao.USER_TABLE_NAME + " where "
                +UserDao.USER_COLUMN_NAME + " =?";
        UserAvatar user = null;
        Cursor cursor = db.rawQuery(sql,new String[]{username});
        if (cursor.moveToNext()){
            user = new UserAvatar();
            user.setMuserName(username);
            user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_NICK)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_ID)));
            user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE)));
            user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH)));
            user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX)));
            user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME)));
            return user;
        }
        return user;
    }

    public synchronized boolean updateUser(UserAvatar user) {
        int result = -1;
        SQLiteDatabase db = dbOpenHelp.getWritableDatabase();
        String sql = UserDao.USER_COLUMN_NAME + "=?";
        ContentValues cv = new ContentValues();
        cv.put(UserDao.USER_COLUMN_NICK,user.getMuserNick());
        if (db.isOpen()){
            result = db.update(UserDao.USER_TABLE_NAME,cv,sql,new String[]{user.getMuserName()});
        }
        return result>0;
    }
}
