package com.qopper.qassets.SupportFiles;

import org.json.JSONObject;

/**
 * Created by Ashish on 5/7/2018.
 */

public class Constants {

    public static String FORGE_BASE_URL = "https://forge.staging.qopper.com/api/v2.0.1/";
    public static String ALLOY_V1_BASE_URL = "https://staging.qopper.com/api/v1/";
    public static String ACCESS_TOKEN = "";

    public static final int VOLLEY_EXPIRE_TIME = 30000;
//    public static String KEY_FLAG = "success";
//    public static String KEY_STATUS = "status";

    public static String getError(String error){

        return "Error";
    }
}
