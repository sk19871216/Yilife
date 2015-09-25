package com.alipay.sdk.pay.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

public class PayDemoActivity extends FragmentActivity {

	public static final String PARTNER = "2088611402679660";
	public static final String SELLER = "webmaster@iemylife.com";

	public static final String RSA_PRIVATE ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANtJYkiaI78B/g2D\n" +
            "50rKaHvV4ExEHLfxiQkOAfGHYhT5SwosnzS6A/9+Y1ZtPibBYnVrahU/IFuqfa49\n" +
            "rD/owJzld1z5uWhDepRx4Yvd/Hz09eAOTkmWo209aCg6fahUvSYb57aLCyTtgjo9\n" +
            "8Tm5uH84GH7H0PODOymDJF14NDfvAgMBAAECgYEAub1b0XE2K/jtDteWFsSBYbsU\n" +
            "JKVgyfF9iDzkKofo7SkHhZOsOoYBEu0kCTW4+iy9UAXushZpOxubHvZAK1Xjpq/h\n" +
            "zRxTHoLvzWDQVOcOVXVqHdo4zOoAHjS9SWUITxc50amo/kwrMvQicUDgHnFqTI2M\n" +
            "KDkEd+EywYsuvpUBikECQQD4p0BZ5vZqWoepTTgMolGhgR2juf3abRsn2DAPKGi7\n" +
            "2qSNSy8hpXDA4COQT3u7We6vp++l6V5pYZRThahNu5khAkEA4cQCwIlQKyzSqNC7\n" +
            "lXWBSNxBArBD4eTsV3KL0OFpRUn4VXJICEqDPiDMPnSz7a5L3+zJFjvcl9MkMx5w\n" +
            "YQNfDwJAalc9EjXQlNN6yVEY825H8wZBItBcXJnkzzmJ9DGX+R8c2MCau40Bl7tD\n" +
            "+mfDxvBDQCRWPWJ82hy9TqsOu4/LgQJBANmRIKnVaknBT13akfutmqLRh2EE5tYp\n" +
            "281GGRF6OQ3KBCSsFljPHf1gdBP4OXFjF8G7GepwU9yIYIlW2yvSqOcCQHYR/AAD\n" +
            "3pL0/pT8hpn4dlArGTS/fcXudbIWLJqLAlRUkZfUhHVnkD8vIAd0pOuRLVY67mu9\n" +
            "yXCvBv5FJhz63xE=";
	public static final String RSA_PUBLIC = "";

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				Result resultObj = new Result((String) msg.obj);
				String resultStatus = resultObj.resultStatus;

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayDemoActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000” 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayDemoActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(PayDemoActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PayDemoActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PayDemoActivity.this);
				// 调用支付接口
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask payTask = new PayTask(PayDemoActivity.this);
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 获取外部订单号
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
