package sdk.payexpresse.com;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;


public class PayExpresse {

    static PCallback callback;
    private HashMap<String, Object> params = new HashMap<>();
    private HashMap<String, Object> themeOptions = new HashMap<>();
    private HashMap<String, String> headers = new HashMap<>();
    private Context context;
    private ProgressDialog progress;
    private String requestTokenUrl;
    private String requestMethod = "POST";
    private String loadingDialogText = "Chargement";
    private String tokenUrl;

    public PayExpresse(Context context, String requestTokenUrl, String requestMethod) {
        this.context = context;
        this.requestTokenUrl = requestTokenUrl;
        this.requestMethod = requestMethod;
    }

    public PayExpresse(Context context) {
        this.context = context;
    }

    public PayExpresse(Context context, String requestTokenUrl) {
        this.context = context;
        this.requestTokenUrl = requestTokenUrl;
    }

    /* Returns true if url is valid */
    private static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void send() {
        this.showLoading();

        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    PayExpresse.this.fetchData();
                    Log.i("fetche", PayExpresse.this.tokenUrl);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            PayExpresse.this.openViewActivity();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            PayExpresse.this.dismissLoadingDialog();
                            PayExpresse.callback.onResult(PCallback.Result.ERROR);
                        }
                    });

                }
            }
        };

        new Thread(runnable).start();

    }

    private void fetchData() throws Exception {
        Log.i("url request", this.requestTokenUrl);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = new URL(this.requestTokenUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod(this.requestMethod);
        this.setHeaders(conn);
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);


        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(this.getQuery());
        writer.flush();
        writer.close();
        os.close();

        conn.connect();

        String result = this.readInputStreamToString(conn);
        JSONObject obj = new JSONObject(result);
        Log.i("query & result", result + " " + this.getQuery());


        if (obj.has("redirect_url") && PayExpresse.isValid(obj.getString("redirect_url"))) {
            this.tokenUrl = obj.getString("redirect_url");
        } else {
            throw new Exception("Non expected response: " + result);
        }

    }

    private void openViewActivity() {
        this.dismissLoadingDialog();
        Intent intent = new Intent(this.context, ViewActivity.class);
        intent.putExtra("url", this.tokenUrl);
        this.context.startActivity(intent);
    }

    private void showLoading() {
        Log.i("Loading", "loadinf");
        if (progress == null) {
            progress = new ProgressDialog(this.context);
            String loadingDialogTitle = "PayExpresse";
            progress.setTitle(loadingDialogTitle);
            progress.setMessage(this.loadingDialogText);
        }

        progress.show();
    }

    private void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    private String getQuery() throws UnsupportedEncodingException {
        params.put("is_mobile", "yes");


        params.put("__themeOption__", getThemeQuery());

        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : this.params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
        }

        return result.toString();
    }

    private String getThemeQuery() {
        JSONObject jsonObject = new JSONObject(themeOptions);
        String jsonString = jsonObject.toString();
        return Base64.encodeToString(jsonString.getBytes(), Base64.DEFAULT);
    }

    private void setHeaders(HttpURLConnection connection) {
        for (Map.Entry<String, String> entry : this.headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private String readInputStreamToString(HttpURLConnection connection) {
        String result = null;
        StringBuilder sb = new StringBuilder();
        InputStream is = null;

        try {
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();
        } catch (Exception e) {
            Log.i("error", "Error reading InputStream");
            result = null;
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.i("error", "Error closing InputStream");
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public PayExpresse setParams(HashMap<String, Object> params) {

        if (params == null) {
            throw new IllegalArgumentException("Null value passsed to method");
        }

        this.params = params;

        return this;
    }

    public PayExpresse setHeaders(HashMap<String, String> headers) {
        if (headers == null) {
            throw new IllegalArgumentException("Null value passsed to method");
        }

        this.headers = headers;

        return this;
    }

    public PayExpresse setRequestTokenUrl(String requestTokenUrl) {

        if (!PayExpresse.isValid(requestTokenUrl)) {
            throw new IllegalArgumentException("Invalid URL passsed to method");
        }

        this.requestTokenUrl = requestTokenUrl;

        return this;
    }

    public PayExpresse setRequestMethod(String requestMethod) {

        if (!requestMethod.equalsIgnoreCase("POST") && !requestMethod.equalsIgnoreCase("GET")) {
            throw new IllegalArgumentException("Invalid HTTP REQUEST. Try 'GET' or 'POST'");
        }

        this.requestMethod = requestMethod.toUpperCase();
        return this;
    }

    public PayExpresse setLoadingDialogText(String loadingDialogText) {

        if (loadingDialogText == null) {
            throw new IllegalArgumentException("Null value passsed to method");
        }

        this.loadingDialogText = loadingDialogText;
        return this;
    }

    public PayExpresse setThemeOptions(HashMap<String, Object> themeOptions) {
        if (themeOptions == null) {
            throw new IllegalArgumentException("Null value passsed to method");
        }

        this.themeOptions = themeOptions;

        return this;
    }

    public PayExpresse setCallback(PCallback callback) {

        if (callback == null) {
            throw new IllegalArgumentException("Null value passsed to method");
        }

        PayExpresse.callback = callback;
        return this;
    }
}

