package com.OnCafe.Waiter;

/**
 * Created by Bishal on 8/2/2015.
 */

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import com.OnCafe.R;


public class ServerFileTransfer extends ActionBarActivity {
Context context;
    TextView fileTransferMsg;
    String tableNo;
    String IPAddress;
    SharedPreferences sharedData;
    OrderedDBAdapter odb;

    static String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/databases/";
    public ServerFileTransfer() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_transfer);
        fileTransferMsg= (TextView)findViewById(R.id.fileTransferMsg);
        sharedData= getSharedPreferences("Table Number", 0);
        tableNo= sharedData.getString("hello", "admin");

        String fileName = "Table" + tableNo + ".db";
        sharedData= getSharedPreferences("Ip Address", 0);
        IPAddress= sharedData.getString("Ip", "admin");
        odb= new OrderedDBAdapter(ServerFileTransfer.this);
        if(odb.frontTableExist(tableNo)) {
            ClientRxThread clientRxThread =
                    new ClientRxThread(IPAddress, 8080, fileName);
            clientRxThread.start();
        }else{
            fileTransferMsg.setText("Sorry, There is nothing on the list.\n Please take order and try again");
            fileTransferMsg.setTextColor(Color.RED);
        }

    }


    public class ClientRxThread extends Thread {
        String dstAddress;
        String fileName;


        int dstPort;



        ClientRxThread(String address, int port,String fileName) {
            dstAddress = address;
            dstPort = port;
            this.fileName=fileName;

        }

        @Override
        public void run() {
            Socket socket = null;




            try {

                socket = new Socket(dstAddress, dstPort);
                DataOutputStream d = new DataOutputStream(socket.getOutputStream());
                d.writeUTF(tableNo);



            File file = new File(DB_PATH, fileName);


            byte[] bytes = new byte[(int) file.length()];
            BufferedInputStream bis;

                bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(bytes, 0, bytes.length);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(bytes);
                oos.flush();

                socket.close();


                ServerFileTransfer.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        fileTransferMsg.setText("Orders Successfully sent");
                        fileTransferMsg.setTextColor(Color.BLUE);
                        Toast.makeText(ServerFileTransfer.this,
                                "Sent Success",
                                Toast.LENGTH_LONG).show();
                    }
                });


            } catch (IOException e) {

                e.printStackTrace();

                final String eMsg = "Something wrong: " + e.getMessage();


                ServerFileTransfer.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        fileTransferMsg.setText(eMsg);
                        fileTransferMsg.setTextColor(Color.RED);
                       /* Toast.makeText(ServerFileTransfer.this,
                                eMsg,
                                Toast.LENGTH_LONG).show();*/
                    }
                });

            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ServerFileTransfer.this,orderTabs.class);
        finish();
        startActivity(i);
    }
}












/*
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
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
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {

            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }*/

    /*
//ServerSocket Thread to accept the connection
    public class ServerSocketThread extends Thread {

        @Override
        public void run() {
            Socket socket = null;

            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                ServerFileTransfer.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        infoPort.setText("I'm waiting here: "
                                + serverSocket.getLocalPort());
                    }});

                while (true) {
                    socket = serverSocket.accept();
                    FileTxThread fileTxThread = new FileTxThread(socket);
                    fileTxThread.start();
                }
            } catch (IOException e) {

                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        }

    }*/
/*
    public class FileTxThread extends Thread {
        Socket socket;

        FileTxThread(Socket socket){
            this.socket= socket;
        }

        @Override
        public void run() {

            File file = new File(DB_PATH,DB_NAME);


            byte[] bytes = new byte[(int) file.length()];
            BufferedInputStream bis;
            try {
                bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(bytes, 0, bytes.length);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(bytes);
                oos.flush();

                socket.close();

                final String sentMsg = "File sent to: " + socket.getInetAddress();
                ServerFileTransfer.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(ServerFileTransfer.this,
                                sentMsg,
                                Toast.LENGTH_LONG).show();
                    }});

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }
}*/