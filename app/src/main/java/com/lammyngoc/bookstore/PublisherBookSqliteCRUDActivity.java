package com.lammyngoc.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lammyngoc.bookstore.adapter.AdvancedBookAdapter;
import com.lammyngoc.bookstore.model.Book;
import com.lammyngoc.bookstore.model.Publisher;

import java.util.ArrayList;

public class PublisherBookSqliteCRUDActivity extends AppCompatActivity {

    Spinner spinnerPublisher, spinnerPublisherSettings;
    ArrayAdapter<Publisher> publisherAdapter;
    public static final String DATABASE_NAME = "BookStore.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    ListView lvBook;
    AdvancedBookAdapter advancedBookAdapter;
    EditText edtBookId, edtBookName, edtUnitPrice;
    Button btnNew, btnSave, btnUpdate, btnDelete;
    EditText edtPublisherId, edtPublisherName;
    Button btnNewPublisher, btnSavePublisher, btnUpdatePublisher, btnDeletePublisher;

    private Publisher selectedPublisher;

    Dialog dialog=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_book_sqlite_crudactivity);
        addViews();
        loadPublisher();
        addEvents();
    }

    private void displaySelectedPublisherInfo(Publisher publisher) {
        if (publisher != null) {
            edtPublisherId.setText(publisher.getPublisherId());
            edtPublisherName.setText(publisher.getPublisherName());
        }
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
        spinnerPublisherSettings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Publisher selectedPublisher = publisherAdapter.getItem(i);
                displaySelectedPublisherInfo(selectedPublisher);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book=advancedBookAdapter.getItem(i);
                edtBookId.setText(book.getBookId());
                edtBookName.setText(book.getBookName());
                edtUnitPrice.setText(book.getUnitPrice()+"");
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_publisher,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void addViews() {
        spinnerPublisher = findViewById(R.id.spinnerPublisherSettings);
        publisherAdapter = new ArrayAdapter<>(
                PublisherBookSqliteCRUDActivity.this,
                android.R.layout.simple_spinner_item);
        publisherAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerPublisher.setAdapter(publisherAdapter);

        lvBook=findViewById(R.id.lvBook);
        advancedBookAdapter=new AdvancedBookAdapter(
                PublisherBookSqliteCRUDActivity.this,R.layout.advancedbook_item);
        lvBook.setAdapter(advancedBookAdapter);

        edtBookId=findViewById(R.id.edtBookId);
        edtBookName=findViewById(R.id.edtBookName);
        edtUnitPrice=findViewById(R.id.edtUnitPrice);

        dialog=new Dialog(PublisherBookSqliteCRUDActivity.this);
        dialog.setContentView(R.layout.publisher_settings);

        spinnerPublisherSettings=dialog.findViewById(R.id.spinnerPublisherSettings);
        spinnerPublisherSettings.setAdapter(publisherAdapter);

        edtPublisherId=dialog.findViewById(R.id.edtPublisherId);
        edtPublisherName=dialog.findViewById(R.id.edtPublisherName);

        btnNewPublisher=dialog.findViewById(R.id.btnNewPublisher);
        btnSavePublisher=dialog.findViewById(R.id.btnSavePublisher);
        btnUpdatePublisher=dialog.findViewById(R.id.btnUpdatePublisher);
        btnDeletePublisher=dialog.findViewById(R.id.btnDeletePublisher);

        btnNewPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processNewPublisher();
            }
        });
        btnSavePublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processSavePublisher();
            }
        });
        btnUpdatePublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processUpdatePublisher();
            }
        });
        btnDeletePublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processDeletePublisher();
            }
        });
    }

    private void processDeletePublisher() {
        String publisherId = edtPublisherId.getText().toString();

        int rowsAffected = database.delete("Publisher", "publisherId=?", new String[]{publisherId});

        if (rowsAffected > 0) {
            Toast.makeText(getApplicationContext(), "Publisher deleted successfully", Toast.LENGTH_SHORT).show();
            publisherAdapter.clear();
            loadPublisher();
            dialog.dismiss(); // Dismiss dialog after deleting
        } else {
            Toast.makeText(getApplicationContext(), "Failed to delete publisher", Toast.LENGTH_SHORT).show();
        }
    }

    private void processUpdatePublisher() {
        String publisherId=edtPublisherId.getText().toString();
        ContentValues record = new ContentValues();
        record.put("publisherName", edtPublisherName.getText().toString());

        int rowsAffected = database.update("Publisher", record, "publisherId=?", new String[]{publisherId});

        if (rowsAffected > 0) {
            Toast.makeText(getApplicationContext(), "Publisher updated successfully", Toast.LENGTH_SHORT).show();
            publisherAdapter.clear();
            loadPublisher();
            dialog.dismiss(); // Dismiss dialog after updating
        } else {
            Toast.makeText(getApplicationContext(), "Failed to update publisher", Toast.LENGTH_SHORT).show();
        }
    }

    private void processSavePublisher() {
        ContentValues record=new ContentValues();
        record.put("publisherId",edtPublisherId.getText().toString());
        record.put("publisherName",edtPublisherName.getText().toString());

        long result=database.insert("Publisher",null,record);

        if(result>0)
            publisherAdapter.clear();
            loadPublisher();
    }

    private void processNewPublisher() {
        edtPublisherId.setText("");
        edtPublisherName.setText("");
        edtPublisherId.requestFocus();
    }


    public void processNew(View view) {
        edtBookId.setText("");
        edtBookName.setText("");
        edtUnitPrice.setText("");
        edtBookId.requestFocus();
    }

    public void processSave(View view) {
        ContentValues record=new ContentValues();
        record.put("bookId",edtBookId.getText().toString());
        record.put("bookName",edtBookName.getText().toString());
        record.put("unitPrice",Float.parseFloat(edtUnitPrice.getText().toString()));
        Publisher publisher=(Publisher) spinnerPublisher.getSelectedItem();
        record.put("publisherId",publisher.getPublisherId());
        long result=database.insert("Book",null,record);
        if(result>0)
            loadBookByPublisher(publisher);
    }

    public void processUpdate(View view) {
        ContentValues record=new ContentValues();
        record.put("bookName",edtBookName.getText().toString());
        record.put("unitPrice",Float.parseFloat(edtUnitPrice.getText().toString()));
        Publisher publisher=(Publisher) spinnerPublisher.getSelectedItem();
        record.put("publisherId",publisher.getPublisherId());
        String bookId=edtBookId.getText().toString();
        long result=database.update("Book",record,"bookId=?", new String[]{bookId});
        if(result>0)
            loadBookByPublisher(publisher);
    }

    public void processDelete(View view) {
        String bookId=edtBookId.getText().toString();
        long result=database.delete("Book","bookId=?", new String[]{bookId});
        Publisher publisher=(Publisher) spinnerPublisher.getSelectedItem();
        if(result>0){
            loadBookByPublisher(publisher);
            processNew(view);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuPublisherSettings) {
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
