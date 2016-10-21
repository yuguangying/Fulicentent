package ucai.cn.fulicenter.utils;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.bean.ResultBean;


public class MyResultBeanUtils {
    public static <T> ResultBean getResultBeanFromJson(String jsonStr, Class<T> clazz) {
        ResultBean ResultBean = new ResultBean();
        try {
            if (jsonStr == null || jsonStr.isEmpty() || jsonStr.length() < 3) return null;
            JSONObject jsonObject = new JSONObject(jsonStr);
            if (!jsonObject.isNull("retCode")) {
                ResultBean.setRetCode(jsonObject.getInt("retCode"));
            } else if (!jsonObject.isNull("msg")) {
                ResultBean.setRetCode(jsonObject.getInt("msg"));
            }
            if (!jsonObject.isNull("retMsg")) {
                ResultBean.setRetMsg(jsonObject.getBoolean("retMsg"));
            } else if (!jsonObject.isNull("ResultBean")) {
                ResultBean.setRetMsg(jsonObject.getBoolean("result"));
            }
            if (!jsonObject.isNull("retData")) {
                JSONObject jsonRetData = jsonObject.getJSONObject("retData");
                if (jsonRetData != null) {
                    Log.e("Utils", "jsonRetData=" + jsonRetData);
                    String date;
                    try {
                        date = URLDecoder.decode(jsonRetData.toString(), I.UTF_8);
                        Log.e("Utils", "jsonRetData=" + date);
                        T t = new Gson().fromJson(date, clazz);
                        ResultBean.setRetData(t);
                        return ResultBean;

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                        T t = new Gson().fromJson(jsonRetData.toString(), clazz);
                        ResultBean.setRetData(t);
                        return ResultBean;
                    }
                }
            } else {
                if (jsonObject != null) {
                    Log.e("Utils", "jsonRetData=" + jsonObject);
                    String date;
                    try {
                        date = URLDecoder.decode(jsonObject.toString(), I.UTF_8);
                        Log.e("Utils", "jsonRetData=" + date);
                        T t = new Gson().fromJson(date, clazz);
                        ResultBean.setRetData(t);
                        return ResultBean;

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                        T t = new Gson().fromJson(jsonObject.toString(), clazz);
                        ResultBean.setRetData(t);
                        return ResultBean;
                    }
                }
            }
            return ResultBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> ResultBean getListResultBeanFromJson(String jsonStr, Class<T> clazz) {
        ResultBean ResultBean = new ResultBean();
        Log.e("Utils", "jsonStr=" + jsonStr);
        try {
            if (jsonStr == null || jsonStr.isEmpty() || jsonStr.length() < 3) return null;
            JSONObject jsonObject = new JSONObject(jsonStr);
            if (!jsonObject.isNull("retCode")) {
                ResultBean.setRetCode(jsonObject.getInt("retCode"));
            } else if (!jsonObject.isNull("msg")) {
                ResultBean.setRetCode(jsonObject.getInt("msg"));
            }
            if (!jsonObject.isNull("retMsg")) {
                ResultBean.setRetMsg(jsonObject.getBoolean("retMsg"));
            } else if (!jsonObject.isNull("ResultBean")) {
                ResultBean.setRetMsg(jsonObject.getBoolean("result"));
            }
            if (!jsonObject.isNull("retData")) {
                JSONArray array = jsonObject.getJSONArray("retData");
                if (array != null) {
                    List<T> list = new ArrayList<T>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonGroupAvatar = array.getJSONObject(i);
                        T ga = new Gson().fromJson(jsonGroupAvatar.toString(), clazz);
                        list.add(ga);
                    }
                    ResultBean.setRetData(list);
                    return ResultBean;
                }
            } else {
                JSONArray array = new JSONArray(jsonStr);
                if (array != null) {
                    List<T> list = new ArrayList<T>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonGroupAvatar = array.getJSONObject(i);
                        T ga = new Gson().fromJson(jsonGroupAvatar.toString(), clazz);
                        list.add(ga);
                    }
                    ResultBean.setRetData(list);
                    return ResultBean;
                }
            }
            return ResultBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}


