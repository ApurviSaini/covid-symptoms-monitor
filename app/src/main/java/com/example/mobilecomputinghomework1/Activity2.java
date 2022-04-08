package com.example.mobilecomputinghomework1;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Activity2 extends AppCompatActivity {


    Spinner s;
    RatingBar bar;
    String spinner_value = "";


    myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        bar = findViewById(R.id.bar);
//        Float ratingNumber = bar.getRating(); // get rating number from a rating bar
//        int numberOfStars = bar.getNumStars(); // get total number of stars of rating bar

        helper = new myDbAdapter(Activity2.this);

        Button update_button = findViewById(R.id.btnupload);
        Button symptoms = findViewById(R.id.Submit);


        //Spinner
        String[] arraySpinner = new String[]{"Select", "Nausea", "Headache", "Diarrhea", "Soar Throat", "Fever", "Muscle Ache", "Loss of Smell or Taste", "Cough", "Shortness of Breadth", "Feeling Tired"};
        Spinner s = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                int getid = parent.getSelectedItemPosition();   ///get the selected element place id
//                textView1.setText("Position of selected element: "+String.valueOf(getid));
                spinner_value = String.valueOf(parent.getItemAtPosition(position));   // getting the selected element value
                // textView2.setText("Value of Selected Spinner : "+getvalue);
                Toast.makeText(Activity2.this, "spinner value : " + spinner_value, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // perform click event on button
        update_button.setOnClickListener(v -> {
            // get values and then displayed in a toast
            String totalStars = "Total Stars:: " + bar.getNumStars();

            String rating = "Rating :: " + bar.getRating();
            //Toast.makeText(this, totalStars + "\n" + rating, Toast.LENGTH_LONG).show();

            Toast.makeText(this, spinner_value + "\n" + rating, Toast.LENGTH_LONG).show();

            long identity = helper.insertData(spinner_value, rating);
            if(identity<0)
            {
                Message.message(getApplicationContext(),"Unsuccessful");
            } else
            {
                Message.message(getApplicationContext(),"Successful");
            }

            String data = helper.getData();
            Message.message(this,data);

            //Delete all previous data
            //helper.deleteAll();
            //Message.message(this, "DELETED");

            //data = helper.getData();
            //Message.message(this,data);


        });



//        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                //int i = s.getSelectedItemPosition();
//                int i = s.getSelectedItemPosition();
//
//                Toast.makeText(Activity2.this, rating_array[i],
//                        Toast.LENGTH_SHORT).show();
////                rating_array[i] = (int) v;
//            }
//        });



        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity3();

            }
        });


    }
    public void openActivity3() {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);

    }





    }



