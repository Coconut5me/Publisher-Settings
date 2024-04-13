package com.lammyngoc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class RatingBookActivity extends AppCompatActivity {

    RadioButton radGood;
    RadioButton radNormal;
    RadioButton radBad;
    TextView txtResultVoting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_book);
        addViews();
    }

    private void addViews() {
        radGood=findViewById(R.id.radGood);
        radNormal=findViewById(R.id.radNormal);
        radBad=findViewById(R.id.radBad);
        txtResultVoting=findViewById(R.id.txtResultVoting);
    }

    public void processVoting(View view) {
        if(radGood.isChecked())
        {
            txtResultVoting.setText("You choose: "+radGood.getText().toString());
        }
        else if (radNormal.isChecked()) {
            txtResultVoting.setText("You choose: "+radNormal.getText().toString());
        }
        else if (radBad.isChecked()) {
            txtResultVoting.setText("You choose: "+radBad.getText().toString());
        }
    }

    public void goBack(View view) {
        finish();
    }
}