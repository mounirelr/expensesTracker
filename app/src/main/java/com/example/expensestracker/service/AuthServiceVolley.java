package com.example.expensestracker.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.expensestracker.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthServiceVolley {

    private static final String TAG = "AuthServiceVolley";

    private static final String REGISTER_URL = "http://192.168.1.8:80/expenseTracker/registre.php";
    private static final String LOGIN_URL = "http://192.168.1.8:80/expenseTracker/login.php";
    public interface RegisterCallback {
        void onSuccess(String status, String message,int idUser);
        void onError(String error);
    }

    public static void registerUser(Context context, User user, RegisterCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        // Create the JSON body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("firstName", user.getFirstName());
            jsonBody.put("lastName", user.getLastName());
            jsonBody.put("email", user.getEmail());
            jsonBody.put("password", user.getPassword());
            jsonBody.put("currency", user.getCurrency());
        } catch (JSONException e) {
            callback.onError("Error creating JSON: " + e.getMessage());
            return;
        }

        // Send the JSON request
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, jsonBody,
                response -> {
                    try {
                        String status = response.getString("status");
                        String message = response.getString("message");
                        int  idUser = Integer.parseInt(response.getString("idUser"));
                        callback.onSuccess(status, message ,  idUser);
                    } catch (JSONException e) {
                        callback.onError("Invalid JSON response: " + e.getMessage());
                        Log.e(TAG, "JSON parsing error", e);
                    }
                },
                error -> {
                    callback.onError("Volley error: " + error.toString());
                    Log.e(TAG, "Volley error", error);
                });


        queue.add(jsonRequest);
    }

    public interface LoginCallback {

        void onError(String error);

        void onSuccess(JSONObject user);
    }

    public static void loginUser(Context context, String email, String password, LoginCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    LOGIN_URL,
                    jsonBody,
                    response -> handleLoginResponse(response, callback),
                    error -> handleVolleyError(error, callback)
            );

            // Set timeout and retry policy
            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000,  // 10 sec timeout
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));

            queue.add(request);
        } catch (JSONException e) {
            callback.onError("Invalid request data");
            Log.e(TAG, "JSON creation error: " + e.getMessage());
        }
    }

    private static void handleLoginResponse(JSONObject response, LoginCallback callback) {
        try {
            String status = response.getString("status");
            if ("success".equals(status)) {
                callback.onSuccess(response.getJSONObject("user"));
            } else {
                callback.onError(response.getString("message"));
            }
        } catch (JSONException e) {
            callback.onError("Invalid server response");
            Log.e(TAG, "JSON parsing error: " + e.getMessage());
        }
    }

    private static void handleVolleyError(VolleyError error, LoginCallback callback) {
        String errorMsg = "Network error";
        if (error.networkResponse != null) {
            errorMsg = "Server error: " + error.networkResponse.statusCode;
            try {
                errorMsg += " - " + new String(error.networkResponse.data);
            } catch (Exception ignored) {}
        }
        callback.onError(errorMsg);
        Log.e(TAG, "Volley error: " + errorMsg);
    }

}
