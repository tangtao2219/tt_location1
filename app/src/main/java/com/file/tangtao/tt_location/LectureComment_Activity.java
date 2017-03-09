package com.file.tangtao.tt_location;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class LectureComment_Activity extends Activity implements View.OnClickListener{

    private ImageButton back;
    //    private ImageButton headpicture;
//    private TextView name;
//    private TextView tvcomment;
//    private TextView reply;
//    private TextView delete;
    private EditText etcomment;
    private Button send;
    private ImageView delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecture_comment);
        initView();
        initEvents();
        changeEditText();
    }

    private void initView(){
        back = (ImageButton) findViewById(R.id.back_img);
        etcomment = (EditText) findViewById(R.id.et_comment);
        send = (Button) findViewById(R.id.b_send);
        delete = (ImageView) findViewById(R.id.iv_delete);
    }

    private void initEvents(){
        back.setOnClickListener(this);
        etcomment.setOnClickListener(this);
        send.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.iv_delete:
                etcomment.setText("");
                delete.setVisibility(View.GONE);
            case R.id.b_send:

                break;
            default:
                break;
        }
    }

    private void changeEditText(){
        etcomment.setText("");
        //EditText监听
        etcomment.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}//文本改变之前执行

            @Override
            //文本改变的时候执行
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如果长度为0
                if (s.length() == 0) {
                    //隐藏“删除”图片
                    delete.setVisibility(View.GONE);
                } else {//长度不为0
                    //显示“删除图片”
                    delete.setVisibility(View.VISIBLE);
                }
            }
            public void afterTextChanged(Editable s) { }//文本改变之后执行
        });
    }
}
