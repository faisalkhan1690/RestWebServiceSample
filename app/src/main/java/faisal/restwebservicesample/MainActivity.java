package faisal.restwebservicesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);

        List list=new ArrayList(4);
        list.add("Post HTTPS");
        list.add("GET HTTPS");
        list.add("Post HTTP");
        list.add("GET HTTP");


        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        startActivity(new Intent(MainActivity.this,PostHTTPSActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,GetHTTPSActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,PostHTTPActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,GetHTTPActivity.class));
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Wrong choice", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
