package com.example.yuantabonus.yuantabonus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Setting extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();

    Intent intent = new Intent();
    TextView TextView_testPhp;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_shopping_cart:
                    intent.setClass(Setting.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_accounts:
                    intent.setClass(Setting.this, Account.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    intent.setClass(Setting.this, Notification.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_webView:
                    intent.setClass(Setting.this, Webview.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_settings);

        TextView_testPhp = (TextView) findViewById(R.id.TextView_testPhp);
        TextView_testPhp.setMovementMethod(new ScrollingMovementMethod());

        testPhp(); //分割成獨立的function
    }

    public void testPhp(){

        final ExecutorService service = Executors.newSingleThreadExecutor();

        service.submit(new Runnable() {
            @Override
            public void run() {
                try {

                    Request request = new Request.Builder()
                            .url("http://140.113.65.49/android/test.php")
                            .build();

                    final Response response = client.newCall(request).execute();
                    final String resStr = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView_testPhp.setText("test.php: " + resStr);
                            Log.d("test.php: ", ""+resStr);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_settings);
    }
}
