package faisal.restwebservicesample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class PostHTTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_http);
        Button btnClickToHit = (Button) findViewById(R.id.btn_click_to_hit);
        btnClickToHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostHTTPAsyncTask().execute();
            }
        });
    }

    /**
     * created by faisal khan
     */
    class PostHTTPAsyncTask extends AsyncTask<Boolean,Integer,String> {


        ProgressDialog progressDialog;
        String strUrl="http://www.mymi5.net/API/auth/login";
        JSONObject jsonObj;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=new ProgressDialog(PostHTTPActivity.this);
            progressDialog.setMessage("Please wait.....");
            progressDialog.show();
            jsonObj=new JSONObject();
            try {
                jsonObj.put("username", "abcd");
                jsonObj.put("password","1234");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Boolean... params) {


            try{
                URL url = new URL(this.strUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonObj.toString());

                writer.flush();
                writer.close();
                os.close();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String response = "";
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }

                    return response;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressDialog.setProgress(values[0].intValue());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(PostHTTPActivity.this, s + "", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

        /**
         * this method is not used by developer
         */
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        /**
         * this method is not used by developer
         */
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}