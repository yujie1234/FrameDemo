package com.android.common.okgo;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by KZJ on 2017/10/20.
 */

public class ParamsUtil {
    public static JSONObject getParam(Map map) {
        JSONObject object = new JSONObject(map);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("params", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getParam(Object ob) {
        String str = new Gson().toJson(ob);
        JSONObject object;
        JSONObject jsonObject = new JSONObject();
        try {
            object = new JSONObject(str);
            jsonObject.put("params", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getParam(List list) {
        String str = new Gson().toJson(list);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject_ = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray(str);
            jsonObject_.put("list", jsonArray);
            jsonObject.put("params", jsonObject_);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
