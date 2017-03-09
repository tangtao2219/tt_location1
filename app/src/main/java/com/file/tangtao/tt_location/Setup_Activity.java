package com.file.tangtao.tt_location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Administrator on 2017/2/19 0019.
 */
public class Setup_Activity extends Activity{
    private Button button;
    private ImageButton imageButton;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //setTitle(R.string.title_location);
        imageButton=(ImageButton)findViewById(R.id.imageButton9);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setup_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        SharedPreferences.Editor editor = sp.edit();
                editor.putString("USER_NAME","");
                editor.putString("PASSWORD","");
                editor.putString("SessionId","");
                editor.remove("USER_NAME");
                editor.remove("PASSWORD");
                editor.remove("SessionId");
                editor.commit();// 提交修改
                Intent intent = new Intent(Setup_Activity.this,Login.class);
                Bundle bundle = new Bundle();
                bundle.putString("SelectID","2");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


//    // 存储sharedpreferences
//    public void setSharedPreference() {
//        sharedPreferences = getSharedPreferences("itcast", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("username", text1.getText().toString());
//        editor.putInt("password", getpw());
//        editor.commit();// 提交修改
//    }
//
//    // 清除sharedpreferences的数据
//    public void removeSharedPreference() {
//        sharedPreferences = getSharedPreferences("itcast", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove("username");
//        editor.remove("password");
//        editor.commit();// 提交修改
//    }
//
//    // 获得sharedpreferences的数据
//    public void getSahrePreference() {
//        String username = sharedPreferences.getString("username", "");
//        int password = sharedPreferences.getInt("password", 0);
//        String str = String.valueOf(password);
//        text1.setText(username);
//        text2.setText(str);
//    }

}
