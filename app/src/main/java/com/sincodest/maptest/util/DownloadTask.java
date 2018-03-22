package com.sincodest.maptest.util;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.IInterface;

import com.sincodest.maptest.Jiekou.DownloadListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String, Integer, Integer>{
    private static final int TYPE_FAILED = 0;
    private static final int TYPE_SUCCESS = 1;
    private static final int TYPE_PAUSED = 2;
    private static final int TYPE_CANCELED = 3;

    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    //这是一个下载任务，在后台运行，回调下载状态的接口

    private DownloadListener listener ;

    public DownloadTask(DownloadListener listener){
        this.listener = listener;

    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param strings The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Integer doInBackground(String... strings) {

        InputStream inputStream = null;
        RandomAccessFile saveFile = null;
        File file = null;

        try{
            long downloadedLength = 0;//记录已下载的文件长度
            String downloadUrl = strings[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + fileName);
            if(file.exists()){
                downloadedLength = file.length();
            }
            long contentLength = getContentLength(downloadUrl);
            if(contentLength == 0){
                return TYPE_FAILED;
            }else if(contentLength == downloadedLength){
                return TYPE_SUCCESS;
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //断点续传,指定从哪个字节开始下载
                    .addHeader("RANGE", "byte=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if(response != null){
                inputStream = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while((len = inputStream.read(b)) != -1){
                    if(isCanceled){
                        return TYPE_CANCELED;
                    }else if(isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        saveFile.write(b, 0, len);
                        //计算已下载的百分比
                        int progress = (int)((total + downloadedLength) * 100 / contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (inputStream != null){
                    inputStream.close();
                }
                if(saveFile != null){
                    saveFile.close();
                }
                if(isCanceled && file != null){
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if(progress > lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            default:
                break;

        }
    }

    public void pauseDownload(){
        isPaused = true;
    }
    public void cancelDownload(){
        isCanceled = true;
    }
    /**
    * 获得待下载文件的总长度
    * @author DingBy
    * created at 2018/3/20 14:57
    */
    private long getContentLength(String downloadUrl) throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = client.newCall(request).execute();
        if(response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            return contentLength;
        }
        return 0;
    }
}