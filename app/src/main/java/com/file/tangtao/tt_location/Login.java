package com.file.tangtao.tt_location;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*create by tangtao 2017-2-20
登陆页面
* */

public class Login extends AppCompatActivity {
    private ImageButton LoginBtn;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private String  username=null;
    private String  password=null;
    private TextView Register;
    private TextView Nologin;
    private CheckBox remember;
    private SharedPreferences sp;
    private String  passwordsha256;
    public ImageButton Delete_password;
    OkHttpClient okHttpClient = new OkHttpClient();
//        private String mBaseUrl="http://192.168.31.213:8080/httpclient/";
 private String mBaseUrl="http://139.196.98.111:8080/httpclient/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        usernameEditText = (EditText) findViewById(R.id.login_username);
        passwordEditText = (EditText) findViewById(R.id.login_password);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        remember = (CheckBox) findViewById(R.id.check_btn);
        LoginBtn = (ImageButton) findViewById(R.id.login_btn);
        Delete_password=(ImageButton)findViewById(R.id.delete_password);
        Delete_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordEditText.setText("");
            }
        });
        Register= (TextView)findViewById(R.id.register);
        Register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        Nologin= (TextView)findViewById(R.id.nologin);
        Nologin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        if(sp.getString("isChecked","").equals("true")){
            remember.setChecked(true);
            usernameEditText.setText(sp.getString("USER_NAME", ""));
            passwordEditText.setText(sp.getString("PASSWORD", ""));
        }else {
            usernameEditText.setText(sp.getString("USER_NAME", ""));
            passwordEditText.setText("");
            remember.setChecked(false);
        }

        LoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                username=usernameEditText.getText().toString();
                password=passwordEditText.getText().toString();
                passwordsha256=username+password;
                passwordsha256=sha256(passwordsha256,"");
                try {
                    if(username.length()==0||password.length()==0){
                        Toast.makeText(Login.this, "用户名和密码不能为空,请重新输入!", Toast.LENGTH_SHORT).show();
                    }else{

                        if(sp.getString("USER_NAME", "").equals(username)&&
                                sp.getString("PASSWORD", "").equals(password)){
                            Login(username,password);
                        }
                        else Login(username,passwordsha256);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
        Nologin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("SelectID","1");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void Login(String username,String password) throws IOException{
        FormBody.Builder requestBodyBuilder= new FormBody.Builder();
        RequestBody requestBody = requestBodyBuilder.add("username",username).add("password",password).build();
        Request.Builder builder =new Request.Builder();
        Request request = builder.url(mBaseUrl + "login").post(requestBody).build();
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
                final String res= response.body().string();
                Log.e("res",res);
                //不正确输出
                if(res==null)
                {Toast.makeText(Login.this, "发生了未知的错误！", Toast.LENGTH_SHORT).show();}
                else{
                    if (res.equals("0") || res.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (res.equals("0")) {
                                    Toast.makeText(Login.this, "密码不正确,请重新输入!", Toast.LENGTH_SHORT).show();
                                } else if (res.equals("-1")) {
                                    Toast.makeText(Login.this, "该用户不存在，请注册!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "发生了未知的错误！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //正确！
                                String[] ss = res.split(",");
                                SharedPreferences.Editor editor = sp.edit();
                                if (ss[1].length() != 0) {
                                    editor.putString("SessionId", ss[1]);

                                if (remember.isChecked()) {
                                    //记住用户名、密码、
                                    editor.putString("isChecked", "true");
                                    if (sp.getString("USER_NAME", "").equals(username) &&
                                            sp.getString("PASSWORD", "").equals(password)) {
                                        editor.putString("USER_NAME", username);
                                        editor.putString("PASSWORD", password);
                                        editor.commit();
                                    } else {
                                        editor.putString("USER_NAME", username);
                                        editor.putString("PASSWORD", passwordsha256);
                                        editor.commit();
                                    }
                                } else {
                                    Log.e("isChecked", "flase");
                                    editor.putString("isChecked", "flase");
                                    editor.commit();
                                }
                            }
                        }});

                    }
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("SelectID",0);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
