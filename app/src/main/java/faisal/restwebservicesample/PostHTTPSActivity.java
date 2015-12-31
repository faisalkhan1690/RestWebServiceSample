package faisal.restwebservicesample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * created by faisal khan
 */
public class PostHTTPSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_https);

        Button btnClickToHit = (Button) findViewById(R.id.btn_click_to_hit);
        btnClickToHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostHTTPSAsyncTask(null).execute();
            }
        });
    }

    /**
     * created by faisal khan
     */
    class PostHTTPSAsyncTask extends AsyncTask<Boolean,Integer,String>{


        private String requestJson="";
        ProgressDialog progressDialog;
        //TODO put url
        String strUrl="";


        public PostHTTPSAsyncTask(String requestJson){
            this.requestJson=requestJson;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=new ProgressDialog(PostHTTPSActivity.this);
            progressDialog.setMessage("Please wait.....");
            progressDialog.show();
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
                writer.write(requestJson);

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
