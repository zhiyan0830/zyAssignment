
package com.example.myapplication.Remote;

import com.example.myapplication.Model.Result;
import com.example.myapplication.Model.RetrofitClient;
import com.example.myapplication.Remote.iGoogleAPIService;

public class Commons {

    public static Result currentResult;

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";

   public static iGoogleAPIService getGoogleAPIService()
    {
        return RetrofitClient.getClient(GOOGLE_API_URL).create(iGoogleAPIService.class);
    }
}


