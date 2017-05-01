package com.example.yuantabonus.yuantabonus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class Notification extends AppCompatActivity {

    TextView msg;
    private RequestQueue mQueue;
    private StringRequest getRequest;
    private Button getmsg;
    private final static String mUrl = "http://140.113.65.49";

    Intent intent = new Intent();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_shopping_cart:
                    intent.setClass(Notification.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_accounts:
                    intent.setClass(Notification.this, Account.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    return true;
                case R.id.navigation_webView:
                    intent.setClass(Notification.this, Webview.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_settings:
                    intent.setClass(Notification.this, Setting.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_notifications);

        msg = (TextView) findViewById(R.id.msg);
        getmsg = (Button) findViewById(R.id.getmsg);
        mQueue = Volley.newRequestQueue(this);
        getRequest = new StringRequest(mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        msg.setText(s);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        msg.setText(volleyError.getMessage());
                    }
                } );
        getmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.add(getRequest);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_notifications);
    }
}
