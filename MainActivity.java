package com.example.hasee.changeicondemo;

import android.app.Service;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.smtt.sdk.QbSdk;

public class MainActivity extends AppCompatActivity implements View.OnLayoutChangeListener {
    private Button btn;
    private AmountView amountView;
    LinearLayout relativeLayout;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
        preinitX5WebCore();
        relativeLayout= (LinearLayout) findViewById(R.id.layout);
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;


        //预加载x5内核
        Intent intent = new Intent(this, AdvanceLoadX5Service.class);
        startService(intent);
        btn= (Button) findViewById(R.id.btn);
        amountView= (AmountView) findViewById(R.id.amountView);
        amountView.setListener(new AmountView.OnAmountChangeListener() {
          @Override
          public void onAmountChange(View view, int amount) {
          }
      });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WebViewActivity.class));
            }
        });
    }
    private void preinitX5WebCore() {
        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(getApplicationContext(), null);// 设置X5初始化完成的回调接口

            Toast.makeText(this,"修改了代码",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        screenHeight = getWindow().getDecorView().getHeight();
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //获取View可见区域的bottom
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                if(bottom!=0 && oldBottom!=0 && bottom - rect.bottom <= 0){
                    Toast.makeText(MainActivity.this, "隐藏", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "弹出", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {


    }

    public class AdvanceLoadX5Service extends Service {
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            initX5();
        }

        private void initX5() {
            //  预加载X5内核
            QbSdk.initX5Environment(getApplicationContext(), cb);
        }

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),"加载完成",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),"加载完成",Toast.LENGTH_LONG).show();
            }
        };

    }
}