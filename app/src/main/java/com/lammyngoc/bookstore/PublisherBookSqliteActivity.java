package com.lammyngoc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.lammyngoc.bookstore.adapter.AdvancedBookAdapter;
import com.lammyngoc.bookstore.model.Book;
import com.lammyngoc.bookstore.model.Publisher;

import java.util.ArrayList;

public class PublisherBookSqliteActivity extends AppCompatActivity {

    Spinner spinnerPublisher;
    ArrayAdapter<Publisher> publisherAdapter;
    public static final String DATABASE_NAME = "BookStore.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    ListView lvBook;
    AdvancedBookAdapter advancedBookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_book_sqlite);
        addViews();
        loadPublisher();
        addEvents();
    }

    private void addEvents() {
        spinnerPublisher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Publisher selectedPublisher=publisherAdapter.getItem(i);
                loadBookByPublisher(selectedPublisher);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadBookByPublisher(Publisher selectedPublisher) {
        //Remember
        //(1) 1 Publisher has many books
        //(2) 1 book belong to a Publisher
        ArrayList<Book>books=new ArrayList<>();

        String sql="SELECT * FROM Book WHERE publisherId = '"+selectedPublisher.getPublisherId()+"'";

        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext())
        {
            String bookId=cursor.getString(0);
            String bookName=cursor.getString(1);
            float unitPrice=cursor.getFloat(2);
            String description=cursor.getString(3);

            Book book=new Book();
            book.setBookId(bookId);
            book.setBookName(bookName);
            book.setUnitPrice(unitPrice);
            book.setDescription(description);
            book.setPublisher(selectedPublisher);
            books.add(book);
        }
        cursor.close();
        selectedPublisher.setBooks(books);
        advancedBookAdapter.clear();
        advancedBookAdapter.addAll(books);
    }

    private void loadPublisher() {
        database = openOrCreateDatabase(DATABASE_NAME,
                MODE_PRIVATE, null);

        Cursor cursor = database.rawQuery("SELECT * FROM Publisher", null);
        while(cursor.moveToNext()){
            String publisherId = cursor.getString(0);
            String publisherName = cursor.getString(1);

            Publisher publisher =new Publisher(publisherId,publisherName);
            publisherAdapter.add(publisher);

        }
//        cursor.close();

    }

    private void addViews() {
        spinnerPublisher = findViewById(R.id.spinnerPublisherSettings);
        publisherAdapter = new ArrayAdapter<>(
                PublisherBookSqliteActivity.this,
                android.R.layout.simple_spinner_item);
        publisherAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerPublisher.setAdapter(publisherAdapter);

        lvBook=findViewById(R.id.lvBook);
        advancedBookAdapter=new AdvancedBookAdapter(
                PublisherBookSqliteActivity.this,R.layout.advancedbook_item);
        lvBook.setAdapter(advancedBookAdapter);
    }
}
