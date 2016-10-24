package ucai.cn.fulicenter.Dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/24.
 */
public class SharePrefrenceUtils {
    private static final String SHARE_NAME = "saveUserInfo";
    private static SharePrefrenceUtils instance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor meditor;
    public static final String SHARE_KEY_USER_NAME = "share_key_user_name";

    public SharePrefrenceUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARE_NAME,context.MODE_PRIVATE);
        meditor = mSharedPreferences.edit();
    }

    public static SharePrefrenceUtils getInstance(Context context) {
        if (instance==null){
            instance = new SharePrefrenceUtils(context);
        }
        return instance;
    }
    public void saveUser(String userName){
        meditor.putString(SHARE_KEY_USER_NAME,userName);
        meditor.commit();
    }
    public String getUser(){
        return mSharedPreferences.getString(SHARE_KEY_USER_NAME,null);
    }
    public void removeUser(){
        meditor.remove(SHARE_KEY_USER_NAME);
        meditor.commit();
    }
}
