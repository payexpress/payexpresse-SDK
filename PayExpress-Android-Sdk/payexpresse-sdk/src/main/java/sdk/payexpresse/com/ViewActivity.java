package sdk.payexpresse.com;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class ViewActivity extends Activity {

    private String MOBILE_CANCEL_URL = "https://payexpresse.com/mobile/cancel";
    private String MOBILE_SUCCESS_URL = "https://payexpresse.com/mobile/success";
    private WebView webView;
    private WebView childWebView;
    private int lastValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        webView = new WebView(this);

        //webView setting
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setNeedInitialFocus(true);

        webView.loadUrl(getIntent().getStringExtra("url"));


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.i("Overide url", url);

                if (ViewActivity.this.MOBILE_CANCEL_URL.equals(url)) {
                    PayExpresse.callback.onResult(PCallback.Result.CANCEL);
                    ViewActivity.this.finish();

                    return false;
                } else if (ViewActivity.this.MOBILE_SUCCESS_URL.equals(url)) {
                    PayExpresse.callback.onResult(PCallback.Result.SUCCESS);
                    ViewActivity.this.finish();

                    return false;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                webView.removeAllViews();
                childWebView = new WebView(ViewActivity.this);
                WebSettings webSettings = childWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                webSettings.setSupportMultipleWindows(true);
                webSettings.setNeedInitialFocus(true);

                childWebView.setWebViewClient(new WebViewClient());
                // Create dynamically a new view
                childWebView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                childWebView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onCloseWindow(WebView window) {
                        webView.removeAllViews();
                        childWebView = null;
                    }
                });
                webView.addView(childWebView);

                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(childWebView);
                resultMsg.sendToTarget();
                return true;
            }
        });

        setContentView(webView);
        AndroidBug5497Workaround.assistActivity(this);

    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (childWebView != null) {
                webView.removeAllViews();
                childWebView = null;
                return false;
            } else {
                PayExpresse.callback.onResult(PCallback.Result.CANCEL);
                ViewActivity.this.finish();
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    private void callJs(String fn) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(fn, null);
        } else {
            webView.loadUrl("javascript:" + fn);
        }
    }
}


