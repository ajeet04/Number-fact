package com.example.raghvendrapandey.knowledgeworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  private CheckBox forSpecific,forRandom;
    private Button proccedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        forSpecific=(CheckBox)findViewById(R.id.specific);
        forRandom=(CheckBox)findViewById(R.id.random);
        proccedButton =(Button)findViewById(R.id.proceed);
        proccedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(forSpecific.isChecked()){
                   Intent specIn=new Intent(MainActivity.this,SpecificFact.class);
                   startActivity(specIn);

               }
              else if(forRandom.isChecked()){
                   Intent ranIn=new Intent(MainActivity.this,RandomFact.class);
                   startActivity(ranIn);

               }
               else{
                   Toast.makeText(MainActivity.this, "Please select one fact", Toast.LENGTH_SHORT).show();
               }
            }
        });
        forSpecific.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    forRandom.setChecked(false);
                }
            }
        });
        forRandom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    forSpecific.setChecked(false);
                }
            }
        });
    }
}
