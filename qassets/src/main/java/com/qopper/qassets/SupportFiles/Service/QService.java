package com.qopper.qassets.SupportFiles.Service;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.qopper.qassets.SupportFiles.AppController;
import com.qopper.qassets.SupportFiles.Constants;
import com.qopper.qassets.SupportFiles.SupportInterface.OnPostApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QService {

    public static void call(String URL, JSONObject requestObj, int method, final OnPostApiResponse listener) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(method,
                URL,  requestObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("SUCCESS")){
                                listener.OnPostApiResponse(true , response.toString());
                            }else{
                                listener.OnPostApiResponse(false , Constants.getError(response.toString()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.OnPostApiResponse(false , Constants.getError(response.toString()));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.OnPostApiResponse(false , Constants.getError(error.toString()));
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + Constants.ACCESS_TOKEN);
                return headers;
            }
        };

        // Adding request to request queue
        jsonObjReq.setRetryPolicy(new
                DefaultRetryPolicy(Constants.VOLLEY_EXPIRE_TIME,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq, "POST_SERVER_RESPONDING");

    }
}
