package com.qopper.qassets.Asset.Register;

import com.android.volley.Request;
import com.qopper.qassets.Asset.Model.QAsset;
import com.qopper.qassets.Asset.Interface.OnRegisterAsset;
import com.qopper.qassets.SupportFiles.Constants;
import com.qopper.qassets.SupportFiles.Service.QService;
import com.qopper.qassets.SupportFiles.SupportInterface.OnPostApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class Register {

    public static void registerAsset(String accessToken, final String tenantID, final String assetName, final String product, final String vendor, final String source, final OnRegisterAsset listener){

        try {

            Constants.ACCESS_TOKEN = accessToken;

            String url = Constants.FORGE_BASE_URL + "namespaces/" + tenantID + "/assets";
            JSONObject requestObject = new JSONObject();

            requestObject.put("tenantId",tenantID);
            requestObject.put("assetId",assetName);
            requestObject.put("product",product);
            requestObject.put("vendor",vendor);
            requestObject.put("source",source);

            QService.call(url, requestObject, Request.Method.POST, new OnPostApiResponse() {
                @Override
                public void OnPostApiResponse(boolean isSuccess, String response) {
                    if (isSuccess) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            JSONObject dataObj = responseJson.getJSONObject("data");
                            String namespace = dataObj.getString("namespace");
                            String locationNamespace = dataObj.getString("locationNamespace");
                            QAsset qAsset = new QAsset(tenantID,assetName,product,vendor,source,namespace,locationNamespace);
                            listener.OnRegisterAsset(isSuccess,null,qAsset);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.OnRegisterAsset(false,"Failed to create Asset",null);
                        }
                    }else{
                        listener.OnRegisterAsset(false,response,null);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            listener.OnRegisterAsset(false,"Failed to create Asset",null);
        }

    }
}
