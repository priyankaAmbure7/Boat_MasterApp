package com.example.boat_master;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class BoatAddScreen extends AppCompatActivity {

    private EditText survey_date;
    int year;
    DatePickerDialog.OnDateSetListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_add_screen);

        Spinner myspinner = (Spinner) findViewById(R.id.spinner);
        EditText editText=(EditText)findViewById(R.id.survey_date);
        Calendar calendar =Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);


        survey_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog= new DatePickerDialog(BoatAddScreen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        editText.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(BoatAddScreen.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        myspinner.setAdapter(myAdapter);



    }
}

