package com.example.boat_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText Boat_name;
    private Button showbtn;
    private TextView textView;
    private TextView boatnotregistered;
    String boatMasterSearch = "http://103.127.31.139:7080/test/BoatMasterSearch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textView =findViewById(R.id.textView);
        Button showbtn = (Button) findViewById(R.id.showbtn);
        TextView boatnotregistered = (TextView) findViewById(R.id.boatnotregistered);
        Spinner myspinner = (Spinner) findViewById(R.id.spinner);

        boatnotregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivity();
            }
        });

        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Postdata();
            }
        });

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        myspinner.setAdapter(myAdapter);

    }

    private void Postdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url="http://103.127.31.139:7080/test/BoatMasterSearch";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    //textView.setText("Post Data : " + response);
                }catch (NullPointerException nullPointerException){
                    nullPointerException.printStackTrace();
                }

            }}, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                   // textView.setText("Post Data : Response Failed ");

                }
            })
            {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String,String>();
                     params.put("company_id","C00001");
                    params.put("branch_id","B00037");
                    params.put("fin_year","2010");
                    params.put("boat_id","");
                    params.put("boat_name","");
                    params.put("status","");
                    return params;
                }
            };
        requestQueue.add(stringRequest);
    }
        public void openActivity() {
        Intent intent = new Intent(this,BoatAddScreen.class);
        startActivity(intent);
    }

}