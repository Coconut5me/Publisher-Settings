package com.lammyngoc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SimpleListBookActivity extends AppCompatActivity {
    ListView lvBook;
    ArrayAdapter<String> bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list_book);
        addViews();
        loadListBook();
    }

    private void loadListBook() {
        bookAdapter.add("Book01\nBasic Android Programming\n100VND");
        bookAdapter.add("Book02\nAdvanced Android Programming\n200VND");
        bookAdapter.add("Book03\nMachine Learning\n70VND");
        bookAdapter.add("Book04\nDeep Learning\n80VND");
        String []arrDataset={"C++","Python","Java","C#"};
        bookAdapter.addAll(arrDataset);
    }

    private void addViews() {
        lvBook=findViewById(R.id.lvBook);
        bookAdapter=new ArrayAdapter<>(SimpleListBookActivity.this, android.R.layout.simple_list_item_1);
        lvBook.setAdapter(bookAdapter);
    }
}