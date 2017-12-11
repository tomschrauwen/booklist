package com.example.tom.mybookslist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final String LOGTAG = "EXPLORECA";

    private ListView bookList;

    BooksDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inputSearch = (EditText) findViewById(R.id.inputSearch);

        datasource = new BooksDataSource(this);
        datasource.open();

        List<Book> books = datasource.findAll();
        bookList = (ListView) findViewById(R.id.bookList);

        BookListItemAdapter adapter = new BookListItemAdapter((ArrayList)books, this);
        bookList.setAdapter(adapter);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Will trigger when the user clicks on a game
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);

                Book selectedBook = (Book) parent.getAdapter().getItem(position);
                intent.putExtra("selectedBook", selectedBook);
                //Open GameDetailsActivity
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_book){
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }
}
