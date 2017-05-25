package com.example.obessonov.qrscanner;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class SettingActivity extends Activity {

    EditText ed_IpAddress;
    EditText ed_Port;
    EditText ed_DbName;
    EditText ed_login;
    EditText ed_password;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ed_IpAddress = (EditText) findViewById(R.id.ed_IpAddress);
        ed_Port      = (EditText) findViewById(R.id.ed_Port);
        ed_DbName    = (EditText) findViewById(R.id.ed_DbName);
        ed_login     = (EditText) findViewById(R.id.ed_login);
        ed_password  = (EditText) findViewById(R.id.ed_password);
        loadSetting();
    }

    private void loadSetting(){
        sPref = getSharedPreferences(Constants.SETTING_NAME,MODE_PRIVATE);
        ed_IpAddress.setText(sPref.getString(Constants.SETTING_IP,""));
        ed_Port.setText(sPref.getString(Constants.SETTING_PORT,""));
        ed_DbName.setText(sPref.getString(Constants.SETTING_DB_NAME,""));
        ed_login.setText(sPref.getString(Constants.SETTING_LOGIN,""));
        ed_password.setText(sPref.getString(Constants.SETTING_PASSWORD,""));
    }

    private void savedSetting(){
        sPref = getSharedPreferences(Constants.SETTING_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(Constants.SETTING_IP,ed_IpAddress.getText().toString());
        editor.putString(Constants.SETTING_PORT,ed_Port.getText().toString());
        editor.putString(Constants.SETTING_DB_NAME,ed_DbName.getText().toString());
        editor.putString(Constants.SETTING_LOGIN,ed_login.getText().toString());
        editor.putString(Constants.SETTING_PASSWORD,ed_password.getText().toString());
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savedSetting();
    }
}
