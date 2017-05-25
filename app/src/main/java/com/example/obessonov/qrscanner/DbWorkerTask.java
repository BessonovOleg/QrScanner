package com.example.obessonov.qrscanner;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbWorkerTask extends AsyncTask <String,Void,String>{

    final static String MSSQL_DB = "jdbc:jtds:sqlserver://192.168.6.2:1433:/v2_kramatorsk_new;";
    final static String MSSQL_LOGIN = "admin";
    final static String MSSQL_PASS= "databaseowner";

    @Override
    protected String doInBackground(String... params) {


        System.out.println(params[0]);
        /*


        String res = "";
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection con = null;
            Statement st = null;
            ResultSet rs = null;

            System.out.println("INIT OK");
            try {
                con = DriverManager.getConnection(MSSQL_DB, MSSQL_LOGIN, MSSQL_PASS);
                System.out.println("CONNECT....");
                if (con != null) {
                    st = con.createStatement();
                    rs = st.executeQuery("select top 1 doc_id from documents");
                    if (rs != null) {
                        while (rs.next()) {
                         //   rowObject.put(rs.getMetaData().getColumnName(i), (rs.getString(i) != null) ? rs.getString(i) : "");
                            System.out.println(rs.getString(0));
                            res = rs.getString(0);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        }

        return res;
        */

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
