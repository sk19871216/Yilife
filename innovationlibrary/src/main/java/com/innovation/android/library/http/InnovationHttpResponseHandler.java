package com.innovation.android.library.http;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class InnovationHttpResponseHandler extends JsonHttpResponseHandler {

    // 成功
    private static final int SUCCESS = 1;

    // 失败
    private static final int FAILURE = 2;

    // 错误
    private static final int ERROR = 3;

    // 成功
    private static final String SUCCESS_1000 = "1000";

    // 服务器内部错误
    private static final String ERROR_5000 = "5000";

    @Override
    public void onStart() {
        onInnovationStart();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

        Log.d("结果",""+response);

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        InnovationResponse oaResponse = gson.fromJson(response.toString(), InnovationResponse.class);

        switch (oaResponse.getResult()) {
            case SUCCESS:
                if (oaResponse.getResultMessage().equals(SUCCESS_1000)) {
                    onInnovationSuccess(oaResponse.getReturnValue());
                }
                break;
            case FAILURE:
                onInnovationFailure(oaResponse.getResultMessage());
                onInnovationFailure(oaResponse.getResultMessage(), oaResponse.getReturnValue());
                break;
            case ERROR:
                if (oaResponse.getResultMessage().equals(ERROR_5000)) {
                    onInnovationError("服务器内部错误");
                }
                break;
        }
        onInnovationFinish();
    }

    /**
     * 开始请求网络连接会调用此方法
     */
    public void onInnovationStart() {

    }

    public void onInnovationSuccess(JsonElement value) {

    }

    public void onInnovationFailure(String msg) {
        if (null == msg) {
            return;
        }
        Log.d("onOAFailure", msg);
    }

    /**
     * 当返回错误时,需要对相关错误的内容进行处理时,会调用此方法
     */
    public void onInnovationFailure(String msg, JsonElement value) {
        Log.d("onInnovationFailure", msg);
    }

    public void onInnovationError(String value) {
        if (null == value) {
            return;
        }
        Log.d("onOAError", value);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
        onInnovationExceptionFinish();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        onInnovationExceptionFinish();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        onInnovationExceptionFinish();
    }

    /**
     * 正常结束会调用此方法
     */
    public void onInnovationFinish() {

    }

    /**
     * 异常结束会调用此方法
     * 注意:如果重写onFailure相关方法时,请在方法结束处调用此方法.
     */
    public void onInnovationExceptionFinish() {
        Log.d("onInnovationExceptionFinish", "onInnovationExceptionFinish");
    }

    /**
     * 把Json格式的字符串转换成实体类型的方法.
     * 注意:实体类中的变量需要用@SerializedName注释
     */
    public final <T> T get(String value, Class<T> classOfT) {
        try {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            return gson.fromJson(value, classOfT);
        } catch (JsonSyntaxException exception) {
            Log.d("exception", exception.getMessage());
        }
        return null;
    }
}
