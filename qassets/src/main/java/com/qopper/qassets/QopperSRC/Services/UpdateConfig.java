package com.qopper.qassets.QopperSRC.Services;

import com.android.volley.Request;
import com.qopper.qassets.Asset.Model.QAsset;
import com.qopper.qassets.QopperSRC.Interface.OnConfigUpdate;
import com.qopper.qassets.Metrics.Model.QMetrics;
import com.qopper.qassets.SupportFiles.Constants;
import com.qopper.qassets.SupportFiles.Service.QService;
import com.qopper.qassets.SupportFiles.SupportInterface.OnPostApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateConfig {

    public static void update(QAsset asset, QMetrics[] metrics, String[] properties, String[] alerts, final OnConfigUpdate listener){

        JSONObject requestObj = new JSONObject();

        for (QMetrics metric: metrics) {
            JSONObject metricObj = new JSONObject();
            try {
                metricObj.put("label",metric.label);
                metricObj.put("unit",metric.unit);
                metricObj.put("type","METRICS");
                requestObj.put(metric.key,metricObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (String property: properties) {
            JSONObject propertyObj = new JSONObject();
            try {
                propertyObj.put("label",property);
                propertyObj.put("type","PROPERTIES");
                requestObj.put(property,propertyObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (String alert: alerts) {
            JSONObject alertObj = new JSONObject();
            try {
                alertObj.put("label",alert);
                alertObj.put("type","ALERTS");
                requestObj.put(alert,alertObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject finalObj = new JSONObject();
        try {
            finalObj.put("configMetadata",requestObj);
            String url = Constants.ALLOY_V1_BASE_URL + "namespaces/" + asset.locationNamespace + "/assets/" + asset.namespace + "/configMetadata";
            QService.call(url, finalObj, Request.Method.PATCH, new OnPostApiResponse() {
                @Override
                public void OnPostApiResponse(boolean isSuccess, String response) {
                    listener.OnConfigUpdate(isSuccess,response);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            listener.OnConfigUpdate(false,"Failed to update config");
        }
    }
}
