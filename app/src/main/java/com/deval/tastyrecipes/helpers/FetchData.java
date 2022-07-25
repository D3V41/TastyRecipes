package com.deval.tastyrecipes.helpers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deval.tastyrecipes.interfaces.VolleyCallback;

public class FetchData {
    public static StringRequest getRequest(String randomRecipeURL,final VolleyCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, randomRecipeURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                });
        return stringRequest;
    }
}
