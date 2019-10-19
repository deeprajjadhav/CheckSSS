package com.qopper.qassets.Properties.Service;
import com.android.volley.Request;
import com.qopper.qassets.Asset.Model.QAsset;
import com.qopper.qassets.Properties.Interface.OnPropertiesSend;
import com.qopper.qassets.SupportFiles.Constants;
import com.qopper.qassets.SupportFiles.Service.QService;
import com.qopper.qassets.SupportFiles.SupportInterface.OnPostApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class QSendAssetProperties {
    public static void send(QAsset asset, JSONObject properties, final OnPropertiesSend listener){
        String url = Constants.ALLOY_V1_BASE_URL + "namespaces/" + asset.locationNamespace + "/assets/" + asset.namespace + "/properties";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("properties",properties);
            QService.call(url, jsonObject, Request.Method.PATCH, new OnPostApiResponse() {
                @Override
                public void OnPostApiResponse(boolean isSuccess, String response) {
                    listener.OnPropertiesSend(isSuccess,response);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            listener.OnPropertiesSend(false,"Failed to send Properties");
        }
    }
}
