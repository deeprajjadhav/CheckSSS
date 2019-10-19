package com.qopper.qassets;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qopper.qassets.Alerts.Interface.OnAlertSend;
import com.qopper.qassets.Alerts.Model.QAlert;
import com.qopper.qassets.Asset.Interface.OnFetchAssetStatus;
import com.qopper.qassets.Asset.Model.QAsset;
import com.qopper.qassets.Metrics.Interface.OnMetricsSend;
import com.qopper.qassets.QopperSRC.Interface.OnConfigUpdate;
import com.qopper.qassets.Metrics.Model.QMetrics;
import com.qopper.qassets.Properties.Interface.OnPropertiesSend;
import com.qopper.qassets.Asset.Interface.OnRegisterAsset;
import com.qopper.qassets.QopperSRC.Interface.OnSendHeartBeats;
import com.qopper.qassets.QopperSRC.Qopper;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    QAsset SDKAsset;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Qopper.registerAsset("ls9bhpip8pp2empu", "guest4652.network.corp", "Syndicate001", "Assassin's Creed", "Ubisoft", "SDK", new OnRegisterAsset() {
                    @Override
                    public void OnRegisterAsset(Boolean isSuccess, String error, QAsset qAsset) {
                        if (isSuccess && qAsset != null) {
                            SDKAsset = qAsset;
                            updateConfig();
                        }else{
                            System.out.println(error);
                        }
                    }
                });
            }
        });
    }

    private void updateConfig(){

        QMetrics qMetrics = new QMetrics("timeStamp","timeStamp_Unit","timeStamp_Label");
        QMetrics qMetrics1 = new QMetrics("system_mem_total","system_mem_total_Unit","system_mem_total_Label");
        QMetrics qMetrics2 = new QMetrics("system_disk_free","system_disk_free_Unit","system_disk_free_Label");
        QMetrics qMetrics3 = new QMetrics("system_load_5","system_load_5_Unit","system_load_5_Label");
        QMetrics qMetrics4 = new QMetrics("system_cpu_user","system_cpu_user_Unit","system_cpu_user_Label");
        QMetrics[] metricsArray = {qMetrics,qMetrics1,qMetrics2,qMetrics3,qMetrics4};

        String[] properties = {"device_model","device_name","system_name","device_orientation","device_udid"};
        String[] alerts = {"id","response_time"};

        this.SDKAsset.updateConfig(metricsArray, properties, alerts, new OnConfigUpdate() {
            @Override
            public void OnConfigUpdate(boolean isSuccess, String response) {
                System.out.println(response);
                sendHeartBeats();
            }
        });
    }

    public void getStatus() {
        this.SDKAsset.getStatus(new OnFetchAssetStatus() {
            @Override
            public void OnFetchAssetStatus(boolean isSuccess, String response) {
                System.out.println(response);
            }
        });
    }

    public void sendAlert() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id","8129750D-97C8-4D44-A4A8-C837F87E9AE3");
            jsonObject.put("response_time","3235.8219623565674");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        QAlert qAlert = new QAlert("ONLINE","Transactions response time in ms",jsonObject);

        this.SDKAsset.sendAlert(qAlert, new OnAlertSend() {
            @Override
            public void OnAlertSend(boolean isSuccessful, String response) {
                System.out.println(isSuccessful);
                System.out.println(response);
            }
        });
    }

    public void sendProperties(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device_model","Test_iPhone");
            jsonObject.put("device_name","Mac mini");
            jsonObject.put("system_name","Test_iOS");
            jsonObject.put("device_orientation","Test_UIDeviceOrientationPortrait");
            jsonObject.put("device_udid","Test_2F568FCD-37AB-4A4F-8B0F-53FD15B5C3B1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.SDKAsset.sendProperties(jsonObject, new OnPropertiesSend() {
            @Override
            public void OnPropertiesSend(boolean isSuccess, String response) {
                System.out.println(isSuccess);
                System.out.println(response);
            }
        });
    }

    public void sendMetrics(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("timeStamp","2018-08-01 10:24:36");
            jsonObject.put("system_mem_total","8589934592 bytes");
            jsonObject.put("system_disk_free","29.07 GB");
            jsonObject.put("system_load_5","2.085449");
            jsonObject.put("system_cpu_user","9.89%");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.SDKAsset.sendMetrics(jsonObject, new OnMetricsSend() {
            @Override
            public void OnMetricsSend(boolean isSuccess, String response) {
                System.out.println(response);
            }
        });
    }

    public void sendHeartBeats(){
        this.SDKAsset.sendHeartBeats(new OnSendHeartBeats() {
            @Override
            public void OnSendHeartBeats(boolean isSuccess, String response) {
                System.out.println(response);
            }
        });
    }
}
