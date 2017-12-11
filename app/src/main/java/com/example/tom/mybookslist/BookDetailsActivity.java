package com.example.tom.mybookslist;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;


public class BookDetailsActivity extends ActionBarActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener{

    private TextView title, author, status, date, notes;
    private Book book;

    BooksDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        title = (TextView) findViewById(R.id.details_label_title);

        book = (Book) getIntent().getSerializableExtra("selectedBook");

        datasource = new BooksDataSource(this);

        //String dateString = new SimpleDateFormat("dd/MM/yyyy").format(book.getDateAdded());

        title = (TextView) findViewById(R.id.details_value_title);
        author = (TextView) findViewById(R.id.details_value_author);
        status = (TextView) findViewById(R.id.details_value_status);
        date = (TextView) findViewById(R.id.details_value_date);
        notes = (TextView) findViewById(R.id.details_value_notes);

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        status.setText(book.getBookStatus());
        //date.setText(dateString);
        date.setText("not available");
        notes.setText(book.getNotes());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_modify_book){
            //Go to ModifyGameActivity, and pass the current game with it to modify
            Intent intent = new Intent(BookDetailsActivity.this, ModifyBookActivity.class);
            intent.putExtra("currentBook", book);
            startActivity(intent);
        } else if (id == R.id.action_delete_book){
            //Show the ConfirmDeleteDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            dialog.show(this.getFragmentManager(), "ConfirmDeleteDialog");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        datasource.delete(book);
        showBookDeletedToast();
        Intent intent = new Intent(BookDetailsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void showBookDeletedToast(){
        Context context = getApplicationContext();
        String text = getString(R.string.book_deleted);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
