package com.android.common.okgo;

import com.android.common.util.LogUtils;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Werwolf on 2017/4/26.
 */

public class RequestParams<T> extends HashMap {

    private Gson gson;

    public RequestParams() {
        gson = new Gson();
        put("transHead", transHead());
    }

    private String transHead() {
        String aesKey = RandomUtil.genRandomCheckString(16, 6);
        HeadBean head = new HeadBean();
        head.setClientFlag("25600001");
        String jniCode = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJ7ln6M5S4vup1BDXizcuwVYwLcIBeKigluZb+tthU3N7t8hISR2/EpmGvgp42Ooijo8abVhvbcihmYal9k35eUH01AecVE9e9Vn6gL9XBTj3LsPTIg01BfDoxSKBjEtwwtbyeG6BNCQbF89NF2Nfj/Zn7i2BduQcrlaYZ7wr4SwIDAQAB";
        String encrypt = RSAUtil.pubKeyEnc(aesKey, jniCode);
        if (encrypt.equals("10000")) {
            return gson.toJson(head);
        } else {
            String newEnc = encrypt.replace("\n", "");
            String data = newEnc.replace("+", "%2B");
            head.setSecretKey(data);
            head.setService("test_service_and");
            head.setVersion("0.1.0");
            head.setReqNo(System.currentTimeMillis() + "");

        }
        return gson.toJson(head);
    }


    //这里可以传Map,List,实体类之类的，有特殊的还可以扩展
    public void buildJson(T map) {
        if (map != null) {
            String body = gson.toJson(map);
            LogUtils.e("OkGo", "请求参数：" + body);
            put("transBody", body);
        }
    }
    //拼接传递参数为时的json字符串
    public String buildToJson(T map) {
        String json = null;
        if (map != null) {
            String body = gson.toJson(map);
            json = "{ \"params\": " + body + " }";
            LogUtils.e("OkGo", "请求参数：" + body);
//            put("transBody", json);
        }
        return json;
    }

}
