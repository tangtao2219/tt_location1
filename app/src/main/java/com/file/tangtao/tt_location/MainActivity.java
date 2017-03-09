package com.file.tangtao.tt_location;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Call;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 *跳转到主页都要带参数（跳转到第几页）！！！！！
 Intent intent = new Intent(Setup_Activity.this,Login.class);
 Bundle bundle = new Bundle();
 bundle.putString("SelectID","0");
 intent.putExtras(bundle);
 startActivity(intent);
 * @author guolin
 */
public class MainActivity extends CheckPermissionsActivity implements View.OnClickListener
{
    private LinearLayout mTabMain;
    private LinearLayout mTabLook;
    private LinearLayout mTabMe;
    private ImageButton mMainImg;
    private ImageButton mLookImg;
    private ImageButton mMeImg;
    private TextView mMainText;
    private TextView mLookText;
    private TextView mMeText;
    private MainFragment mTab01;
    private LookFragment mTab02;
    private MeFragment mTab03;
    public String Latitude="";
    public String Longitude="";
    private SharedPreferences sp;
    private String  sessionid_temp="";
    OkHttpClient okHttpClient = new OkHttpClient();
//       private String mBaseUrl="http://192.168.31.213:8080/httpclient/";
 private String mBaseUrl="http://139.196.98.111:8080/httpclient/";
    private  FragmentManager fragmentManager;
    /** 声明mLocationOption对象 */
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器 不用看了
    public AMapLocationListener mLocationListener  = new AMapLocationListener() {
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
            //可在其中解析amapLocation获取相应内容。
                Latitude=""+amapLocation.getLatitude();//获取纬度
                Longitude=""+amapLocation.getLongitude();//获取经度
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
};

    //初始化定位
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
//初始化AMapLocationClientOption对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         initView();
           initEvents();
          fragmentManager = getSupportFragmentManager();
            int Select=0;
        Bundle bundle = this.getIntent().getExtras();
        Select = bundle.getInt("SelectID");
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        sessionid_temp = sp.getString("SessionId","defaultname");
        Log.e("sessionid_temp",sessionid_temp);
       if(sessionid_temp.equals("defaultname")) Select=1;
          setSelect(Select);
       //启动定位 创造新线程 发送位置到服务器
        new Thread(new Runnable(){
            @Override
            public void run(){
              int flag=0;
                while (flag==0)
                {
                    try {
                        String jingweidu= gaodeLocation();
                        if(jingweidu.length()>10){
                            String[] sourceStrArray=jingweidu.split(",");
                            if(!sessionid_temp.equals("defaultname")){
                                try {
                                    postlocation(sourceStrArray[0],sourceStrArray[1],sessionid_temp);
                                    flag=1;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        mLocationClient.onDestroy();
                 //600s定位一次
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
//发送位置到服务器
    public void postlocation(String longitude,String latitude,String session) throws IOException {
        FormBody.Builder requestBodyBuilder= new FormBody.Builder();
        RequestBody requestBody = requestBodyBuilder.add("longitude",longitude).add("latitude",latitude).add("sessionid",session).build();
        Request.Builder builder =new Request.Builder();
        Request request = builder.url(mBaseUrl + "postlocation").post(requestBody).build();
        executeRequest(request);
    }
    private void executeRequest(Request request) {
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
    //高德
    public String gaodeLocation(){
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //给定位客户端对象设置定位参数
//        mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
        Log.e("纬度：",Latitude);
        Log.d("经度：",Longitude);
       return Longitude+","+Latitude;
//        mLocationClient.stopLocation();
//        mLocationClient.onDestroy();
    }
    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initEvents()
    {
        mTabMain.setOnClickListener(this);
        mTabLook.setOnClickListener(this);
        mTabMe.setOnClickListener(this);
        mMainImg.setOnClickListener(this);
        mLookImg.setOnClickListener(this);
        mMeImg.setOnClickListener(this);
        mMainText.setOnClickListener(this);
        mLookText.setOnClickListener(this);
        mMeText.setOnClickListener(this);
    }

    private void initView()
    {
        mTabMain = (LinearLayout)findViewById(R.id.id_tab_main);
        mTabLook = (LinearLayout)findViewById(R.id.id_tab_look);
        mTabMe= (LinearLayout)findViewById(R.id.id_tab_me);
        mMainImg = (ImageButton)findViewById(R.id.id_tab_main_img);
        mLookImg = (ImageButton)findViewById(R.id.id_tab_look_img);
        mMeImg = (ImageButton)findViewById(R.id.id_tab_me_img);
        mMainText = (TextView)findViewById(R.id.main_text);
        mLookText = (TextView)findViewById(R.id.look_text);
        mMeText = (TextView)findViewById(R.id.me_text);
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     * 每个tab页对应的下标。0表示主页，1表示随便看看，2表示我的。
     */
    private void setSelect(int i)
    {
        resetImg();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragment(transaction);

        switch (i)
        {
            case 0:
                // 当点击了主页tab时，改变控件的图片和 文字颜色
                mMainImg.setImageResource(R.drawable.main_select);
                mMainText.setTextColor(Color.parseColor("#1296db"));
                if(mTab01 == null)
                {
                    mTab01 = new MainFragment();
                    transaction.add(R.id.id_concent,mTab01);
                }
                else
                {
                    transaction.show(mTab01);
                }
                break;
            case 1:
                mLookImg.setImageResource(R.drawable.look_select);
                mLookText.setTextColor(Color.parseColor("#1296db"));
                if(mTab02 == null)
                {
                    mTab02 = new LookFragment();
                    transaction.add(R.id.id_concent,mTab02);
                }
                else
                {
                    transaction.show(mTab02);
                }
                break;
            case 2:
                mMeImg.setImageResource(R.drawable.me_select);
                mMeText.setTextColor(Color.parseColor("#1296db"));
                if(mTab03 == null)
                {
                    mTab03 = new MeFragment();
                    transaction.add(R.id.id_concent,mTab03);
                }
                else
                {
                    transaction.show(mTab03);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *            用于对Fragment执行操作的事务
     */
    private void hideFragment(FragmentTransaction transaction)
    {
        if(mTab01 !=null)
        {
            transaction.hide(mTab01);
        }
        if(mTab02 !=null)
        {
            transaction.hide(mTab02);
        }
        if(mTab03 !=null)
        {
            transaction.hide(mTab03);
        }
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_tab_main:
                setSelect(0);
                break;
            case R.id.id_tab_look:
                setSelect(1);
                break;
            case R.id.id_tab_me:
                setSelect(2);
                break;
            case R.id.id_tab_main_img:
                setSelect(0);
                break;
            case R.id.id_tab_look_img:
                setSelect(1);
                break;
            case R.id.id_tab_me_img:
                setSelect(2);
                break;
            default:
                break;
        }
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void resetImg()
        {
            mMainImg.setImageResource(R.drawable.main_unselect);
            mLookImg.setImageResource(R.drawable.look_unselect);
            mMeImg.setImageResource(R.drawable.me_unselect);
            mMainText.setTextColor(Color.parseColor("#8a8a8a"));
            mLookText.setTextColor(Color.parseColor("#8a8a8a"));
            mMeText.setTextColor(Color.parseColor("#8a8a8a"));
        }
}
