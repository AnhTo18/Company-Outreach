package edu.iastate.coms309.project309.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Controller for requests with a server
 * @author Sam Henley
 */
public class RequestController {

    private RequestQueue rq;
    private Context c;

    /**
     * Initialize Request Controller
     * @param context Context of the application
     */
    public RequestController(Context context)
    {
        c = context;
        rq = Volley.newRequestQueue(c);
    }


    /**
     * Make a JsonObjectRequest
     * @param method type of request (Request.Method.GET, Request.Method.POST)
     * @param url address to make request with
     * @return response from server
     */
    public JSONObject requestJsonObject(int method, String url)
    {
        return requestJsonObject(method, url, null);
    }

    /**
     * Make a JsonObjectRequest
     * @param method type of request (Request.Method.GET, Request.Method.POST)
     * @param url address to make request with
     * @param jsonRequest JSONObject sent to server with request
     * @return response from server
     */
    public JSONObject requestJsonObject(int method, String url, JSONObject jsonRequest)
    {
        Log.d("Volley", "Making Json " + ((method == 0) ? "GET" : "POST") + " Request to " + url);
        Log.d("Volley", "JSONObject sent to server: " + jsonRequest.toString());

        final JSONObject[] ret = new JSONObject[1];
        JsonObjectRequest j = new JsonObjectRequest(method, url, jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ret[0] = response;
                Log.d("Volley", "Server response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Volley", "Volley Error: " + e.toString());
                e.printStackTrace();
            }
        });

        rq.add(j);

        return ret[0];
    }

    /**
     * Make a JsonArrayRequest
     * @param method type of request (Request.Method.GET, Request.Method.POST)
     * @param url address to make request with
     * @return response from server
     */
    public JSONArray requestJsonArray(int method, String url)
    {
        return requestJsonArray(method, url, null);
    }

    /**
     * Make a JsonArrayRequest
     * @param method type of request (Request.Method.GET, Request.Method.POST
     * @param url address to make request with
     * @param jsonRequest JSONArray sent to server with request
     * @return response from server
     */
    public JSONArray requestJsonArray(int method, String url, JSONArray jsonRequest)
    {
        Log.d("Volley", "Making Json " + ((method == 0) ? "GET" : "POST") + " Request to " + url);
        Log.d("Volley", "JSONObject sent to server: " + jsonRequest.toString());

        final JSONArray[] ret = new JSONArray[1];
        JsonArrayRequest j = new JsonArrayRequest(method, url, jsonRequest, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ret[0] = response;
                Log.d("Volley", "Server response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Volley", "Volley Error: " + e.toString());
                e.printStackTrace();
            }
        });

        rq.add(j);

        return ret[0];
    }

}
