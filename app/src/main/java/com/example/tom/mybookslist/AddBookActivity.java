package com.example.tom.mybookslist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddBookActivity extends ActionBarActivity {

    EditText titleInput;
    EditText authorInput;
    Spinner statusSpinner;
    EditText notesInput;
    Button saveButton;

    BooksDataSource datasource;

    private static final String LOGTAG = "EXPLORECA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        titleInput = (EditText) findViewById(R.id.add_input_title);
        authorInput = (EditText) findViewById(R.id.add_input_author);
        statusSpinner = (Spinner) findViewById(R.id.add_input_status);
        notesInput = (EditText) findViewById(R.id.add_input_notes);
        saveButton = (Button) findViewById(R.id.add_button_save);

        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.book_status, android.R.layout.simple_spinner_item);

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        datasource = new BooksDataSource(this);
        datasource.open();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveBook();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveBook(){
        //Get the current date in numbered day-month-year format
        String curDate = getSimpleCurrentDate();
        //Retrieve the input from the user
        String title = titleInput.getText().toString();
        String author = authorInput.getText().toString();
        String bookStatus = statusSpinner.getSelectedItem().toString();
        String notes = notesInput.getText().toString();

        if(title.equals("")){
            //Make EditText titleInput display an error message, and display a toast
            //that the title field is empty
            setErrorText(titleInput, getString(R.string.title_is_required));
            showToast(getString(R.string.title_field_is_empty));
        } else if (author.equals("")){
            //Make EditText platformInput display an error message, and display a toast
            //that the platform field is empty
            setErrorText(authorInput, getString(R.string.author_is_required));
            showToast(getString(R.string.author_field_is_empty));
        } else {


            Book book = new Book(-1, title, author, curDate, bookStatus, notes);

            //add book to database
            //Log.i(LOGTAG, "author = " + book.getAuthor() + " author variable = " + author);
            book = datasource.create(book);
            Log.i(LOGTAG, "book created with id " + book.getId() + " and author "
                    + book.getAuthor() + " and date " + book.getDateAdded());

            //Notify the user with a toast that the game has been added
            showToast(getString(R.string.book_has_been_added));
            //Go back to MainActivity
            Intent intent = new Intent(AddBookActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private String getSimpleCurrentDate(){
//        Date curDate = null;
//
//        Date today = new Date();
        //formatter that will convert dates into the day-month-year format

        String curDate = new String();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        //Today's date, but with time included, which we don't want
        Date today = new Date();

            //format.format returns a string, but we need a Date
            curDate = format.format(today);
            //Parse the date String into a Date object
            //today = format.parse(curDateString);


        return curDate;
    }

    private void showToast(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void setErrorText(EditText editText, String message){
        //get the color white in integer form
        int RGB = android.graphics.Color.argb(255,255,255,255);

        //Object that contains the color white
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(RGB);

        //object that will hold the message, and makes it possible to change the color of the text
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);

        //give the message from the first till the last character a white color.
        //The last '0' means that the message should not display additional behaviour
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);

        //Make the EditText display the error message
        editText.setError(ssbuilder);
    }
}
