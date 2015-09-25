package com.jiuan.android.app.yilife.fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.activity.HTML5Activity;

import com.jiuan.android.app.yilife.bean.login.LoginClient;
import com.jiuan.android.app.yilife.bean.login.LoginResponse;
import com.jiuan.android.app.yilife.bean.login.RefreshTookenHandler;
import com.jiuan.android.app.yilife.bean.splashimage.SplashimageClient;
import com.jiuan.android.app.yilife.bean.splashimage.SplashimageHandler;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class LifeFragment extends Fragment {
    private TextView tv_title;
    private ToastOnly toastOnly;
    private NetworkImageView imageView,imageView_dayli;
    private LruCache<String, Bitmap> mLruCache;
    private ImageView iv,iv_setting;
    private String path,pathlife,base64life;
    private RequestQueue queue;
    private ViewPager vp;
    private PagerAdapter adapter;
    private ArrayList<NetworkImageView> list;
    private SharedPreferences mySharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ielife, container, false);
        toastOnly = new ToastOnly(getActivity());
        queue = Volley.newRequestQueue(getActivity());
        list = new ArrayList<NetworkImageView>();
        mySharedPreferences = getActivity().getSharedPreferences("self", Activity.MODE_PRIVATE);
        String refresh = mySharedPreferences.getString("RefreshToken","");
        String tooken = mySharedPreferences.getString("AccessToken","");
        String hguid = mySharedPreferences.getString("HGUID","");
        Log.e("结果refresh",refresh);
        LoginClient.refreshtooken(getActivity(), hguid, refresh, new RefreshTookenHandler() {
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
            }

            @Override
            public void onLoginSuccess(LoginResponse response) {
                super.onLoginSuccess(response);
                Log.e("结果refresh", "refresh");
                SharedPreferences.Editor editot = mySharedPreferences.edit();
                editot.putString("AccessToken", response.getToken().getAccessToken());
                editot.putLong("AccessExpire", response.getToken().getAccessExpire());
                editot.putString("RefreshToken", response.getToken().getRefreshToken());
                editot.putString("HGUID", response.getHguid());
                editot.commit();
            }
        }, TestOrNot.isTest);
        vp = (ViewPager) view.findViewById(R.id.ielife_vp);
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view ==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(list.get(position), 0);
                return list.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(list.get(position));
            }
        };
        imageView  = new NetworkImageView(getActivity());
        imageView_dayli  = new NetworkImageView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView_dayli.setLayoutParams(params);
        imageView.setLayoutParams(params);
//        imageView = (NetworkImageView) view.findViewById(R.id.image_ielife);
        iv = (ImageView) view.findViewById(R.id.image_ielife1);
        imageView.setBackgroundResource(R.drawable.home_up);
        imageView_dayli.setBackgroundResource(R.drawable.e_splash1);
//        list.add(imageView_dayli);
        list.add(imageView);
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==1){

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HTML5Activity.class);
//                intent.putExtra("path","http://www.iemylife.com/mobile/cam-gprsbp/camindex");
                intent.putExtra("path",path);
                startActivity(intent);
            }
        });
//        imageView.setDefaultImageResId(R.drawable.home);
//        imageView.setErrorImageResId(R.drawable.home);
        int maxMemorysize = (int) (Runtime.getRuntime().maxMemory()/1024);
        mLruCache = new LruCache<String, Bitmap>(maxMemorysize/8) {
            protected int sizeOf(String string, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
        if (NetWorkInfo.isNetworkAvailable(getActivity())){
            SplashimageClient.requestLogin(getActivity(),new SplashimageHandler(){
                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    FailMessage.showfail(getActivity(), msg);
                }

                @Override
                public void onLoginSuccess(String response) {
                    super.onLoginSuccess(response);
//                    FakeX509TrustManager.allowAllSSL();
                    path = response.substring((response.lastIndexOf("=")+1));
//                    WindowManager wm = getActivity().getWindowManager();
//                    WindowManager wm = (WindowManager) getActivity().getSupportFragmentManager();
//                    int width = wm.getDefaultDisplay().getWidth();
//                    int height = wm.getDefaultDisplay().getHeight();
                    pathlife = response;

                    ImageRequest imageRequest = new ImageRequest(response,new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            BitmapDrawable bd=new BitmapDrawable(response);
                            imageView.setBackground(bd);
                            adapter.notifyDataSetChanged();
                            SharedPreferences.Editor editor_self = mySharedPreferences.edit();
                            editor_self.putString("lifepage", pathlife);
                            editor_self.putString("base64life", bitmapToBase64(response)).commit();
                        }
                    },
                            imageView.getWidth(),
                            imageView.getHeight(),
                            Bitmap.Config.RGB_565,
                            new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError arg0) {

                        }
                    });
                   queue.add(imageRequest);
                }
            }, TestOrNot.isTest);
        }else{

            toastOnly.toastShowShort("请检查您的网络环境");
        }
//        iv_setting = (ImageView) getActivity().findViewById(R.id.blue_icon_setting);

        return view;
    }
    private Bitmap downloadBitmap(String imageUrl) {
        Bitmap bitmap = null;
        HttpURLConnection con = null;

        try {
            if (con.getResponseCode() == 200) {

                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                con.setDoInput(true);
                con.setDoOutput(true);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return bitmap;
    }

    @Override
    public void onResume() {
        super.onResume();
        String xxx = mySharedPreferences.getString("base64life","");
        if (!xxx.equals("")) {
            Bitmap bp =  base64ToBitmap(xxx);
            imageView.setImageBitmap(bp);
        }else{
            imageView.setImageResource(R.drawable.home_up);
        }
    }
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
