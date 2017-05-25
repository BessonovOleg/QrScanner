package com.example.obessonov.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MainActivity extends Activity implements View.OnClickListener{

    Button btnScan;
    Button btn_setting;

    TextView tvDocName;
    TextView tvDocDate;
    TextView tvDocNo;
    TextView tvAgent;
    TextView tvDocSum;
    TextView tvDocState;
    TextView tvStateCheck;
    TextView tvDocMemo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = (Button) findViewById(R.id.btnscan);
        btnScan.setOnClickListener(this);

        btn_setting = (Button) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(this);

        tvDocName    = (TextView) findViewById(R.id.tvDocName);
        tvDocDate    = (TextView) findViewById(R.id.tvDocDate);
        tvDocNo      = (TextView) findViewById(R.id.tvDocNo);
        tvAgent      = (TextView) findViewById(R.id.tvAgent);
        tvDocSum     = (TextView) findViewById(R.id.tvDocSum);
        tvDocState   = (TextView) findViewById(R.id.tvDocState);
        tvStateCheck = (TextView) findViewById(R.id.tvStateCheck);
        tvDocMemo    = (TextView) findViewById(R.id.tvDocMemo);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnscan) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Наведите камеру на QR код");
            integrator.setCameraId(0);
            integrator.setOrientationLocked(true);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }

        if (v.getId() == R.id.btn_setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result != null){
            if(result.getContents() == null) {
                //Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(this,"Ok) Code = " + result.getContents(),Toast.LENGTH_SHORT).show();
                DbTask dbTask = new DbTask();
                dbTask.execute(result.getContents());
            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }


    class DbTask extends AsyncTask<String,String,Void>{

        DocumentInfo dInfo = new DocumentInfo();
        String strErrorInfo = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(strErrorInfo.length() > 0){
                Toast.makeText(getBaseContext(),"Ошибка: " + strErrorInfo,Toast.LENGTH_LONG).show();
            }

            tvDocName.setText(dInfo.getDoc_name());
            tvDocDate.setText(dInfo.getDoc_date());
            tvDocNo.setText(dInfo.getDoc_no());
            tvAgent.setText(dInfo.getDoc_ps1());
            tvDocSum.setText(dInfo.getDoc_sum());
            tvDocState.setText(dInfo.getDoc_done());
            tvStateCheck.setText(dInfo.getTxtResultCheck());
            tvDocMemo.setText(dInfo.getDoc_memo());

        }

        @Override
        protected Void doInBackground(String... params) {
            //"jdbc:jtds:sqlserver://192.168.6.2:1433/v2_kramatorsk_new;";

            StringBuilder strConnect = new StringBuilder();
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SETTING_NAME,MODE_PRIVATE);
            strConnect.append("jdbc:jtds:sqlserver://");
            strConnect.append(sharedPreferences.getString(Constants.SETTING_IP,""));
            strConnect.append(":");
            strConnect.append(sharedPreferences.getString(Constants.SETTING_PORT,""));
            strConnect.append("/");
            strConnect.append(sharedPreferences.getString(Constants.SETTING_DB_NAME,""));
            strConnect.append(";");

            String MSSQL_DB    = strConnect.toString();
            String MSSQL_LOGIN = sharedPreferences.getString(Constants.SETTING_LOGIN,"");
            String MSSQL_PASS  = sharedPreferences.getString(Constants.SETTING_PASSWORD,"");

            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                Connection con = null;
                Statement st = null;
                ResultSet rs = null;

                try {
                    con = DriverManager.getConnection(MSSQL_DB, MSSQL_LOGIN, MSSQL_PASS);
                    if (con != null) {
                        st = con.createStatement();
                        rs = st.executeQuery("exec DECODE_QR_CODE '" + params[0] + "'");
                        if (rs != null) {
                            while (rs.next()) {
                                //1 doc_id
                                //2 doc_date
                                //3 doc_no
                                //4 doc_name
                                //5 doc_ps1
                                //6 doc_memo
                                //7 doc_done
                                //8 doc_sum
                                //9 txtResultCheck
                                dInfo.setDoc_id(rs.getInt(1));
                                dInfo.setDoc_date(rs.getString(2));
                                dInfo.setDoc_no(rs.getString(3));
                                dInfo.setDoc_name(rs.getString(4));
                                dInfo.setDoc_ps1(rs.getString(5));
                                dInfo.setDoc_memo(rs.getString(6));
                                dInfo.setDoc_done(rs.getString(7));
                                dInfo.setDoc_sum(rs.getString(8));
                                dInfo.setTxtResultCheck(rs.getString(9));
                            }
                        }
                    }
                } catch (SQLException e) {
                    //e.printStackTrace();
                    strErrorInfo = e.getMessage();
                } finally {
                    try {
                        if (rs != null) rs.close();
                        if (st != null) st.close();
                        if (con != null) con.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
                strErrorInfo = e.getMessage();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

}
