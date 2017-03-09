package com.file.tangtao.tt_location.fragment;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.file.tangtao.tt_location.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*create by tangtao 2017-2-20
主页的listview
* */

public class ForumDisplayAdapter extends BaseAdapter {

    private String test;
    private Activity context;
    public ForumDisplayAdapter(Activity context,String test) {
        this.context=context;
        this.test=test;
    }

    @Override
    public int getCount() {
        JSONObject jsonObject= null;//我们需要把json串看成一个大的对象
        try {
            if(test==null) return 0;
            jsonObject = new JSONObject(test);
            if(jsonObject==null) return 0;
            JSONArray jsonArray=jsonObject.getJSONArray("test");//这里获取的是装载有所有pet对象的数组
            return  jsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=context.getLayoutInflater().inflate(R.layout.forum_display_item,null);
        }
        TextView title = (TextView) convertView
                .findViewById(R.id.forumDisplayTitle);
        TextView replyCnt = (TextView) convertView
                .findViewById(R.id.replyCnt);
        TextView viewsCnt = (TextView) convertView
                .findViewById(R.id.viewsCnt);
        TextView userName = (TextView) convertView
                .findViewById(R.id.postUserName);
        ImageViewWithCache img = (ImageViewWithCache) convertView
                .findViewById(R.id.headImg);
        JSONObject jsonObject= null;//我们需要把json串看成一个大的对象
        String title_1=null,autor=null,point_count=null,sorce=null;
        try {
            jsonObject = new JSONObject(test);

            JSONArray jsonArray=jsonObject.getJSONArray("test");//这里获取的是装载有所有test对象的数组
            JSONObject jsonpet = jsonArray.getJSONObject(position);//获取这个数组中第一个pet对象
            //   Log.e(String.valueOf(jsonArray.length()),"length");
            title_1=jsonpet.getString("title");//获取pet对象的参数
            autor=jsonpet.getString("autor");
            point_count=jsonpet.getString("point_count");
            sorce=jsonpet.getString("sorce");
            title.setText(title_1);
            replyCnt.setText(sorce);
            viewsCnt.setText(point_count);
            userName.setText(autor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //	replyCnt.setText(item.getString("replycount"));

//        if (item.getInteger("avatar") == 1) {
//            try {
//                img.setImageUrl(new URL(Api.getInstance()
//                        .getUserHeadImageUrl(item.getInteger("postuserid"))));
//            } catch (MalformedURLException e) {
//                // TODO 自动生成的 catch 块
//                e.printStackTrace();
//            }
//        } else {
//            img.setImageResource(R.mipmap.default_user_head_img);
//        }
        img.setImageResource(R.mipmap.ic_launcher);
//        convertView.findViewById(R.id.forumDisplayLock).setVisibility(
//                (View.VISIBLE));

        return convertView;
    }


}

