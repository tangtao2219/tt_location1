package com.file.tangtao.tt_location;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.R.attr.value;

public class MeFragment extends android.support.v4.app.Fragment
{
    private TextView name;
    private TextView signature;
    private TextView follow;
    private TextView comment;
    private TextView collect;
    private TextView feedback;
    private TextView setup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me,container,false);

        name = (TextView)view.findViewById(R.id.nickname_text);
        signature = (TextView)view.findViewById(R.id.page_text);
        follow = (TextView)view.findViewById(R.id.foucs_text);
        collect = (TextView)view.findViewById(R.id.collect_text);
        comment = (TextView)view.findViewById(R.id.comment_text);
        feedback = (TextView)view.findViewById(R.id.feedback_text);
        setup = (TextView)view.findViewById(R.id.setup_text);

        signature.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserPage_Activity.class);
                startActivity(intent);
            }
        });

        name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(this,"dingwei",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), UserPage_Activity.class);
                startActivity(intent);
            }
        });

        follow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Follow_Activity.class);
                startActivity(intent);
            }
        });

        collect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Collect_Activity.class);
                startActivity(intent);
            }
        });

        comment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Comment_Activity.class);
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FeedBack_Activity.class);
                startActivity(intent);
            }
        });

        setup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Setup_Activity.class);
                startActivity(intent);
            }
        });
    return view;
    }
}
