package com.qopper.qassets.Alerts.Service;

import com.android.volley.Request;
import com.qopper.qassets.Alerts.Interface.OnAlertSend;
import com.qopper.qassets.Alerts.Model.QAlert;
import com.qopper.qassets.Asset.Model.QAsset;
import com.qopper.qassets.SupportFiles.Constants;
import com.qopper.qassets.SupportFiles.Service.QService;
import com.qopper.qassets.SupportFiles.SupportInterface.OnPostApiResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class QSendAssetAlerts {
    public static void sendAlert(QAsset asset, QAlert alert, final OnAlertSend listener){
        String url = Constants.ALLOY_V1_BASE_URL + "namespaces/" + asset.locationNamespace + "/assets/" + asset.namespace + "/alerts";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title",alert.title);
            jsonObject.put("status",alert.status);
            jsonObject.put("properties",alert.data);
            QService.call(url, jsonObject, Request.Method.POST, new OnPostApiResponse() {
                @Override
                public void OnPostApiResponse(boolean isSuccess, String response) {
                    listener.OnAlertSend(isSuccess,response);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            listener.OnAlertSend(false,"Failed to send Alerts");
        }
    }
}
