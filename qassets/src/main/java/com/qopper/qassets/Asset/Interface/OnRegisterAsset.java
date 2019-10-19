package com.qopper.qassets.Asset.Interface;

import com.qopper.qassets.Asset.Model.QAsset;

public interface OnRegisterAsset {
    public void OnRegisterAsset(Boolean isSuccess ,String error, QAsset qAsset);
}
