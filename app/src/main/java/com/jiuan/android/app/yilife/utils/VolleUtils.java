package com.jiuan.android.app.yilife.utils;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

public class VolleUtils {
	/**
	 * 该方法是生成Volly请求数据的加载类
	 * @param context 上下文对象
	 * @param url   请求数据的url地址
	 * @param callBack 响应成功后返回的JSONOObject对象
	 */
	public static void initVolley(Context context,String url,final ResultCallBack callBack){
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		JsonObjectRequest objectRequest = new JsonObjectRequest(url, null, 
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						callBack.result(response);
					}
				}, 
				new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        Log.d("volley ERR结果",error.toString());
                    }
                }){
                    protected Response<JSONObject> parseNetworkResponse(
                            NetworkResponse response) {

                        try {
                            JSONObject jsonObject = new  JSONObject(
                                    new String(response.data, "UTF-8"));
                            return        Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
                        } catch (UnsupportedEncodingException e) {
                            return Response.error(new ParseError(e));
                        } catch (Exception je) {
                            return Response.error(new ParseError(je));
                        }
                    }

				};
		requestQueue.add(objectRequest);
	}
	
	public interface ResultCallBack{
		void result(JSONObject response);
	}
}
