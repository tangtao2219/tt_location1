package com.file.tangtao.tt_location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/*create by tangtao 2017-2-20
启动页
* */

public class ActSplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.TYPE_STATUS_BAR, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activityactsplashscreen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ActSplashScreen.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("SelectID",0);
                intent.putExtras(bundle);
                startActivity(intent);
                ActSplashScreen.this.finish();
            }
        }, 1500);
    }

}
