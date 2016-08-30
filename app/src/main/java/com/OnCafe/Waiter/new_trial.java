package com.OnCafe.Waiter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.OnCafe.ExitDialog;
import com.OnCafe.Management.LoginPage;
import com.OnCafe.R;
import com.OnCafe.SaveIp;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class new_trial extends AppCompatActivity {
    OrderedDBAdapter odbAdapter;
    TableNoFrontDB frontDB;
    OrderTable tableNO;
    TextView frontTableNum;
    SharedPreferences sTableNo,readyonupdate;
    ServerSocket serverSocket;
    SharedPreferences sharedData;
    TextView infoIp;
    ServerSocketThread2 serverSocketThread2;
    int SelectedItemID;
    MediaPlayer mMediaPlayer;
    String fileName;
    static String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/databases/";
    static final int SocketServerPORT = 8181;
    ImageButton history,adminpanel,setting;
    ImageView itemphoto;
    TextView itemname,describe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trial);
        itemphoto=(ImageView)findViewById(R.id.itemphoto);
        itemname=(TextView)findViewById(R.id.itemname);
        describe=(TextView)findViewById(R.id.describe);
        history=(ImageButton)findViewById(R.id.history);
        setting=(ImageButton)findViewById(R.id.setting);
        adminpanel=(ImageButton)findViewById(R.id.adminpanel);

        FloatingActionButton message=(FloatingActionButton)findViewById(R.id.message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyonupdate= getSharedPreferences("Received Table Number", 0);

                final String r= readyonupdate.getString("ready","welcome");
                Snackbar.make(view, r , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(new_trial.this,HistoryRecords.class));
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(new_trial.this,SaveIp.class));

            }
        });
        adminpanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goadmin cdd=new Goadmin(new_trial.this);
                cdd.show();
            }
        });

        int imagesToShow[] = { R.drawable.slide1, R.drawable.slide2,R.drawable.slide3,R.drawable.slide4,R.drawable.slide5};
        String name[] = {"On Cafe","Who we are?","Remember Us","Taste","We Offer"};
        String desp[]={"Digitalizing Your Cafe","Everthing that needs to satisfy you","Whenever your feel free","Feeling of Homemade","Best ever taste only at on cafe"};
        animate(itemphoto, imagesToShow,name,desp,0,true);


        receiveStatus();
        odbAdapter = new OrderedDBAdapter(this);
        ArrayList<String> stringArrayList = new ArrayList<String>();
        String[] temp = stringArrayList.toArray(new String[stringArrayList.size()]);

        //String[] temp = new String[15];
        //  String[] tableNum= {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
        // String[] temp={};
        // temp[0]=tableNum[0];
        frontDB=new TableNoFrontDB(new_trial.this);
        for(int i=1;i<=15;i++)
        {

            if(odbAdapter.frontTableExist(String.valueOf(i))&&!frontDB.CheckIsDataAlreadyInDBorNot(String.valueOf(i)) )//&& !Arrays.asList(temp).contains(String.valueOf(i))) {
            //stringArrayList.add(String.valueOf(i));
            //temp = stringArrayList.toArray(new String[stringArrayList.size()]);
            {
                frontDB.addProduct(String.valueOf(i));
            }


        }

        //

        //  ListAdapter bishalsAdapter = new HomeCustomTableAdapter(this,temp);
        // ListView bishalsListView = (ListView) findViewById(R.id.waiterHomeListView);
        // bishalsListView.setAdapter(bishalsAdapter);
        frontDB=new TableNoFrontDB(new_trial.this);
        printTables();
        frontTableNum=(TextView)findViewById(R.id.frontTableNum);
        frontTableNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortcut();
            }
        });






        Button orders= (Button) findViewById(R.id.orders);



        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(new_trial.this, Tables.class);
                finish();
                startActivity(t);
            }
        });








    }

    private void animate(final ImageView imageView, final int images[],final String name[],final String desp[], final int imageIndex, final boolean forever) {

        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 5000;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.VISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        imageView.setImageResource(images[imageIndex]);
        itemname.setText(name[imageIndex]);
        describe.setText(desp[imageIndex]);


        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.length - 1 > imageIndex) {
                    animate(imageView, images,name,desp, imageIndex + 1,forever); //Calls itself until it gets to the end of the array
                }
                else {
                    if (forever == true){
                        animate(imageView, images,name,desp, 0,forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
    }
    public void printTables(){
        //final String[] finalTemp = temp[0];



        //  String tablesNo =String.valueOf(parent.getItemAtPosition(position));

        //passing tableN to create database with the respective table name


        Cursor cursor = frontDB.getAllRows();
        final String[] fromFieldNames = new String[]{
                TableNoFrontDB.COLUMN_TABLENUM
        };
        int[] toViewIDs = new int[]{

                R.id.homeText
        };
        final SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(new_trial.this, R.layout.home_custom_table_layout, cursor, fromFieldNames, toViewIDs, 0);
        final ListView myList = (ListView) findViewById(R.id.waiterHomeListView);
        myList.setAdapter(myCursorAdapter);
        myList.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.setSelected(true);

                        Cursor cursor = (Cursor) myList.getItemAtPosition(position);
                        final int tableNum = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[0]));
                        tableNO= new OrderTable(String.valueOf(tableNum));
                        odbAdapter= new OrderedDBAdapter(new_trial.this);
                        odbAdapter.setName(tableNO);

                        frontTableNum.setText("Table no " + tableNum + "        >");
                        printDatabase();
                        printDatabase();
                    }
                }
        );
    }

    public void printDatabase() {

        Cursor cursor = odbAdapter.getAllRows();
        final String[] fromFieldNames = new String[]{

                OrderedDBAdapter.COLUMN_PRODUCTNAME,
                OrderedDBAdapter.COLUMN_AMOUNT,
                OrderedDBAdapter.COLUMN_STATUS,


                OrderedDBAdapter.COLUMN_SERVED,
                OrderedDBAdapter.COLUMN_ID};
        int[] toViewIDs = new int[]{

                R.id.frontProductName,
                R.id.frontAmt,
                R.id.frontStatus,


                R.id.frontServeStatus};
        final SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(new_trial.this, R.layout.coupon_layout, cursor, fromFieldNames, toViewIDs, 0);
        final ListView myList = (ListView) findViewById(R.id.customOrdersListView);
        myList.setAdapter(myCursorAdapter);

        odbAdapter= new OrderedDBAdapter(new_trial.this);
        Cursor cur =odbAdapter.get_sum();
        int amount=0;
        if(cur.moveToFirst())
        {
            amount = cur.getInt(0);
        }

        TextView total= (TextView)findViewById(R.id.frontTotal);
        total.setText("" + amount);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) myList.getItemAtPosition(position);
                //SelectedItemID = cursor.getInt(cursor.getColumnIndexOrThrow(fromFieldNames[4]));
                String SelectedName=cursor.getString(cursor.getColumnIndexOrThrow(fromFieldNames[0]));
                odbAdapter=new OrderedDBAdapter(new_trial.this);
                odbAdapter.setName(tableNO);
                odbAdapter.updateServeStatus(String.valueOf(position+1));
                Toast.makeText(new_trial.this, SelectedName + " served", Toast.LENGTH_LONG).show();
                printDatabase();

            }
        });



    }




    @Override
    public void onBackPressed() {

        ExitDialog e= new ExitDialog(new_trial.this);
        e.show();

    }
    public void shortcut(){
        if (!frontTableNum.getText().toString().equals("Table Num")) {
            sTableNo= getSharedPreferences("Table Number",0);

            SharedPreferences.Editor editor=sTableNo.edit();
            editor.putString("hello", tableNO.get_tableNo());
            editor.apply();

            odbAdapter = new OrderedDBAdapter(new_trial.this);
            odbAdapter.setName(tableNO);


            Intent n = new Intent(new_trial.this, orderTabs.class);
            finish();
            startActivity(n);
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

    public void receiveStatus() {
        serverSocketThread2 = new ServerSocketThread2();
        serverSocketThread2.start();

    }
    //Listening Server Thread
    public class ServerSocketThread2 extends Thread {

        @Override
        public void run() {
            Socket socket = null;

            try {
                serverSocket = new ServerSocket(SocketServerPORT);

                while (true) {
                    socket = serverSocket.accept();
                    new_trial.FileTxThread2 fileTxThread = new FileTxThread2(socket);
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

    }
    public class FileTxThread2 extends Thread {
        Socket socket;



        FileTxThread2(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {


            try{


                DataInputStream d = new DataInputStream(socket.getInputStream());
                fileName = d.readUTF();

                readyonupdate= getSharedPreferences("Received Table Number",0);
                SharedPreferences.Editor editor=readyonupdate.edit();

                editor.putString("ready", "Recent Update on:  Table " + fileName);
                editor.apply();




                File file = new File(DB_PATH, "Table"+fileName+".db");

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                byte[] bytes;
                FileOutputStream fos = null;
                try {
                    bytes = (byte[])ois.readObject();
                    fos = new FileOutputStream(file);
                    fos.write(bytes);

                } catch (ClassNotFoundException e) {

                    e.printStackTrace();
                } finally {
                    if(fos!=null){
                        fos.close();

                    }

                }

                //socket.close();



            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
            catch (IOException e) {

                e.printStackTrace();
            } finally {
                try {

                    socket.close();

                    playAlertTone();



                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

        }
    }
   public void playAlertTone() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.notification);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

/*
    public class ClientRxThread extends Thread {
        String dstAddress;
        String fileName;
        Socket socket;

        int dstPort;



        ClientRxThread(String address, int port) {
            dstAddress = address;
            dstPort = port;


        }

        @Override
        public void run() {


            try{

                socket=new Socket(dstAddress,dstPort);
                DataInputStream d = new DataInputStream(socket.getInputStream());
                fileName = d.readUTF();



                File file = new File(DB_PATH,fileName);

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                byte[] bytes;
                FileOutputStream fos = null;
                try {
                    bytes = (byte[])ois.readObject();
                    fos = new FileOutputStream(file);
                    fos.write(bytes);

                } catch (ClassNotFoundException e) {

                    e.printStackTrace();
                } finally {
                    if(fos!=null){
                        fos.close();

                    }

                }

                //socket.close();



            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
            catch (IOException e) {

                e.printStackTrace();
            } finally {
                try {

                    socket.close();
                    frontDB=new TableNoFrontDB(WaiterHome.this);
                    if(!frontDB.CheckIsDataAlreadyInDBorNot(fileName)) {
                        frontDB.addProduct(fileName);
                    }

                    WaiterHome.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            sTableNo= getSharedPreferences("Received Table Number",0);

                            SharedPreferences.Editor editor=sTableNo.edit();
                            editor.putString("hello","Recent Update on:  Table "+fileName);
                            editor.apply();
                            Toast.makeText(WaiterHome.this,
                                    "Table no"+fileName,
                                    Toast.LENGTH_LONG).show();
                        }
                    });



                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

        }
    }

*/



    //file thread
}
