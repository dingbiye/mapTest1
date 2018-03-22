package com.sincodest.maptest.Jiekou;

/**
 * Created by Administrator on 2018/3/20.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
