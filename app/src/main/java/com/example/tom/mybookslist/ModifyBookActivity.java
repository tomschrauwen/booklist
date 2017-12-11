package com.example.tom.mybookslist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class ModifyBookActivity extends ActionBarActivity {

    EditText titleInput;
    EditText authorInput;
    Spinner statusSpinner;
    EditText notesInput;
    Button modifyButton;

    Book book;
    BooksDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_book);

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("currentBook");

        datasource = new BooksDataSource(this);

        titleInput = (EditText) findViewById(R.id.modify_input_title);
        authorInput = (EditText) findViewById(R.id.modify_input_author);
        statusSpinner = (Spinner) findViewById(R.id.modify_input_status);
        notesInput = (EditText) findViewById(R.id.modify_input_notes);

        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.book_status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        statusSpinner.setAdapter(statusAdapter);

        titleInput.setText(book.getTitle());
        authorInput.setText(book.getAuthor());
        setSpinnerPosition(statusAdapter);
        notesInput.setText(book.getNotes());

        modifyButton = (Button) findViewById(R.id.modify_button_modify);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyBook();
            }
        });
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

    private void setSpinnerPosition(ArrayAdapter adapter){
        if (!book.getBookStatus().equals(null)){
            //Gets the position of the correct spinner item by comparing
            //which item of the Spinner matches with the gameStatus
            int spinnerPosition = adapter.getPosition(book.getBookStatus());
            //Display the correct gameStatus in the Spinner based on the found position
            statusSpinner.setSelection(spinnerPosition);
        }
    }

    public void modifyBook() {
        //Get the input from the Views
        String title = titleInput.getText().toString();
        String author = authorInput.getText().toString();
        String bookStatus = statusSpinner.getSelectedItem().toString();
        String notes = notesInput.getText().toString();

        if (title.equals("")) {
            //Make EditText titleInput display an error message, and display a toast
            //that the title field is empty
            setErrorText(titleInput, getString(R.string.title_is_required));
            showToast(getString(R.string.title_field_is_empty));
        } else if (author.equals("")) {
            //Make EditText platformInput display an error message, and display a toast
            //that the platform field is empty
            setErrorText(authorInput, getString(R.string.author_is_required));
            showToast(getString(R.string.author_field_is_empty));
        } else {
            //update the game with the new data
            book.setTitle(title);
            book.setAuthor(author);
            book.setBookStatus(bookStatus);
            book.setNotes(notes);


            //to do: save de shit naar de database
            datasource.update(book);

            //Notify the user of the success
            showToast(getString(R.string.book_has_been_modified));

            //Go back to ModifyGameActivity, and pass the updated game with is
            Intent intent = new Intent(ModifyBookActivity.this, BookDetailsActivity.class);
            intent.putExtra("selectedBook", book);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify_book, menu);
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
}
