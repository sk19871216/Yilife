package com.innovation.android.library.http;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

public abstract class InnovationRequestImpl extends InnovationRequest {

    @Override
    public String getContent() {
        InnovationRequestBody obj = getInnovationRequestBody();
        if (obj == null) {
            return null;
        }
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        Log.d("content",""+gson.toJson(obj));
        return gson.toJson(obj);
    }

    protected abstract InnovationRequestBody getInnovationRequestBody();

}
