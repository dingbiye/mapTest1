package com.sincodest.maptest.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.sincodest.maptest.Jiekou.DownloadListener;
import com.sincodest.maptest.MainActivity;
import com.sincodest.maptest.R;
import com.sincodest.maptest.util.DownloadTask;

import java.io.File;

public class MyDownloadService extends Service {
    private DownloadTask downloadTask;
    public MyDownloadService() {
    }
    private String downloadUrl;
    private DownloadBinder mBinder = new DownloadBinder();
    public class DownloadBinder extends Binder {
        public void startDownload(String url){
            if(downloadTask == null){
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                downloadTask.execute(downloadUrl);
                startForeground(1, getNotification("Downloading...", 0));
                Toast.makeText(MyDownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload(){
            if(downloadTask != null){

                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload(){
            if(downloadTask != null){
                downloadTask.cancelDownload();
            }else{
                if(downloadUrl != null){
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + fileName;
                    File file = new File(directory + fileName);
                    if(file.exists()){
                        file.delete();
                    }

                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(MyDownloadService.this, "cancel", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private NotificationManager getNotificationManager(){
        NotificationManager notiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        return notiManager;
    }
    private Notification getNotification(String title, int progress){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        if(progress > 0){
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("download success", -1));
            Toast.makeText(MyDownloadService.this, "download success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("download failed", -1));
            Toast.makeText(MyDownloadService.this, "download failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(MyDownloadService.this, "download pause", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(MyDownloadService.this, "download canceled", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
