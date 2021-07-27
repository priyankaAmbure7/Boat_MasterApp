package com.example.boat_master;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BoatAddScreen extends AppCompatActivity {

    private EditText officialnumber, surveydate, boatid;
    private EditText Boat_name;
    private TextView selectboat_type;

    private Button Add_btn;
    String bname, btype, bofficialnum, bdateofsurvey, bid, boatType;
    int year;
    DatePickerDialog.OnDateSetListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_add_screen);


        Add_btn = (Button) findViewById(R.id.Addbtn);
        Spinner myspinner = (Spinner) findViewById(R.id.spinner);
        surveydate = (EditText) findViewById(R.id.survey_date);
        officialnumber = (EditText) findViewById(R.id.Official_number);
        Boat_name = (EditText) findViewById(R.id.boat_name);
        boatid = (EditText) findViewById(R.id.boat_id);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);


        surveydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BoatAddScreen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        surveydate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btype = myspinner.getSelectedItem().toString();
                PostAddData(btype);
            }

        });

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(BoatAddScreen.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        myspinner.setAdapter(myAdapter);
    }

    private void PostAddData(String btypeDesc) {

        bname = Boat_name.getText().toString().trim();
        bofficialnum = officialnumber.getText().toString().trim();
        bid = boatid.getText().toString().trim();
        bdateofsurvey = surveydate.getText().toString();
        if (bname.isEmpty() || btypeDesc.isEmpty() || bofficialnum.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Valid Data", Toast.LENGTH_LONG).show();
        } else {
            if (btypeDesc.equals("Survey")) {
                boatType = "SI";
                if (bdateofsurvey.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Survey Date", Toast.LENGTH_LONG).show();
                } else {
                    SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
                    bdateofsurvey = sm.format(bdateofsurvey);
                }
            } else if (btypeDesc.equals("Fishing")) {
                boatType = "FI";
            } else {
                boatType = "PA";
            }


            RequestQueue requestQueue = Volley.newRequestQueue(BoatAddScreen.this);
            String url = "http://103.127.31.139:7080/test/Boatmaster";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("anyText", response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");

                        if (message.contains("Success")) {
                            Toast.makeText(getApplicationContext(), "Data Successfully Added", Toast.LENGTH_LONG).show();
                        }
                        if (message.contains("Fail")) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    
                   Toast.makeText(getApplicationContext(), "Error !2" + error, Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("company_id", "C00001");
                    params.put("branch_id", "B00037");
                    params.put("fin_year", "2010");
                    params.put("emp_name", "Priyanka");
                    params.put("boat_name", bname);
                    params.put("boat_type", boatType);
                    params.put("boat_id", bid);
                    params.put("s_date", bdateofsurvey);
                    params.put("official_number", bofficialnum);

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}


