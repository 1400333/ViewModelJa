package com.example.viewmodelja.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.viewmodelja.util.HttpUtil;

public class RequestManager {
    private static final int MESSAGE_DATA_RECOVERY = 0;
    public void postData(RequestData requestData, RequestCallback callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ResponseData trunkResponseData = HttpUtil.postData(requestData);

                Message msg = new Message();

                msg.what = MESSAGE_DATA_RECOVERY;
                msg.obj = new ResponseInfo(trunkResponseData, callback);

                m_handler.sendMessage(msg);
            }
        }).start();
    }

    private final Handler m_handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MESSAGE_DATA_RECOVERY) {
                ResponseInfo responseInfo = (ResponseInfo) msg.obj;

                if (responseInfo != null) {
                    responseInfo.onDataRecovery();
                }
            }
        }
    };

    public class ResponseInfo {
        ResponseData m_responseData = null;
        RequestCallback m_callback = null;

        ResponseInfo(ResponseData responseData, RequestCallback callback) {
            m_responseData = responseData;
            m_callback = callback;
        }

        void onDataRecovery() {
            if (m_callback != null) {
                m_callback.onDataRecovery(m_responseData);
            }
        }
    }

    public interface RequestCallback {
        void onDataRecovery(ResponseData responseData);
    }
}
