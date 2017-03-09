package com.file.tangtao.tt_location;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LectureContent_Activity extends Activity implements View.OnClickListener{

    private ImageButton back;
    private TextView content;
    private EditText etcomment;
    private Button send;
    private ImageView ivcomment;
    private ImageView ivcollect;
    private ImageView ivshare;
    private ImageView delete;

    private String mBaseUrl="http://139.196.98.111:8080/httpclient/";
    //private String mBaseUrl="http://192.168.31.213:8080/httpclient/";
    private String lectureid="";
    private String Content_text="";
    private String jsonresult1="";

    int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecture_content);

        Bundle bundle = this.getIntent().getExtras();
        lectureid = bundle.getString("letureID");
         ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isAvailable())
        {
            //当前没联网
            Toast.makeText(LectureContent_Activity.this,"当前没有网络",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //当前联网
            if(lectureid.length()!=0){
                try {
                    int count=0;
                    while (Content_text.length()==0&&count<200) {
                        try {
                            Content_text = readLecturecontent(lectureid);
                            Thread.sleep(10);count++;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(count==200) {
                        Toast.makeText(LectureContent_Activity.this,"网络超时",Toast.LENGTH_SHORT).show();
                    }
                    //得到Content_text里的值；
                    Log.e("Content_text",Content_text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       

        initView();
        content.setText(Content_text);
        initEvents();
        changeCollect();
        changeEditText();
    }

    private void initView(){
        back = (ImageButton) findViewById(R.id.back_img);
        content = (TextView) findViewById(R.id.tv_content);
        etcomment = (EditText) findViewById(R.id.et_comment);
        send = (Button) findViewById(R.id.b_send);
        ivcomment = (ImageView) findViewById(R.id.iv_comment);
        ivcollect = (ImageView) findViewById(R.id.iv_collect);
        ivshare = (ImageView) findViewById(R.id.iv_share);
        delete = (ImageView) findViewById(R.id.iv_delete);
    }

    private void initEvents(){
        back.setOnClickListener(this);
        content.setOnClickListener(this);
        send.setOnClickListener(this);
        ivcomment.setOnClickListener(this);
        ivcollect.setOnClickListener(this);
        ivshare.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    //接收数据
    public String readLecturecontent(String lectureid) throws IOException {
        Request.Builder builder =new Request.Builder();
        Request request= builder.get().url(mBaseUrl+"readLectureContent?lectureid="+lectureid).build();
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
                jsonresult1=res.toString();

            }
        });
        return jsonresult1;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.back_img:
                finish();
                break;
            case R.id.tv_content:

                break;
//            case R.id.et_comment:
//                //EditText添加监听
//                etcomment.setText("");
//                etcomment.addTextChangedListener(new TextWatcher() {
//
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}//文本改变之前执行
//
//                    @Override
//                    //文本改变的时候执行
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        //如果长度为0
//                        if (s.length() == 0) {
//                            //隐藏“删除”图片
//                            delete.setVisibility(View.GONE);
//                            send.setVisibility(View.GONE);
//                            ivcomment.setVisibility(View.VISIBLE);
//                            ivcollect.setVisibility(View.VISIBLE);
//                            ivshare.setVisibility(View.VISIBLE);
//                        } else {//长度不为0
//                            //显示“删除图片”
//                            delete.setVisibility(View.VISIBLE);
//                            send.setVisibility(View.VISIBLE);
//                            ivcomment.setVisibility(View.GONE);
//                            ivcollect.setVisibility(View.GONE);
//                            ivshare.setVisibility(View.GONE);
//                        }
//                    }
//                    public void afterTextChanged(Editable s) { }//文本改变之后执行
//                });
//                break;
            case R.id.b_send:

                break;
            case R.id.iv_comment:
                Intent intent = new Intent(LectureContent_Activity.this,LectureComment_Activity.class);
                startActivity(intent);
                break;
            case R.id.iv_delete:
                etcomment.setText("");
                delete.setVisibility(View.GONE);
                break;
            case R.id.iv_share:

                break;
            default:
                break;

        }
    }

    private void changeCollect(){
        ivcollect.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (flag){
                    case 0:
                        ivcollect.setImageResource(android.R.drawable.btn_star_big_off);
                        flag = 1;
                        break;
                    case 1:
                        ivcollect.setImageResource(android.R.drawable.btn_star_big_on);
                        flag = 0;
                        break;
                }
            }
        });
    }

    private void changeEditText(){
        etcomment.setText("");
        etcomment.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}//文本改变之前执行

            @Override
            //文本改变的时候执行
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如果长度为0
                if (s.length() == 0) {
                    //隐藏“删除”图片
                    delete.setVisibility(View.GONE);
                    send.setVisibility(View.GONE);
                    ivcomment.setVisibility(View.VISIBLE);
                    ivcollect.setVisibility(View.VISIBLE);
                    ivshare.setVisibility(View.VISIBLE);
                } else {//长度不为0
                    //显示“删除图片”
                    delete.setVisibility(View.VISIBLE);
                    send.setVisibility(View.VISIBLE);
                    ivcomment.setVisibility(View.GONE);
                    ivcollect.setVisibility(View.GONE);
                    ivshare.setVisibility(View.GONE);
                }
            }
            public void afterTextChanged(Editable s) { }//文本改变之后执行
        });
    }
}
