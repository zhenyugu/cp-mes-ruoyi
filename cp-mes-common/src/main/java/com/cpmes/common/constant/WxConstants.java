package com.cpmes.common.constant;

/**
 * @Author cp-mes
 * @Date 2023/11/22 11:14
 **/
public interface WxConstants {

    String LOGIN_REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";
    String GRANT_TYPE_OPENID = "authorization_code";
    String ACCESS_TOKEN_REQUEST_URL = "https://api.weixin.qq.com/cgi-bin/token";
    String MESSAGE_SUBSCRIBE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
    String APP_ID = "wxb1ca8df0b412e0c6";
    String APP_SECRET = "3c587c9caa443a1e764a880ed2f13d4c";
    String INSPECTION_PAGE = "pages/userCenter/inspection/index/index";
    String INSPECTION_TEMPLATE_ID = "eTp6whcSQ_x7u5sTA-kq2uxUzQuQ4zyWNOb7lCSCxnQ"; // 巡检通知模板id

    String TOKEN = "szcloudpulse";

    String AES_KEY = "dCpYdkmUkAb0vlYOhbT9MZNEeNeL5lEwwjPWwREluhy";

    String MSG_DATA_FORMAT = "JSON";
}
