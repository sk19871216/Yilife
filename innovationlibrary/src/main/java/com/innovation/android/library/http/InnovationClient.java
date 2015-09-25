package com.innovation.android.library.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class InnovationClient {

    private static InnovationClient sInstance;

    private final AsyncHttpClient client;

    private  DefaultHttpClient httpClient;

    private InnovationClient() {
        client = new AsyncHttpClient();
        client.setTimeout(12000);
        client.setMaxRetriesAndTimeout(0,10000);

        httpClient = new DefaultHttpClient();
    }

    public static InnovationClient getInstance() {
        synchronized (InnovationClient.class) {
            if (sInstance == null) {
                sInstance = new InnovationClient();
            }
            return sInstance;
        }
    }

    public AsyncHttpClient getClient() {
        return client;
    }
    public DefaultHttpClient getHttpClient() {
        return httpClient;
    }

    public void setSSLSocketFactory() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sf);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RequestHandle post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        return client.post(context, url, params, responseHandler);
    }

    public void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        client.cancelRequests(context, mayInterruptIfRunning);
    }

    public void cancelAllRequests(boolean mayInterruptIfRunning){
        client.cancelAllRequests(mayInterruptIfRunning);
    }

    public void shutdown(){
        getHttpClient().getConnectionManager().shutdown();
    }
}
