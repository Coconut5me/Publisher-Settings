package com.lammyngoc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FeedbackActivity extends AppCompatActivity {

    EditText edtContenFeedback;
    Button btnSubmitFeedback;
    Intent previousIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        addViews();
        getDataFromPreviousActivity();
        addEvents();
    }

    private void addEvents() {
        btnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=edtContenFeedback.getText().toString();
                previousIntent.putExtra("CONTENT_FEEDBACK",content);
                setResult(2,previousIntent);
                finish();
            }
        });
    }

    private void getDataFromPreviousActivity() {
        previousIntent=getIntent();
    }

    private void addViews() {
        edtContenFeedback=findViewById(R.id.edtContentFeedback);
        btnSubmitFeedback=findViewById(R.id.btnSubmitFeedback);
    }
}