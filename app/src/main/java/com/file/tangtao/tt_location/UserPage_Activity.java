package com.file.tangtao.tt_location;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class UserPage_Activity extends Activity implements View.OnClickListener
{
    private TextView follow;
    private TextView collect;
    private TextView comment;
    private ImageButton back;
    private ImageButton edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage);
        //setTitle(R.string.title_location);

        initView();
        initEvents();

       // fragmentManager = getSupportFragmentManager();
    }

    private void initEvents()
    {
        follow.setOnClickListener(this);
        collect.setOnClickListener(this);
        comment.setOnClickListener(this);
        back.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

    private void initView()
    {
        follow = (TextView) findViewById(R.id.foucs_text);
        collect = (TextView) findViewById(R.id.collect_text);
        comment= (TextView) findViewById(R.id.comment_text);
        back = (ImageButton)findViewById(R.id.imageButton);
        edit = (ImageButton)findViewById(R.id.imageButton2);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageButton:
                finish();
                break;
            case R.id.imageButton2:
               // setSelect(0);
                Intent intent3 =new Intent(UserPage_Activity.this,UserPageEdit_Activity.class);
                startActivity(intent3);
                break;
            case R.id.foucs_text:
                Intent intent =new Intent(UserPage_Activity.this,Follow_Activity.class);
                startActivity(intent);
                break;
            case R.id.collect_text:
                Intent intent1 =new Intent(UserPage_Activity.this,Collect_Activity.class);
                startActivity(intent1);
                break;
            case R.id.comment_text:
                Intent intent2 =new Intent(UserPage_Activity.this,Comment_Activity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
