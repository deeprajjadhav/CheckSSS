package com.qopper.qassets.Metrics.Service;

import com.android.volley.Request;
import com.qopper.qassets.Asset.Model.QAsset;
import com.qopper.qassets.Metrics.Interface.OnMetricsSend;
import com.qopper.qassets.SupportFiles.Constants;
import com.qopper.qassets.SupportFiles.Service.QService;
import com.qopper.qassets.SupportFiles.SupportInterface.OnPostApiResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QSendAssetMetrics {
    public static void send(QAsset asset, JSONObject metrics, final OnMetricsSend listener){
        try {
            String url = Constants.ALLOY_V1_BASE_URL + "namespaces/" + asset.locationNamespace + "/assets/" + asset.namespace + "/metrics";
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(metrics);
            jsonObject.put("metrics",jsonArray);
            QService.call(url, jsonObject, Request.Method.POST, new OnPostApiResponse() {
                @Override
                public void OnPostApiResponse(boolean isSuccess, String response) {
                    listener.OnMetricsSend(isSuccess,response);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            listener.OnMetricsSend(false,"Failed to send Metrics");
        }
    }
}
