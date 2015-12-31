package faisal.restwebservicesample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class GetHTTPSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_https);
        Button btnClickToHit = (Button) findViewById(R.id.btn_click_to_hit);
        btnClickToHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetHTTPSAsyncTask().execute();
            }
        });
    }

    /**
     * created by faisal khan
     */
    class GetHTTPSAsyncTask extends AsyncTask<Boolean,Integer,String> {


        ProgressDialog progressDialog;
        String strUrl="http://androidexample.com/media/webservice/JsonReturn.php";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=new ProgressDialog(GetHTTPSActivity.this);
            progressDialog.setMessage("Please wait.....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Boolean... params) {

            //done both https and http run
            String response = "";
            try {
                URL url = new URL(strUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoOutput(false);

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressDialog.setProgress(values[0].intValue());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(GetHTTPSActivity.this, s + "", Toast.LENGTH_SHORT).show();
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