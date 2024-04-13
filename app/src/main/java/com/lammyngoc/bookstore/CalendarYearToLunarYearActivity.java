package com.lammyngoc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CalendarYearToLunarYearActivity extends AppCompatActivity
    implements View.OnClickListener
{


    EditText edtCalendarYear;
    Button btnLunarYear;
    TextView txtLunarYear;
    ImageView imgReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_year_to_lunar_year);
        addViews();
        addEvents();
    }

    private void addEvents() {
        btnLunarYear.setOnClickListener(this);
        imgReturn.setOnClickListener(this);
    }

    private void addViews() {
        edtCalendarYear=findViewById(R.id.edtCalendarYear);
        btnLunarYear=findViewById(R.id.btnLunarYear);
        txtLunarYear=findViewById(R.id.txtLunarYear);
        imgReturn=findViewById(R.id.imgReturn);
    }
    String convertCalendar2Lunar(int calendarYear)
    {
        String result="";
        String []arr_can={"Canh","Tân","Nhâm","Quý","Giáp","Ất","Bính","Đinh","Mẫu","Kỷ"};
        String []ar_chi={"Thân","Dậu","Tuất","Hợi","Tí","Sửu","Dần","Mẹo","Thìn","Tỵ","Ngọ","Mùi"};
        int can=calendarYear%10;
        String ten_can=arr_can[can];
        int chi=calendarYear%12;
        String ten_chi=ar_chi[chi];
        result=ten_can+" "+ten_chi;
        return result;
    }
    @Override
    public void onClick(View view) {
        if(view.equals(btnLunarYear))
        {
            //process convert Calendar Year to Lunar Year
            int calendarYear=Integer.parseInt(edtCalendarYear.getText().toString());
            String result=convertCalendar2Lunar(calendarYear);
            txtLunarYear.setText(result);
        }
        else if(view.equals(imgReturn))
        {
            //process return to the previous screen
            finish();
        }

    }
}