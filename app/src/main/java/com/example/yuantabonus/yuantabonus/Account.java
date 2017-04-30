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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Account extends AppCompatActivity {

    Intent intent = new Intent();
    TextView TextView_event_log,TextView_balanceOf;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_shopping_cart:
                    intent.setClass(Account.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_accounts:
                    return true;
                case R.id.navigation_notifications:
                    intent.setClass(Account.this, Notification.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_webView:
                    intent.setClass(Account.this, Webview.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_settings:
                    intent.setClass(Account.this, Setting.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_accounts);

        TextView_balanceOf = (TextView)findViewById(R.id.TextView_balanceOf);
        TextView_event_log = (TextView)findViewById(R.id.TextView_event_log);
        TextView_event_log.setMovementMethod(new ScrollingMovementMethod());


        balanceOf(); //分割成獨立的function
        new_eventFilter(); //分割成獨立的function
    }

    public void balanceOf(){

        final ExecutorService service = Executors.newSingleThreadExecutor();

        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();
                    JSONObject params_object = new JSONObject();
                    JSONArray params = new JSONArray();
                    json.put("method", "eth_call");
                    //params_object.put("from", "0x13f42EcB9fBF94Ff33cD22828070F2FA10048a27");
                    params_object.put("to", "0x19ecbD8f51a13E5Ed533f8c5736e71B3cC1f5C07");
                    params_object.put("data", "0x70a0823100000000000000000000000013f42ecb9fbf94ff33cd22828070f2fa10048a27");
                    params.put(params_object);
                    params.put("latest");
                    json.put("params", params);
                    json.put("id", 1);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString().getBytes());

                    Request request = new Request.Builder()
                            .url("http://140.113.72.54:8545")
                            .post(requestBody)
                            .build();
                    // balanceOf(address) = "0x70a08231" 取前4個bytes => 0x + 8個字
                    // address = 0x13f42ecb9fbf94ff33cd22828070f2fa10048a27 => 00000000000000000000000013f42ecb9fbf94ff33cd22828070f2fa10048a27
                    //靠右邊開始 往左填滿32bytes
                    // http://www.rapidtables.com/convert/number/ascii-to-hex.htm 轉address

                    OkHttpClient client = new OkHttpClient();
                    final Response response = client.newCall(request).execute();
                    final String resStr = response.body().string();
                    JSONObject j = new JSONObject(resStr);
                    String result = j.getString("result");
                    result = result.substring(2,result.length());
                    final Long balanceOf = Long.parseLong(result, 16);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView_balanceOf.setText("元大幣餘額: " + balanceOf);
                            Log.d("balanceOf: ", ""+balanceOf);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void new_eventFilter(){

        final ExecutorService service = Executors.newSingleThreadExecutor();

        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();
                    JSONObject params_object = new JSONObject();
                    JSONArray params = new JSONArray();
                    json.put("method", "eth_newFilter");
                    params_object.put("fromBlock", "0x1F7F");
                    //params_object.put("toBlock", "latest");
                    params_object.put("address", "0x19ecbD8f51a13E5Ed533f8c5736e71B3cC1f5C07");
                    params.put(params_object);
                    json.put("params", params);
                    json.put("id", 2);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString().getBytes());

                    Request request = new Request.Builder()
                            .url("http://140.113.72.54:8545")
                            .post(requestBody)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    final Response response = client.newCall(request).execute();
                    final String resStr = response.body().string();
                    Log.d("resStr: ", ""+resStr);
                    JSONObject j = new JSONObject(resStr);
                    final String result = j.getString("result");
                    //result = result.substring(2,result.length());
                    //final Long event = Long.parseLong(result, 16);

                    get_eventFilter(result);    // 傳註冊成功的Filter_Id 給這個函式

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "註冊Filter成功", Toast.LENGTH_SHORT).show();
                            Log.d("result: ", ""+result);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void get_eventFilter(final String filter_Id){

        final ExecutorService service = Executors.newSingleThreadExecutor();

        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();
                    JSONArray params = new JSONArray();

                    json.put("method", "eth_getFilterLogs");
                    params.put(filter_Id);
                    json.put("params", params);
                    json.put("id", 3);

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString().getBytes());

                    Request request = new Request.Builder()
                            .url("http://140.113.72.54:8545")
                            .post(requestBody)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    final Response response = client.newCall(request).execute();
                    final String result_String = response.body().string();
                    Log.d("result_String: ", ""+result_String);

                    JSONObject result_String_Object = new JSONObject(result_String);
                    String result = result_String_Object.getString("result");

                    JSONArray result_array = new JSONArray(result);
                    result = result_array.getString(1);

                    //result_String_Object = new JSONObject(result_array.getString(0));
                    //final String final_result = result_String_Object.getString("data");

                    result = result.substring(result.indexOf("{")).replace(",",",\n\n");
                    result = result.substring(result.indexOf("{")).replace(":",":\n");
                    result = result.substring(result.indexOf("{")).replace("[","[\n");
                    final String final_result = result.substring(result.indexOf("{")).replace("]","\n]");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("result: ", ""+final_result);
                            TextView_event_log.setText(final_result);
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
        navigation.setSelectedItemId(R.id.navigation_accounts);
    }
}
