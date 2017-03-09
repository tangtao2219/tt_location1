package com.file.tangtao.tt_location;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.file.tangtao.tt_location.fragment.ForumDisplayAdapter;
import com.file.tangtao.tt_location.fragment.XListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainFragment extends android.support.v4.app.Fragment
{
    private ImageButton newThreadBtn;
    private EditText search;
    Context context;
    private ProgressBar progressBar;
    private XListView xListView;
   private String mBaseUrl="http://139.196.98.111:8080/httpclient/";
//      private String mBaseUrl="http://192.168.31.213:8080/httpclient/";
    private  String jsonresult;
    String jsonresult1="";
    String  result="";
    private SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.forum_display_page,container,false);
        search = (EditText)view.findViewById(R.id.search);
        search.setFocusable(false);
        /*搜索放大镜大小调整*/
        Drawable leftDrawable = search.getCompoundDrawables()[0];
        if(leftDrawable!=null){
            leftDrawable.setBounds(0, 0, 70, 70);
            search.setCompoundDrawables(leftDrawable, search.getCompoundDrawables()[1], search.getCompoundDrawables()[2], search.getCompoundDrawables()[3]);
        }

        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Search_Activity.class);
                startActivity(intent);
            }
        });

        xListView=(XListView)view.findViewById(R.id.forumDisplayListView);
        jsonresult=Json_bject();
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() throws IOException {
                Log.e("onRefresh1","onRefresh1");
                int count=0;result="";
                while(result.length()==0&&count<2){
                    try {
                        Log.e("onRefresh2","onRefresh2");
                        result=zhueyejson();
                        Log.e("onRefresh3","onRefresh3");
                        Thread.sleep(600);count++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                xListView.setAdapter(new ForumDisplayAdapter(getActivity(),result));
                xListView.stopLoadMore();
                //停止刷新
                xListView.stopRefresh();
            }
            @Override
            public void onLoadMore() {
                Log.d("onLoadMore","onLoadMore");
                //停止加载更多
                xListView.stopLoadMore();
                //停止刷新
                xListView.stopRefresh();
            }
        });
        int count=0;
        while(result.length()==0&&count<10){
            try {
                result=zhueyejson();
                Thread.sleep(600);count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        xListView.setAdapter(new ForumDisplayAdapter(getActivity(),result));

        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position < 1)
                    return;
                int data=0;String lectureid="";
                JSONObject jsonObject= null;//我们需要把json串看成一个大的对象
                try {
                    jsonObject = new JSONObject(result);
                    JSONArray jsonArray=jsonObject.getJSONArray("test");//这里获取的是装载有所有test对象的数组
                    JSONObject jsonpet = jsonArray.getJSONObject(position-1);
                    lectureid =jsonpet.getString("lectureid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getActivity(), LectureContent_Activity.class);
                // intent.putExtra("letureID",lectureid);
                Bundle bundle = new Bundle();
                bundle.putString("letureID", lectureid);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
                                         }
        );
        xListView.setPullLoadEnable(true);
        return view;
    }


    //接收数据 根据传的session判断！！！！！！！！
    public String zhueyejson() throws IOException {
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String  sessionid_temp = sp.getString("SessionId","defaultname");
        Request.Builder builder =new Request.Builder();
        SimpleDateFormat sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String   date   =   sDateFormat.format(new   java.util.Date());
        Request request= builder.get().url(mBaseUrl+"zhuyejson?sessionid="+sessionid_temp+"&&refreshtime="+date).build();
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
                Log.e("test","222  "+jsonresult1);
            }
        });
        return jsonresult1;
    }






    //模拟json
    private String Json_bject() {
        JSONObject object = new JSONObject();
        String jsonresult="";
        try {
            JSONArray jsonarray = new JSONArray();//json数组，里面包含的内容为pet的所有对象
            JSONObject[] jsonObj=new JSONObject[10];
            int i=0;
            //  for(int i=0;i<10;i++) {
            jsonObj[i] = new JSONObject();
            jsonObj[i].put("title", "a");//向对象里面添加值
            jsonObj[i].put("autor", "b");
            jsonObj[i].put("point_count",i);
            jsonObj[i].put("sorce", "10");
            jsonarray.put(jsonObj[i]);//向json数组里面添加pet对象
            //   }
            object.put("test", jsonarray);//向总对象里面添加包含pet的数组
            jsonresult = object.toString();//生成返回字符串

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return jsonresult;
        }
        return jsonresult;
    }

}
