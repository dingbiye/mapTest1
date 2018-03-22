package com.sincodest.maptest;

import android.Manifest;
import android.app.AlarmManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sincodest.maptest.Jiekou.DownloadListener;
import com.sincodest.maptest.db.Book;
import com.sincodest.maptest.service.MyDownloadService;
import com.sincodest.maptest.util.DownloadTask;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.security.Permission;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyTestService.MyBinder myBinder;
    //    private OnResultOkListener listener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call();
                }else{
                    Toast.makeText(getApplicationContext(), "没权限", Toast.LENGTH_SHORT).show();
                }

                break;
            case 2:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    web();
                }else{
                    Toast.makeText(getApplicationContext(), "没权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    download();
                }else{
                    Toast.makeText(getApplicationContext(), "没权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;

    }
    private  TextView tv_cbr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        Toolbar tb_test = findViewById(R.id.tb_test);
        setSupportActionBar(tb_test);

        Intent intent = new Intent(this, MyDownloadService.class);
        startService(intent);

        bindService(intent, connection, BIND_AUTO_CREATE);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }

        final Button btn_drawer = findViewById(R.id.btn_drawer);
        btn_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDrawer();
            }
        });
        final Button btn_downloadPause = findViewById(R.id.btn_downloadPause);
        btn_downloadPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
        final Button btn_downloadCancel = findViewById(R.id.btn_downloadCancel);
        btn_downloadCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        Button btn_downloadTest = findViewById(R.id.btn_downloadTest);
        btn_downloadTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 3);

                }else{
                    download();

                }
            }
        });
        Button btn_web = findViewById(R.id.btn_web);
        btn_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 2);

                }else{
                    web();

                }
            }
        });

        Button btn_call = findViewById(R.id.btn_call);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                }else{
                    call();

                }
            }
        });



        Button btn_litePalquery = findViewById(R.id.btn_litePalquery);
        btn_litePalquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> all = DataSupport.findAll(Book.class);
                Toast.makeText(getApplicationContext(), all.size() + "啊", Toast.LENGTH_SHORT).show();

            }
        });
        Button btn_litePaldel = findViewById(R.id.btn_litePaldel);
        btn_litePaldel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class, "name = ?", "浮冰");

            }
        });
         tv_cbr = findViewById(R.id.tv_cbr);
        //        btn_litePalADD
        Button btn_litePalUpdate = findViewById(R.id.btn_litePalUpdate);
        btn_litePalUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setPrice(999);
                book.updateAll("name = ? ", "浮冰");

            }
        });
        Button btn_litePalADD = findViewById(R.id.btn_litePalADD);
        btn_litePalADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                startActivity(new Intent(getApplicationContext(), DispatchActivity.class));
                Book book = new Book();
                book.setAuthor("浮冰");
                book.setPages(323);
                book.setPrice(32);
                book.save();

            }
        });
        Button btn_litePal = findViewById(R.id.btn_litePal);
        btn_litePal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                startActivity(new Intent(getApplicationContext(), DispatchActivity.class));
                LitePal.getDatabase();

            }
        });

        Button btn_dispatch = findViewById(R.id.btn_dispatch);
        btn_dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DispatchActivity.class));
            }
        });

        Button btn_new = findViewById(R.id.btn_new);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestDouble.class);
                startActivity(intent);
            }
        });
        Button btn_goon_tell = findViewById(R.id.btn_goon_tell);
        btn_goon_tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBinder.startGoOn();
            }
        });

        Button btn_stop_tell = findViewById(R.id.btn_stop_tell);
        btn_stop_tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBinder.stopTell();
            }
        });

        Button btn_tell = findViewById(R.id.btn_tell);
        btn_tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册，传递函数地址


                myBinder.setOnResultOkListener(new OnResultOkListener() {
                    @Override
                    public void show(int i) {
                        tv_cbr.setText(i + "");
                    }
                });
                myBinder.tell(3);
            }
        });


        Button btn_bindService = findViewById(R.id.btn_bindService);
        btn_bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), MyTestService.class);
                bindService(intent2, connection, BIND_AUTO_CREATE);
            }
        });
        Button btn_startService = findViewById(R.id.btn_startService);
        btn_startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyTestService.class);
                startService(intent);
                //                Toast.makeText(getApplicationContext(), "lick", Toast.LENGTH_SHORT).show();
            }
        });
        Button btn_unbindService = findViewById(R.id.btn_unbindService);
        btn_unbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getApplicationContext(), MyTestService.class);
                unbindService(connection);
                //                Toast.makeText(getApplicationContext(), "lick", Toast.btn_stopService).show();
            }
        });
        Button btn_stopService = findViewById(R.id.btn_stopService);
        btn_stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MyTestService.class);
                stopService(intent1);
                //                Toast.makeText(getApplicationContext(), "lick", Toast.LENGTH_SHORT).show();
            }
        });
        //        initUI();
        
        initAlarm();
    }
private static int alarm = 0;
    private void initAlarm() {

        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, 10000 + SystemClock.elapsedRealtime(), "AlarmTest", new AlarmManager.OnAlarmListener() {
            @Override
            public void onAlarm() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        tv_cbr.setText(alarm++ + "");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_cbr.setText(alarm++ + "");
                            }
                        });
                    }
                }).start();
                initAlarm();
            }
        }, null);


    }
    private Handler mHandler = new Handler(){};
    private void showDrawer() {
        Intent intent = new Intent(this, DrawerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    private void cancel() {
        //
        mDownloadTask.cancelDownload();
    }

    private void pause() {
        mDownloadTask.pauseDownload();
        //
    }
    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {

        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onCanceled() {

        }
    };

    private DownloadTask mDownloadTask;
    private void download() {

                String url = "http://imtt.dd.qq.com/16891/84999CA6B35C9473C19CC6B810083E18.apk?fsname=com.xiaoyu.rightone_3.19.0.295_295.apk&csr=1bbd";
                downloadBinder.startDownload(url);


//        Intent intentDowndoad = new Intent(this, MyDownloadService.class);
////        startService(intentDowndoad);
//        if(mDownloadTask == null){
//            mDownloadTask = new DownloadTask(listener);
//            mDownloadTask.execute("https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk"/*zhifubao*/);
//
////            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
////            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
////            builder.setSmallIcon(R.mipmap.ic_launcher);
////            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
////            builder.setContentTitle("下载中");
//////            builder.build();
////            Notification notification = builder.build();
////            notificationManager.notify(0, notification);
//        }

    }

    private void web() {
        Intent intentWeb = new Intent(this, WebActivity.class);
        startActivity(intentWeb);
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            call();
            return;
        }
        startActivity(intent);
    }

    private MyDownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            myBinder = (MyTestService.MyBinder) service;
            downloadBinder = (MyDownloadService.DownloadBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_startService:
//                Log.i("service", "dfdfdsd");
//                Intent intent = new Intent(getApplicationContext(), MyTestService.class);
//                startService(intent);
//                break;
//            case R.id.btn_stopService:
//                Intent intent1 = new Intent(getApplicationContext(), MyTestService.class);
//                stopService(intent1);
//                break;
//            case R.id.btn_bindService:
//                Intent intent2 = new Intent(getApplicationContext(), MyTestService.class);
//               bindService(intent2, connection, BIND_AUTO_CREATE);
//                break;
//            case R.id.btn_unbindService:
//                Intent intent4 = new Intent(getApplicationContext(), MyTestService.class);
//                unbindService(connection);
//                break;
//        }
//    }
}
