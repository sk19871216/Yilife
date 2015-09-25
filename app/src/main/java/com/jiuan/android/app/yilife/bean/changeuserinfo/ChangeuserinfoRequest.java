package com.jiuan.android.app.yilife.bean.changeuserinfo;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ChangeuserinfoRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/set_user_info";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String phone;

    private String tooken;

    private UserInfo[] array;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTooken() {
        return tooken;
    }

    public void setTooken(String tooken) {
        this.tooken = tooken;
    }

    public UserInfo[] getArray() {
        return array;
    }

    public void setArray(UserInfo[] array) {
        this.array = array;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setPhone(phone);
        body.setTooken(tooken);
        body.setArray(array);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_12_CHANGE_MYDETAIL_MESSAGE);
        }

        @SerializedName("UN")
        private String phone;

        @SerializedName("AccessToken")
        private String tooken;

        @SerializedName("Changes")
        private UserInfo[] array;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTooken() {
            return tooken;
        }

        public void setTooken(String tooken) {
            this.tooken = tooken;
        }

        public UserInfo[] getArray() {
            return array;
        }

        public void setArray(UserInfo[] array) {
            this.array = array;
        }
    }

}
