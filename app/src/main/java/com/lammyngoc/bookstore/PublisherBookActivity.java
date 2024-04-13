package com.lammyngoc.bookstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.lammyngoc.bookstore.adapter.AdvancedBookAdapter;
import com.lammyngoc.bookstore.model.Book;
import com.lammyngoc.bookstore.model.Publisher;

public class PublisherBookActivity extends AppCompatActivity {
    Spinner spinnerPublisher;
    ArrayAdapter<Publisher> publisherAdapter;
    ListView lvBook;
    AdvancedBookAdapter advancedBookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_book);
        addViews();
        makeFakeData();
        addEvents();
        readUserAciton();
    }

    private void readUserAciton() {
        SharedPreferences preferences=getSharedPreferences("USER_ACTION",MODE_PRIVATE);
        int spinner_index=preferences.getInt("SPINNER_INDEX", 0);
        spinnerPublisher.setSelection(spinner_index);
    }

    private void addEvents() {
        spinnerPublisher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Publisher selectedPublisher=publisherAdapter.getItem(i);
                advancedBookAdapter.clear();
                advancedBookAdapter.addAll(selectedPublisher.getBooks());

                SharedPreferences preferences=getSharedPreferences("USER_ACTION",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putInt("SPRING_INDEX",1);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book selectedBook=advancedBookAdapter.getItem(i);
                Intent intent=new Intent(PublisherBookActivity.this,BookDetailsActivity.class);
                intent.putExtra("SELECTED_BOOK", selectedBook);
                startActivity(intent);
            }
        });
    }

    private void makeFakeData() {
        Publisher p1=new Publisher("p1","NXB ĐHQG-HCM");
        Publisher p2=new Publisher("p2","NXB Kim Đồng");
        Publisher p3=new Publisher("p3","NXB Sự Thật");
        publisherAdapter.add(p1);
        publisherAdapter.add(p2);
        publisherAdapter.add(p3);

        Book b1=new Book("B1","Book1",15, R.mipmap.ic_book1);
        //b1.setDescription("<font color='red'>Description of Book1</font>");
        StringBuilder builder=new StringBuilder();

        builder.append("<table border='1'>");

        builder.append("<tr>");
        builder.append("<th>#</th>");
        builder.append("<th>Specs</th>");
        builder.append("</tr>");

        builder.append("<tr>");
        builder.append("<td>1</td>");
        builder.append("<td>Spec 1</td>");
        builder.append("</tr>");

        builder.append("<tr>");
        builder.append("<td>2</td>");
        builder.append("<td>Spec 2</td>");
        builder.append("</tr>");

        builder.append("<tr>");
        builder.append("<td>3</td>");
        builder.append("<td>Spec 3</td>");
        builder.append("</tr>");

        builder.append("</table>");

        b1.setDescription(builder.toString());

        p1.getBooks().add(b1);
        b1.setPublisher(p1);

        Book b2=new Book("B2","Book2",15, R.mipmap.ic_book2);
        b2.setDescription("<font color='green'>Description of Book2</font>");
        p2.getBooks().add(b2);
        b2.setPublisher(p2);

        Book b3=new Book("B3","Book3",15, R.mipmap.ic_book3);
        b3.setDescription("<font color='blue'><b>Description of Book3</b></font>");
        p3.getBooks().add(b3);
        b3.setPublisher(p3);

        Book b4=new Book("B43","Book4",15, R.mipmap.ic_book4);
        b4.setDescription("<font color='pink'>Description of Book4</font>");
        p2.getBooks().add(b4);
        b4.setPublisher(p2);

        Book b5=new Book("B5","Book5",15, R.mipmap.ic_book5);
        b5.setDescription("<font color='purple'>Description of Book5</font>");
        p2.getBooks().add(b5);
        b5.setPublisher(p2);
    }

    private void addViews() {
        spinnerPublisher=findViewById(R.id.spinnerPublisherSettings);
        publisherAdapter=new ArrayAdapter<>(
                PublisherBookActivity.this,
                android.R.layout.simple_spinner_item);
        publisherAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerPublisher.setAdapter(publisherAdapter);

        lvBook=findViewById(R.id.lvBook);
        advancedBookAdapter=new AdvancedBookAdapter(
                PublisherBookActivity.this,R.layout.advancedbook_item);
        lvBook.setAdapter(advancedBookAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_book,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mnuFeedback)
        {
            Intent intent=new Intent(PublisherBookActivity.this, FeedbackActivity.class);
            startActivityForResult(intent,1);
        }
        else if(item.getItemId()==R.id.mnuHelp)
        {
            String url="https://www.uel.edu.vn/";
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri= Uri.parse(url);
            intent.setData(uri);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.mnuAbout)
        {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2 && requestCode==1)
        {
            String content=data.getStringExtra("CONTENT_FEEDBACK");
            AlertDialog.Builder builder=new AlertDialog.Builder(PublisherBookActivity.this);
            builder.setTitle("Feedback");
            builder.setMessage(content);
            builder.create().show();
        }
    }
}