package com.example.boat_master;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BoatAddScreen extends AppCompatActivity {

    private EditText officialnumber,surveydate,boatid;
    private EditText Boat_name;
    private TextView selectboat_type;
    private Button mbtton;
    int year;
    DatePickerDialog.OnDateSetListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_add_screen);

        Spinner myspinner = (Spinner) findViewById(R.id.spinner);
        mbtton = (Button)findViewById(R.id.Addbtn);
        surveydate=(EditText)findViewById(R.id.survey_date);
        officialnumber = (EditText)findViewById(R.id.Official_number);
        Boat_name = (EditText)findViewById(R.id.boat_name);
        Calendar calendar =Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);


        surveydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog= new DatePickerDialog(BoatAddScreen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        surveydate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        mbtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostAddData();

                Toast.makeText(getApplicationContext(),
                        "Successfully added", Toast.LENGTH_LONG).show();

            }
        });

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(BoatAddScreen.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        myspinner.setAdapter(myAdapter);



    }

    private void PostAddData() {
        RequestQueue requestQueue = Volley.newRequestQueue(BoatAddScreen.this);
        String url="http://103.127.31.139:7080/test/Boatmaster";

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                }catch (NullPointerException nullPointerException){
                    nullPointerException.printStackTrace();
                }

            }}, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){


            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    }


