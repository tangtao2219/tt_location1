package com.file.tangtao.tt_location;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Search_Activity extends FragmentActivity implements View.OnClickListener{

    private TextView lecture;
    private TextView user;
    private TextView school;
    private EditText editText;
    private Button search;
    private ImageView delete;

    private ViewPager mPageVp;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentAdapter mFragmentAdapter;
    private int currentIndex;//当前页卡编号
    private int screenWidth;//横线图片宽度
    /**
     * Tab的那个引导线
     */
    private ImageView mTabLineIv;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
//        lecture = (TextView) findViewById(R.id.tv_lecture);
//        user = (TextView) findViewById(R.id.tv_user);
//        school = (TextView) findViewById(R.id.tv_school);
//
//        lecture.setOnClickListener(this);
//        user.setOnClickListener(this);
//        school.setOnClickListener(this);
//
//        mTabs.add(searchLectureFragment);
//        mTabs.add(searchUserFragment);
//        mTabs.add(searchSchoolFragment);

////        Spinner spinner = (Spinner) findViewById(R.id.spinner);
////        // 建立数据源
////        String[] mItems = getResources().getStringArray(R.array.search);
////        // 建立Adapter并且绑定数据源
////        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        spinner .setAdapter(adapter);
////        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view,
////                                       int pos, long id) {
////                String[] search = getResources().getStringArray(R.array.search);
////                Toast.makeText(Search_Activity.this, "你点击的是:"+search[pos], 2000).show();
////            }
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////                // Another interface callback
////            }
////        });
//        mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        /**
//         * 初始化Adapter
//         */
//        mViewPager.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//
//        initTabLine();
        InitTextView();
        sEditText();
        init();
        initTabLineWidth();
//        InitImage();
//        InitViewPager();
    }

    /*
     * 初始化标签名
     */
    public void InitTextView(){
        lecture = (TextView) findViewById(R.id.tv_lecture);
        user = (TextView) findViewById(R.id.tv_user);
        school = (TextView) findViewById(R.id.tv_school);
        editText = (EditText)findViewById(R.id.search);
        search = (Button)findViewById(R.id.id_button_search);
        delete = (ImageView)findViewById(R.id.iv_delete);
        mTabLineIv = (ImageView) this.findViewById(R.id.cursor);
        mPageVp = (ViewPager) this.findViewById(R.id.viewpager);
        findViewById(R.id.trac).setOnClickListener(this);

        //edittext设置图标大小与 焦点
        Drawable leftDrawable = editText.getCompoundDrawables()[0];
        if(leftDrawable!=null){
            leftDrawable.setBounds(0, 0, 70, 70);
            editText.setCompoundDrawables(leftDrawable, editText.getCompoundDrawables()[1], editText.getCompoundDrawables()[2], editText.getCompoundDrawables()[3]);
        }
        editText.setFocusable(true);
        editText.requestFocus();
//        lecture.setOnClickListener(new txListener(0));
//        user.setOnClickListener(new txListener(1));
//        school.setOnClickListener(new txListener(2));
        search.setOnClickListener(this);
    }

    public void sEditText(){
        //设置删除图片的点击事件
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把EditText内容设置为空
                editText.setText("");
                //把ListView隐藏
                delete.setVisibility(View.GONE);
            }
        });

        //EditText添加监听
        editText.addTextChangedListener(new TextWatcher() {

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

    private void init() {
        SearchLectureFragment searchLectureFragment = new SearchLectureFragment();
        SearchUserFragment searchUserFragment = new SearchUserFragment();
        SearchSchoolFragment searchSchoolFragment = new SearchSchoolFragment();
        mFragmentList.add(searchLectureFragment);
        mFragmentList.add(searchUserFragment);
        mFragmentList.add(searchSchoolFragment);

        mFragmentAdapter = new FragmentAdapter(
                this.getSupportFragmentManager(), mFragmentList);
        mPageVp.setAdapter(mFragmentAdapter);
        mPageVp.setCurrentItem(0);
        lecture.setTextColor(Color.BLUE);

        mPageVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset,
                                       int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                        .getLayoutParams();

                Log.e("offset:", offset + "");
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->2; 2->1; 1->0
                 */

                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }
                mTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetImg();
                switch (position) {
                    case 0:
                        lecture.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        school.setTextColor(Color.BLUE);
                        break;
                    case 2:
                        user.setTextColor(Color.BLUE);
                        break;
                }
                currentIndex = position;
            }
        });

    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 3;
        mTabLineIv.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
//    private void resetTextView() {
//        mTabChatTv.setTextColor(Color.BLACK);
//        mTabFriendTv.setTextColor(Color.BLACK);
//        mTabContactsTv.setTextColor(Color.BLACK);
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.trac:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.tv_lecture:
                resetImg();
                lecture.setTextColor(Color.parseColor("#f8f8f8"));
                break;
            case R.id.tv_user:
                resetImg();
                user.setTextColor(Color.parseColor("#f8f8f8"));
                break;
            case R.id.tv_school:
                resetImg();
                school.setTextColor(Color.parseColor("#f8f8f8"));
                break;
            case R.id.id_button_search:

                break;
            default:
                break;
        }

    }

    /*
    * 原来的颜色*/
    private void resetImg()
    {
        lecture.setTextColor(Color.parseColor("#8a8a8a"));
        user.setTextColor(Color.parseColor("#8a8a8a"));
        school.setTextColor(Color.parseColor("#8a8a8a"));
    }


