package com.qopper.qassets.QopperSRC.Services;

import com.android.volley.Request;
import com.qopper.qassets.Asset.Model.QAsset;
import com.qopper.qassets.QopperSRC.Interface.OnSendHeartBeats;
import com.qopper.qassets.SupportFiles.Constants;
import com.qopper.qassets.SupportFiles.Service.QService;
import com.qopper.qassets.SupportFiles.SupportInterface.OnPostApiResponse;

public class SendHeartBeats {
    public static void send(QAsset asset, final OnSendHeartBeats listener){
        String url = Constants.ALLOY_V1_BASE_URL + "namespaces/" + asset.locationNamespace + "/assets/" + asset.namespace + "/heartbeat";
        QService.call(url, null, Request.Method.GET, new OnPostApiResponse() {
            @Override
            public void OnPostApiResponse(boolean isSuccess, String response) {
                listener.OnSendHeartBeats(isSuccess,response);
            }
        });
    }
}
