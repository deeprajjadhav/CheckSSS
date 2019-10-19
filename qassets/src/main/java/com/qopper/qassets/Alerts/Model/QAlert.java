package com.qopper.qassets.Alerts.Model;

import org.json.JSONArray;
import org.json.JSONObject;

public class QAlert {
    public String status;
    public String title;
    public JSONObject data;
    public QAlert(String status, String title, JSONObject data) {
        this.status = status;
        this.title = title;
        this.data = data;
    }
}
