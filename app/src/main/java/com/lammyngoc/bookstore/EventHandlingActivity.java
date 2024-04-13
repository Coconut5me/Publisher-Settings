package com.lammyngoc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EventHandlingActivity extends AppCompatActivity {

    Button btnChangeImage;
    ImageView imgStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_handling);
        addViews();
        addEvents();
    }

    private void addEvents() {
        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgStudent.getTag()!=null)
                {
                    if(imgStudent.getTag().equals("man"))
                    {
                        imgStudent.setImageResource(R.mipmap.ic_woman);
                        imgStudent.setTag("woman");
                    }
                    else
                    {
                        imgStudent.setImageResource(R.mipmap.ic_man);
                        imgStudent.setTag("man");
                    }
                }
                else
                {
                    imgStudent.setImageResource(R.mipmap.ic_man);
                    imgStudent.setTag("man");
                }
            }
        });
    }

    private void addViews() {
        btnChangeImage=findViewById(R.id.btnChangeImage);
        imgStudent=findViewById(R.id.imgStudent);
    }

    public void openPTB2Activity(View view) {
        Intent intent=new Intent(EventHandlingActivity.this, PTB2Activity.class);
        startActivity(intent);
    }

    public void openCalendarYear2LunarYearActivity(View view) {
        Intent intent=new Intent(EventHandlingActivity.this, CalendarYearToLunarYearActivity.class);
        startActivity(intent);
    }


    public void openImageSlideShowAcitivity(View view) {
        Intent intent=new Intent(EventHandlingActivity.this, ImageSliderShowActivityActivity.class);
        startActivity(intent);
    }

    public void openRuntimeViewActivity(View view) {
        Intent intent=new Intent(EventHandlingActivity.this, RuntimeViewActivity.class);
        startActivity(intent);
    }

    public void openNotificationActivity(View view) {
        Intent intent=new Intent(EventHandlingActivity.this, NotificationsExampleActivity.class);
        startActivity(intent);
    }
}