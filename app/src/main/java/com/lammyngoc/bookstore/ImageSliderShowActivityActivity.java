package com.lammyngoc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageSliderShowActivityActivity extends AppCompatActivity {

    ImageView imgSmall1;
    ImageView imgSmall2;
    ImageView imgBig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider_show_activity);
        addViews();
    }

    private void addViews() {
        imgSmall1=findViewById(R.id.imgSmall1);
        imgSmall2=findViewById(R.id.imgSmall2);
        imgBig=findViewById(R.id.imgBig);
    }

    class MyClassEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            if(view.equals(imgSmall1))
            {
                imgBig.setImageResource(R.mipmap.ic_book_big1);
            }
            else if(view.equals(imgSmall2))
            {
                imgBig.setImageResource(R.mipmap.ic_book_big2);
            }
        }
    }
}