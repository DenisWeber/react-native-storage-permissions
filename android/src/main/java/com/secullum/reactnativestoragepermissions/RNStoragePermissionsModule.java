package com.secullum.reactnativestoragepermissions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNStoragePermissionsModule extends ReactContextBaseJavaModule implements ActivityEventListener {
  private final ReactApplicationContext reactContext;

  private static final int STORAGE_PERMISSION_CODE = 1;
  private Promise m_StoragePermissionPromise;

  public RNStoragePermissionsModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.reactContext.addActivityEventListener(this);
  }

  @Override
  public String getName() {
    return "RNStoragePermissions";
  }

  @ReactMethod
    public void checkStoragePermissionsAsync(Promise promise) {
        m_StoragePermissionPromise = promise;
      
        try {
            if (Build.VERSION.SDK_INT < 30) {
                m_StoragePermissionPromise.reject(new Error("SDK version less than 30"));
                return;
            }

            boolean managePermissionResult = Environment.isExternalStorageManager();
            m_StoragePermissionPromise.resolve(managePermissionResult);
        } catch (Exception e) {
            m_StoragePermissionPromise.reject(e);
        }
    }

    @ReactMethod
    public void requestStoragePermissionsAsync(Promise promise) {
        m_StoragePermissionPromise = promise;

        try {
            if (Build.VERSION.SDK_INT < 30) {
                m_StoragePermissionPromise.reject(new Error("SDK version less than 30"));
                return;
            }

            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setData(Uri.parse("package:" + this.reactContext.getPackageName()));

            getCurrentActivity().startActivityForResult(intent, STORAGE_PERMISSION_CODE);
        } catch (Exception e) {
            m_StoragePermissionPromise.reject(e);
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        try {
          if (requestCode == STORAGE_PERMISSION_CODE) {
              if (Build.VERSION.SDK_INT < 30) {
                  m_StoragePermissionPromise.reject(new Error("SDK version less than 30"));
                  return;
              }

              boolean hasStoragePermissions = Environment.isExternalStorageManager();
              m_StoragePermissionPromise.resolve(hasStoragePermissions);
          }
        } catch (Exception e) {
              m_StoragePermissionPromise.reject(e);
        }
    }

    @Override
    public void onNewIntent(Intent intent) { }
}
