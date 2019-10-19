package com.qopper.qassets.QopperSRC;

import com.qopper.qassets.Asset.Model.QAsset;
import com.qopper.qassets.Asset.Register.Register;
import com.qopper.qassets.Asset.Interface.OnRegisterAsset;

public class Qopper {

    public static void registerAsset(String accessToken, String tenantID, String assetName, String source, final OnRegisterAsset listener) {
        register(accessToken, tenantID, assetName,"" , "", source, new OnRegisterAsset() {
            @Override
            public void OnRegisterAsset(Boolean isSuccess, String error, QAsset qAsset) {
                listener.OnRegisterAsset(isSuccess,error,qAsset);
            }
        });
    }

    public static void registerAsset(String accessToken, String tenantID, String assetName, String product, String vendor, String source, final OnRegisterAsset listener) {
        register(accessToken, tenantID, assetName, product , vendor, source, new OnRegisterAsset() {
            @Override
            public void OnRegisterAsset(Boolean isSuccess, String error, QAsset qAsset) {
                listener.OnRegisterAsset(isSuccess,error,qAsset);
            }
        });
    }

    private static void register(String accessToken, final String tenantID, final String assetName, final String product, final String vendor, final String source, final OnRegisterAsset listener){
        Register.registerAsset(accessToken, tenantID, assetName, product, vendor, source, new OnRegisterAsset() {
            @Override
            public void OnRegisterAsset(Boolean isSuccess, String error, QAsset qAsset) {
                listener.OnRegisterAsset(isSuccess,error,qAsset);
            }
        });
    }
}
