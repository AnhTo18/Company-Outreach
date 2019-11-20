package edu.iastate.coms309.project309.util;

public class Const {
    public static final String DOMAIN =  "coms-309-ss-8.misc.iastate.edu:8080/";
    public static final String URL_REGISTER = "http://" + DOMAIN + "owners/add";
    public static final String URL_SHOW_USERS = "http://" + DOMAIN + "owners/";
    public static final String URL_LOGIN = "http://" + DOMAIN +"owners/login";
    public static final String URL_QR = "http://" + DOMAIN +"product/company/";
    public static final String URL_ADD_POINTS = "http://" + DOMAIN +"owners/addpoints";
    public static final String WS_EVENT_UPDATE = "ws://" + DOMAIN + "/notify/";
    public static final String URL_EVENT_LIST = "http://" + DOMAIN + "events/";
    public static final String URL_SHOP = "http://" + DOMAIN +"prizes/";
    public static final String URL_REDEEM =  "http://" + DOMAIN + "prize/redeem/";

    public static String username = "";
    public static String event = "";
    public static String company = "";

}

