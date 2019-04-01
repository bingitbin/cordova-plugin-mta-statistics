package org.apache.cordova.mtastatistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

/**
 * This class echoes a string called from JavaScript.
 */
public class MTAStatisticsPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        command(action, args, callbackContext);
        return true;
    }

    @Override
    public void pluginInitialize() {
        // [可选]设置是否打开debug输出，上线时请关闭，Logcat标签为"MtaSDK"
        StatConfig.setDebugEnable(true);
        // 基础统计API
        //StatService.registerActivityLifecycleCallbacks(this.getApplication());
    }


    private void command(String action, JSONArray args, CallbackContext callbackContext) {
        if (TextUtils.isEmpty(action)) {
            callbackContext.error("action invalid, error");
        }

        if ((args == null) || (args.length() == 0)) {
            callbackContext.error("args invalid, error");
        }

        if ("onEvent".equals(action)) {
            String eventId = "";
            String label = "";
            try {
                eventId = args.getString(0);
                label = args.getString(1);
            } catch (Exception e) {
                // TODO: handle exception
            }

            if (TextUtils.isEmpty(eventId)) {
                callbackContext.error("eventId invalid, error");
                return;
            }

            StatService.trackCustomEvent(cordova.getActivity(), eventId, label);
        } else if ("onPageStart".equals(action)) {
            String pageName = "";
            try {
                pageName = args.getString(0);
            } catch (Exception e) {
                // TODO: handle exception
            }

            if (TextUtils.isEmpty(pageName)) {
                callbackContext.error("pageName invalid, error");
                return;
            }

            StatService.trackBeginPage(cordova.getActivity(), pageName);
        } else if ("onPageEnd".equals(action)) {
            String pageName = "";
            try {
                pageName = args.getString(0);
            } catch (Exception e) {
                // TODO: handle exception
            }

            if (TextUtils.isEmpty(pageName)) {
                callbackContext.error("pageName invalid, error");
                return;
            }

            StatService.trackEndPage(cordova.getActivity(), pageName);
        }

    }

    // 将相关json进行转换为Map<String, String>, 如果类型不匹配等，则转换失败
    private HashMap<String, String> getConvertedMap(JSONObject jsonObject) {
        HashMap<String, String> hashMap = null;
        if (jsonObject == null) {
            return hashMap;
        }

        if (jsonObject.length() != 0) {
            hashMap = new HashMap<String, String>();
        }

        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            try {
                String key = iterator.next();
                String value = jsonObject.getString(key);
                hashMap.put(key, value);
            } catch (Exception e) {
                // TODO Auto-generated catch block
            }
        }

        return hashMap;
    }
}