//    public class txListener implements View.OnClickListener{
//        private int index=0;
//
//        public txListener(int i) {
//            index =i;
//        }
//        @Override
//        public void onClick(View v) {
//            // TODO Auto-generated method stub
//            mPager.setCurrentItem(index);
//        }
//    }
//
//    /*
//     * 初始化图片的位移像素
//     */
//    public void InitImage(){
//        image = (ImageView)findViewById(R.id.cursor);
//        bmpW = BitmapFactory.decodeResource(getResources(),android.R.drawable.bottom_bar).getWidth();
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int screenW = dm.widthPixels;
//        offset = (screenW/3 - bmpW)/2;
//
//        //imgageview设置平移，使下划线平移到初始位置（平移一个offset）
//        Matrix matrix = new Matrix();
//        matrix.postTranslate(offset, 0);
//        image.setImageMatrix(matrix);
//    }
//
//    /*
//     * 初始化ViewPager
//     */
//    public void InitViewPager(){
//        mPager = (ViewPager)findViewById(R.id.viewpager);
//        fragmentList = new ArrayList<>();
//        Fragment searchLectureFragment= new SearchLectureFragment();
//        Fragment searchUserFragment= new SearchUserFragment();
//        Fragment searchSchoolFragment= new SearchSchoolFragment();
//        fragmentList.add(searchLectureFragment);
//        fragmentList.add(searchUserFragment);
//        fragmentList.add(searchSchoolFragment);
//
//        //给ViewPager设置适配器
//        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), (ArrayList<Fragment>) fragmentList));
//        mPager.setCurrentItem(0);//设置当前显示标签页为第一页
//        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
//    }
//
//
//    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
//        private int one = offset *2 +bmpW;//两个相邻页面的偏移量
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void onPageSelected(int arg0) {
//            // TODO Auto-generated method stub
//            Animation animation = new TranslateAnimation(currIndex*one,arg0*one,0,0);//平移动画
//            currIndex = arg0;
//            animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
//            animation.setDuration(200);//动画持续时间0.2秒
//            image.startAnimation(animation);//是用ImageView来显示动画的
//            int i = currIndex + 1;
//            Toast.makeText(Search_Activity.this, "您选择了第"+i+"个页卡", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
}




































//    @Override
//    public void onClick(View v) {
//        resetTextView();
//        switch (v.getId()) {
//            case R.id.tv_lecture:
//                lecture.setTextColor(Color.parseColor("#1298db"));
//                changeView(0);
//                break;
//            case R.id.tv_user:
//                lecture.setTextColor(Color.parseColor("#1298db"));
//                changeView(1);
//                break;
//            case R.id.tv_school:
//                changeView(2);
//                lecture.setTextColor(Color.parseColor("#1298db"));
//                break;
//            default:
//                break;
//        }
//    }
//    //手动设置ViewPager要显示的视图
//    private void changeView(int desTab)
//    {
//        mViewPager.setCurrentItem(desTab, true);
//    }
//
////    /**
////     * 功能：点击主页TAB事件
////     */
////    public class TabOnClickListener implements View.OnClickListener{
////        private int index=0;
////
////        public TabOnClickListener(int i){
////            index=i;
////        }
////
////        public void onClick(View v) {
////            mViewPager.setCurrentItem(index);//选择某一页
////        }
////
////    }
//
////    /**
////     * 功能：Fragment页面改变事件
////     */
////    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {
////
////        //当滑动状态改变时调用
////        public void onPageScrollStateChanged(int state) {
////
////        }
////
////        //当前页面被滑动时调用
////        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////            LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
////            //返回组件距离左侧组件的距离
////            lp.leftMargin = (int) ((positionOffset + position) * screenWidth / 3);
////            mTabLine.setLayoutParams(lp);
////        }
////
////        //当新的页面被选中时调用
////        public void onPageSelected(int position) {
////            //重置所有TextView的字体颜色
////            resetTextView();
////            switch (position) {
////                case 0:
////                    lecture.setTextColor(Color.parseColor("#1296db"));
////                    break;
////                case 1:
////                    user.setTextColor(Color.parseColor("#1296db"));
////                    break;
////                case 2:
////                    school.setTextColor(Color.parseColor("#1296db"));
////                    break;
////            }
////        }
////    }
//
//        /**
//     * 重置颜色
//     */
//    private void resetTextView() {
//        lecture.setTextColor(Color.parseColor("#8a8a8a"));
//        user.setTextColor(Color.parseColor("#8a8a8a"));
//        school.setTextColor(Color.parseColor("#8a8a8a"));
//    }
//
//    /**
//     * 根据屏幕的宽度，初始化引导线的宽度
//     */
//    private void initTabLine() {
//        mTabLine = (ImageView) findViewById(R.id.id_tab_line);
//
//        //获取屏幕的宽度
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
//        screenWidth = outMetrics.widthPixels;
//
//        //获取控件的LayoutParams参数(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
//        lp.width = screenWidth / 3;//设置该控件的layoutParams参数
//        mTabLine.setLayoutParams(lp);//将修改好的layoutParams设置为该控件的layoutParams
//    }
//
//    /**
//     * 定义自己的ViewPager适配器。
//     * 也可以使用FragmentPagerAdapter。
//     */
//    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {
//
//        public MyFrageStatePagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mTabs.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mTabs.size();
//        }
//
//    }
//
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("Search_ Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
//    }
//
//
//}
