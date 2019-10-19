package com.qopper.qassets.Asset.Model;

import com.android.volley.Request;
import com.qopper.qassets.Alerts.Interface.OnAlertSend;
import com.qopper.qassets.Alerts.Model.QAlert;
import com.qopper.qassets.Alerts.Service.QSendAssetAlerts;
import com.qopper.qassets.Asset.Interface.OnFetchAssetStatus;
import com.qopper.qassets.Metrics.Interface.OnMetricsSend;
import com.qopper.qassets.Metrics.Service.QSendAssetMetrics;
import com.qopper.qassets.QopperSRC.Interface.OnConfigUpdate;
import com.qopper.qassets.Metrics.Model.QMetrics;
import com.qopper.qassets.Properties.Interface.OnPropertiesSend;
import com.qopper.qassets.Properties.Service.QSendAssetProperties;
import com.qopper.qassets.QopperSRC.Interface.OnSendHeartBeats;
import com.qopper.qassets.QopperSRC.Services.SendHeartBeats;
import com.qopper.qassets.QopperSRC.Services.UpdateConfig;
import com.qopper.qassets.QopperSRC.Validate.Validate;
import com.qopper.qassets.SupportFiles.Constants;
import com.qopper.qassets.SupportFiles.Service.QService;
import com.qopper.qassets.SupportFiles.SupportInterface.OnPostApiResponse;

import org.json.JSONObject;

public class QAsset {

    public String tenantID;
    public String assetName;
    public String product;
    public String vendor;
    public String source;

    public String namespace;
    public String locationNamespace;

    private QMetrics metrics[] = {};
    private String[] properties = {};
    private String[] alerts = {};

    public QAsset(String tenantID, String assetName, String product, String vendor, String source, String namespace, String locationNamespace) {
        this.tenantID = tenantID;
        this.assetName = assetName;
        this.product = product;
        this.vendor = vendor;
        this.source = source;
        this.namespace = namespace;
        this.locationNamespace = locationNamespace;
    }

    public void updateConfig(QMetrics metrics[], String[] properties, String[] alerts, final OnConfigUpdate listener){
        this.metrics = metrics;
        this.properties = properties;
        this.alerts = alerts;
        UpdateConfig.update(this, metrics, properties, alerts, new OnConfigUpdate() {
            @Override
            public void OnConfigUpdate(boolean isSuccess, String response) {
                listener.OnConfigUpdate(isSuccess,response);
            }
        });
    }

    public void getStatus(final OnFetchAssetStatus listener){
        String url = Constants.ALLOY_V1_BASE_URL + "namespaces/" + this.locationNamespace + "/assets/" + this.namespace + "/lifecycle?topLevelNamespace=" + this.tenantID;
        QService.call(url, null, Request.Method.GET, new OnPostApiResponse() {
            @Override
            public void OnPostApiResponse(boolean isSuccess, String response) {
                listener.OnFetchAssetStatus(isSuccess,response);
            }
        });
    }

    public void sendProperties(JSONObject properties, final OnPropertiesSend listener){
        if(Validate.validateProperties(this.properties, properties))
            QSendAssetProperties.send(this, properties, new OnPropertiesSend() {
                @Override
                public void OnPropertiesSend(boolean isSuccess, String response) {
                    listener.OnPropertiesSend(isSuccess,response);
                }
            });
        else
            listener.OnPropertiesSend(false,"Some or all of the Properties are not configured!");

    }

    public void sendAlert(QAlert qAlert , final OnAlertSend listener){
        if(Validate.validateAlerts(this.alerts,qAlert))
            QSendAssetAlerts.sendAlert(this, qAlert, new OnAlertSend() {
                @Override
                public void OnAlertSend(boolean isSuccessful, String response) {
                    listener.OnAlertSend(isSuccessful,response);
                }
            });
        else
            listener.OnAlertSend(false,"Some or all of the Alerts are not configured!");
    }

    public void sendMetrics(JSONObject metrics, final OnMetricsSend listener){
        if(Validate.validateMetrics(this.metrics,metrics))
            QSendAssetMetrics.send(this, metrics, new OnMetricsSend() {
                @Override
                public void OnMetricsSend(boolean isSuccess, String response) {
                    listener.OnMetricsSend(isSuccess,response);
                }
            });
        else
            listener.OnMetricsSend(false,"Some or all of the Metrics are not configured!");
    }

    public void sendHeartBeats(final OnSendHeartBeats listener){
        SendHeartBeats.send(this, new OnSendHeartBeats() {
            @Override
            public void OnSendHeartBeats(boolean isSuccess, String response) {
                listener.OnSendHeartBeats(isSuccess,response);
            }
        });
    }

}
