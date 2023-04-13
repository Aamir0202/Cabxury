package com.example.cabxury;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BillingActivity extends AppCompatActivity {
    private String currency = "Rs. ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner1 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spinner_items2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter2);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                double minFare = 5;
                double maxFare = 20;
                double fare = minFare + Math.random() * (maxFare - minFare);
                String fareString = String.format("%.2f", fare);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        TextView editText = findViewById(R.id.textView7);
        double minFare = 100;
        double maxFare = 1500;
        double fare = minFare + Math.random() * (maxFare - minFare);
        editText.setText(currency + " " + String.format("%.2f", fare));
//        Toast.makeText(BillingActivity.this, "The fare is: " + currency + " " + fareString, Toast.LENGTH_SHORT).show();



        TextView fareText = findViewById(R.id.textView7);
        Button button = findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BillingActivity.this,"Your ride is booked,Thank you for using Cabxury",Toast.LENGTH_LONG).show();
                fareText.setText(currency + " " + fare);
            }
        });

    }
}
