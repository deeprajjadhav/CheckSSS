package com.qopper.qassets.QopperSRC.Validate;
import com.qopper.qassets.Alerts.Model.QAlert;
import com.qopper.qassets.Metrics.Model.QMetrics;
import org.json.JSONObject;
public class Validate {

    public static boolean validateAlerts(String[] alertsConfig, QAlert alerts){
        if (alertsConfig == null) {
            return false;
        }
        for (String key : alertsConfig) {
            if (!alerts.data.has(key)){
                return false;
            }
        }
        return true;
    }

    public static boolean validateProperties(String[] propertiesConfig, JSONObject Properties){
        if (propertiesConfig == null) {
            return false;
        }
        for (String key : propertiesConfig) {
            if (!Properties.has(key)){
                return false;
            }
        }
        return true;
    }

    public static boolean validateMetrics(QMetrics[] metricsConfig, JSONObject metrics){
        if (metricsConfig == null) {
            return false;
        }
        String[] keys = new String[metricsConfig.length];
        for (int i=0 ; i <metricsConfig.length ; i++){
            keys[i] = metricsConfig[i].key;
        }
        for (String key : keys) {
            if (!metrics.has(key)){
                return false;
            }
        }
        return true;
    }
}
