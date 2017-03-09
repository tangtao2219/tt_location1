package com.file.tangtao.tt_location;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/*create by tangtao 2017-2-20
注册
* */

public class Register extends AppCompatActivity {
private CheckBox checkBox;
    private RadioButton man;
    private RadioButton female;
    private RadioGroup sex;
    private String sex_string="M";
    private String username="";
    private String password="";
    private String password_repeat="";
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordEditText_repeat;
    private String checkBox_law="false",checkBox_literature="false",checkBox_education="false",checkBox_economics="false",checkBox_diplomacy="false",checkBox_philosophy="false";
    private CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6;
    private Button button;
    private String  interest="";
    private SharedPreferences sp;
    OkHttpClient okHttpClient = new OkHttpClient();
//     private String mBaseUrl="http://192.168.31.213:8080/httpclient/";
    private String mBaseUrl="http://139.196.98.111:8080/httpclient/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sex= (RadioGroup) findViewById(R.id.radioGroupID);
        man= (RadioButton) findViewById(R.id.radioButton3);
        female=(RadioButton)findViewById(R.id.radioButton4);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==female.getId()){
                    sex_string="W";
                }
                else {
                    sex_string="M";
                }
            }
        });
        usernameEditText=(EditText)findViewById(R.id.editText6);
        passwordEditText =(EditText)findViewById(R.id.editText9);
        passwordEditText_repeat=(EditText)findViewById(R.id.editText7);
        checkBox1=(CheckBox)findViewById(R.id.checkBox_law);
        checkBox2=(CheckBox)findViewById(R.id.checkBox_literature);
        checkBox3=(CheckBox)findViewById(R.id.checkBox_education);
        checkBox4=(CheckBox)findViewById(R.id.checkBox_economics);
        checkBox5=(CheckBox)findViewById(R.id.checkBox_diplomacy);
        checkBox6=(CheckBox)findViewById(R.id.checkBox_philosophy);
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    checkBox_law="true";
                    Log.e("checkBox_law","true");
                }else {
                    checkBox_law="false";
                }

            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    checkBox_literature="true";
                }else {
                    checkBox_literature="false";
                }
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    checkBox_education="true";
                }else {
                    checkBox_education="false";
                }
            }
        });
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    checkBox_economics="true";
                }else {
                    checkBox_economics="false";
                }
            }
        });
        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    checkBox_diplomacy="true";
                }else {
                    checkBox_diplomacy="false";
                }
            }
        });
        checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    checkBox_philosophy="true";
                }else {
                    checkBox_philosophy="false";
                }
            }
        });
        button=(Button)findViewById(R.id.button5);

        Log.e(interest,"interest1");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=usernameEditText.getText().toString();
                password=passwordEditText.getText().toString();
                password_repeat=passwordEditText_repeat.getText().toString();
                if(checkBox_law.equals("true")) {interest=interest+"Law,";}
                if(checkBox_literature.equals("true")){interest=interest+"Literature,";}
                if(checkBox_education.equals("true")) {interest=interest+"Education,";}
                if(checkBox_economics.equals("true")) {interest=interest+"Economics,";}
                if(checkBox_diplomacy.equals("true")) {interest=interest+"Diplomacy,";}
                if(checkBox_philosophy.equals("true")) {interest=interest+"Philosophy,";}
                if(username.length()>=4){
                    String regEx = "^[A-Za-z0-9]+$";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(username);
                    boolean rs = matcher.matches();
                    if(rs){
                       if(password.equals(password_repeat)){
                           int count=0;
                           if(checkBox_law.equals("true")) {count++;}
                           if(checkBox_literature.equals("true")){count++;}
                           if(checkBox_education.equals("true")){count++;}
                           if(checkBox_economics.equals("true")) {count++;}
                           if(checkBox_diplomacy.equals("true")) {count++;}
                           if(checkBox_philosophy.equals("true")) {count++;}
                            if(count>=1){
                                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                                if(networkInfo == null || !networkInfo.isAvailable())
                                {
                                    //当前没联网
                                    Toast.makeText(Register.this,"当前没有网络",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    try {
                                        password=username+password;
                                        password=sha256(password,"");
                                        register();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else  Toast.makeText(Register.this,"请至少选择一个兴趣！",Toast.LENGTH_SHORT).show();
                       }else Toast.makeText(Register.this,"请两次输入相同的密码！",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(Register.this,"用户名只能是字母或数字！",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(Register.this,"用户名至少4位字母或数字！",Toast.LENGTH_SHORT).show();
                interest="";
            }

        });


    }

    //接收数据
    public void register() throws IOException {
        Request.Builder builder =new Request.Builder();
        Log.e("interest2",interest);
        Request request= builder.get().url(mBaseUrl+"register?username="+username+"&&password="+password+"&&usersex="+sex_string+"&&register_interest="+interest).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res=response.body().string();
                Log.e(res,"res");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] sourceStrArray=res.split(",");
                        if (sourceStrArray[0].equals("1")) {
                            Toast.makeText(Register.this, "注册成功！！请返回登陆", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("SessionId", sourceStrArray[1]);
                            editor.putString("USER_NAME", username);
                            editor.putString("PASSWORD",password);
                            editor.commit();
                            Intent intent = new Intent(Register.this,Login.class);
                            startActivity(intent);
                        } else if (sourceStrArray[0].equals("-1")) {
                            Toast.makeText(Register.this, "该用户名已注册,请直接登陆！！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Register.this, "发生了未知的错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


    //sha256加密
    public static String sha256(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

}
