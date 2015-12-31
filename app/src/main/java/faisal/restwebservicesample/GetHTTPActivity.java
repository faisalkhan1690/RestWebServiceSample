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

public class GetHTTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_http);
        Button btnClickToHit = (Button) findViewById(R.id.btn_click_to_hit);
        btnClickToHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetHTTPAsyncTask().execute();
            }
        });
    }

    /**
     * created by faisal khan
     */
    class GetHTTPAsyncTask extends AsyncTask<Boolean,Integer,String> {


        ProgressDialog progressDialog;
        String strUrl="http://androidexample.com/media/webservice/JsonReturn.php";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=new ProgressDialog(GetHTTPActivity.this);
            progressDialog.setMessage("Please wait.....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Boolean... params) {

            //done both https and http run
            BufferedReader reader=null;
            try{
                URL url = new URL(strUrl);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
//                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//                wr.write("");
//                wr.flush();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    sb.append(line + "");
                }
                String result = sb.toString();
                return result;
            }catch(Exception ex){
                ex.printStackTrace();
            }
            finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            Toast.makeText(GetHTTPActivity.this, s+"", Toast.LENGTH_SHORT).show();
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