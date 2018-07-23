package com.android.common.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.common.BuildConfig;
import com.android.common.R;
import com.android.common.constant.PermissionConstants;
import com.android.common.util.LogUtils;
import com.android.common.util.MaterialDialogUtils;
import com.android.common.util.PermissionUtils;
import com.android.common.util.ScreenUtils;
import com.android.common.util.StringUtils;
import com.android.common.util.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BaseWebViewActivity extends BaseActivity{

    private LinearLayout webViewLayout;
    protected WebView mWebView;
    protected ProgressBar progressBar;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int VIDEO_REQUEST = 120; //录制视频
    public static final int TYPE_CAMERA = 1;      //拍照
    public static final int TYPE_GALLERY = 2;     //相册选择

    public File picFile = null;
    public Uri u;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    int progress = (Integer) msg.obj;
                    progressBar.setProgress(progress);
                    break;
            }
        }
    };

    @Override
    public int getContentView() {
        return R.layout.webview_layout;
    }

    @Override
    public void initView() {
        webViewLayout = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progressBar);
        mWebView = new WebView(this);
        webViewLayout.addView(mWebView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mWebView != null){
            mWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.clearCache(true); //清空缓存
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                if (webViewLayout != null) {
                    webViewLayout.removeView(mWebView);
                }
                mWebView.removeAllViews();
                mWebView.destroy();
            }else {
                mWebView.removeAllViews();
                mWebView.destroy();
                if (webViewLayout != null) {
                    webViewLayout.removeView(mWebView);
                }
            }
            mWebView = null;
        }
    }

    @Override
    public void initData() {
        initWebViewSetting();
    }


    //初始化webViewSetting
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initWebViewSetting() {
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        settings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        settings.setAllowFileAccessFromFileURLs(false);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        settings.setAllowUniversalAccessFromFileURLs(false);
        //开启JavaScript支持
        settings.setJavaScriptEnabled(true);
        // 支持缩放
        settings.setSupportZoom(true);

        //辅助WebView处理图片上传操作
        mWebView.setWebChromeClient(new MyChromeWebClient());
    }



    //自定义 WebChromeClient 辅助WebView处理图片上传操作【<input type=file> 文件上传标签】
    public class MyChromeWebClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Message message = Message.obtain();
            message.what = 200;
            message.obj = newProgress;
            handler.sendMessage(message);
        }

        // 配置权限（同样在WebChromeClient中实现）
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }


        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            mUploadMessage = uploadMsg;
            showOptions();
        }

        // For Android > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            showOptions();
        }

        // For Android > 5.0支持多张上传
        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> uploadMsg,
                                         FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = uploadMsg;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String[] strs = fileChooserParams.getAcceptTypes();
                if (strs[0].contains("image/*")) {
                    showOptions();
                } else if (strs[0].contains("video/*")) {
                    recordVideo();
                }
            } else {

            }
            return true;
        }
    }

    /**
     * 录像
     */
    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        //限制时长
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
        //开启摄像机
        startActivityForResult(intent, VIDEO_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == TYPE_CAMERA) { // 拍照
                Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                if (mUploadCallbackAboveL != null) {
                    onActivityResultAboveL(requestCode, resultCode, data);
                } else if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(result == null ? (u == null ? null : u) : result);
                    mUploadMessage = null;
                }
            } else if (requestCode == TYPE_GALLERY) {// 相册选择
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        if (mUploadCallbackAboveL != null) {
                            Uri[] uris = new Uri[]{uri};
                            mUploadCallbackAboveL.onReceiveValue(uris);
                            mUploadCallbackAboveL = null;
                        } else if (mUploadMessage != null) {
                            mUploadMessage.onReceiveValue(uri);
                            mUploadMessage = null;
                        } else {
                            ToastUtils.showShort("无法获取数据");
                        }
                    } else {
                        ToastUtils.showShort("获取数据为空");
                    }
                }
            } else if (requestCode == VIDEO_REQUEST) {//录视频
                if (null == mUploadMessage && null == mUploadCallbackAboveL) return;

                Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                if (mUploadCallbackAboveL != null) {
                    if (resultCode == RESULT_OK) {
                        mUploadCallbackAboveL.onReceiveValue(new Uri[]{result});
                        mUploadCallbackAboveL = null;
                    } else {
                        mUploadCallbackAboveL.onReceiveValue(new Uri[]{});
                        mUploadCallbackAboveL = null;
                    }

                } else if (mUploadMessage != null) {
                    if (resultCode == RESULT_OK) {
                        mUploadMessage.onReceiveValue(result);
                        mUploadMessage = null;
                    } else {
                        mUploadMessage.onReceiveValue(Uri.EMPTY);
                        mUploadMessage = null;
                    }

                }
            }
        } else {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
                mUploadCallbackAboveL = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != TYPE_CAMERA)
            return;
        Uri[] results = null;
        Uri imgUri = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
                else {
                    results = new Uri[]{u};
                }
            } else {
                try {
                    imgUri = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), picFile.getAbsolutePath(), null, null));
                    results = new Uri[]{imgUri};
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
    }

    /**
     * 包含拍照和相册选择
     */
    public void showOptions() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                    mUploadMessage = null;
                }
                if (mUploadCallbackAboveL != null) {
                    mUploadCallbackAboveL.onReceiveValue(null);
                    mUploadCallbackAboveL = null;
                }
            }
        });
        alertDialog.setTitle("选择");
        alertDialog.setItems(R.array.options,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //拍照
                            toCamera();
                        } else {
                            //相册选择
                            Intent i = new Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
                            startActivityForResult(i, TYPE_GALLERY);
                        }
                    }
                });
        alertDialog.show();
    }


    // 请求拍照
    public void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android的相机

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "bankimg");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 获取当前时间，进一步转化为字符串
        date = new Date();
        String picFileName  = format.format(date) + ".jpg";
        picFile = new File(dir, picFileName);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            u = Uri.fromFile(picFile);
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            u = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID+".provider", picFile);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
        startActivityForResult(intent, TYPE_CAMERA);
    }

}
