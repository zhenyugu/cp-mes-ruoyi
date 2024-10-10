package com.cpmes.system.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cpmes.common.constant.WxConstants;
import com.cpmes.common.utils.DateUtils;
import com.cpmes.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author cp-mes
 * @Date 2023/11/21 16:49
 **/
public class WeChatUtil {

    /**
     * 获取access_token
     *
     * @return access_token
     */
    public static JSONObject getAccessToken() {
        Map<String, String> requestUrlParam = new HashMap<>();
        //小程序appId
        requestUrlParam.put("appid", WxConstants.APP_ID);
        //小程序secret
        requestUrlParam.put("secret", WxConstants.APP_SECRET);
        //默认参数(获取access_token使用)
        requestUrlParam.put("grant_type", "client_credential");
        //发送get请求读取调用微信接口获取access_token
        return JSON.parseObject(HttpClientUtil.doGet(WxConstants.ACCESS_TOKEN_REQUEST_URL, requestUrlParam));
    }

    /**
     * 获取sessionKey和openid
     *
     * @param loginCode 小程序登录code
     * @return
     */
    public static JSONObject getSessionKeyOrOpenId(String loginCode) {
        String requestUrl = WxConstants.LOGIN_REQUEST_URL;
        Map<String, String> requestUrlParam = new HashMap<>();
        //小程序appId
        requestUrlParam.put("appid", WxConstants.APP_ID);
        //小程序secret
        requestUrlParam.put("secret", WxConstants.APP_SECRET);
        //小程序端返回的code
        requestUrlParam.put("js_code", loginCode);
        //默认参数
        requestUrlParam.put("grant_type", WxConstants.GRANT_TYPE_OPENID);
        //发送post请求读取调用微信接口获取openid用户唯一标识
        return JSON.parseObject(HttpClientUtil.doPost(requestUrl, requestUrlParam));
    }


}
