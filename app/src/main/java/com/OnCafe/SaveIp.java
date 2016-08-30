package com.OnCafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.OnCafe.Management.ManagerCategory;
import com.OnCafe.Waiter.WaiterHome;
import com.OnCafe.Waiter.new_trial;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Bishal on 8/16/2015.
 */
public class SaveIp extends ActionBarActivity {
EditText inputIp;
    String ipAddress;
    TextView yourip;
SharedPreferences sTableNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_ip);
        yourip=(TextView)findViewById(R.id.yourip);
        yourip.setText(getIpAddress());

    }
    public void onSaveClicked(View view){
        inputIp = (EditText)findViewById(R.id.editIp);
        ipAddress = inputIp.getText().toString();
        sTableNo= getSharedPreferences("Ip Address",0);
        SharedPreferences.Editor editor=sTableNo.edit();
        editor.putString("Ip", ipAddress);
        editor.apply();
        inputIp.setText("");

        Toast.makeText(SaveIp.this,
                "Ip saved",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SaveIp.this,new_trial.class);
        finish();
        startActivity(i);
    }
    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "IP Address:\n"
                                + inetAddress.getHostAddress() + "";
                    }

                }

            }

        } catch (SocketException e) {

            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }
}
