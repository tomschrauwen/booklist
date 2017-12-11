package com.example.tom.mybookslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BooksDataSource {

    private static final String LOGTAG = "EXPLORECA";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static String[] allcolumns = {
            BooksDBOpenHelper.COLUMN_ID,
            BooksDBOpenHelper.COLUMN_TITLE,
            BooksDBOpenHelper.COLUMN_AUTHOR,
            BooksDBOpenHelper.COLUMN_STATUS,
            BooksDBOpenHelper.COLUMN_DATE};

    public BooksDataSource(Context context){
        dbhelper = new BooksDBOpenHelper(context);
    }

    public void open(){
        Log.i(LOGTAG, "database opened");
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "database close");
        dbhelper.close();
    }

    public Book create(Book book){
        ContentValues values = new ContentValues();
        values.put(BooksDBOpenHelper.COLUMN_TITLE, book.getTitle());
        values.put(BooksDBOpenHelper.COLUMN_AUTHOR, book.getAuthor());
        values.put(BooksDBOpenHelper.COLUMN_STATUS, book.getBookStatus());
        values.put(BooksDBOpenHelper.COLUMN_DATE, book.getDateAdded());

        long insertid = database.insert(BooksDBOpenHelper.TABLE_BOOKS, null, values);
        book.setId(insertid);
        return book;
    }

    /*public void delete(Book book){

        long bookid = book.getId();
        Log.i(LOGTAG, "before delete");
        database.delete(BooksDBOpenHelper.TABLE_BOOKS,
                BooksDBOpenHelper.COLUMN_ID + "='" + bookid + "'", null);
        Log.i(LOGTAG, "book removed with id " + bookid);
    }*/

    public boolean delete (Book book){
        long bookid = book.getId();
        Log.i("I/bookid", "Book id: " + bookid);
        if (database == null) {
            open();
        } else if (!database.isOpen()) {
            open();
        }
        boolean delete = database.delete(BooksDBOpenHelper.TABLE_BOOKS, BooksDBOpenHelper.COLUMN_ID
                + " =?", new String[] { Long.toString(bookid)}) > 0;
        if (database.isOpen()) {
            close();
        }
        return delete;
    }

    /*
    public List<Book> findAll(){
        List<Book> books = new ArrayList<Book>();

        Cursor cursor = database.query(BooksDBOpenHelper.TABLE_BOOKS,
                allcolumns, null, null, null, null, null);
        Log.i(LOGTAG, "returned " + cursor.getCount() + " rows");

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Book book = new Book();
                book.setId(cursor.getLong(cursor.getColumnIndex(BooksDBOpenHelper.COLUMN_ID)));
                book.setTitle(cursor.getString(cursor.getColumnIndex(BooksDBOpenHelper.COLUMN_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndex(BooksDBOpenHelper.COLUMN_AUTHOR)));
                book.setBookStatus(cursor.getString(cursor.getColumnIndex(BooksDBOpenHelper.COLUMN_STATUS)));
                books.add(book);
            }
        }
        return  books;
    }
    */
    public ArrayList<Book> findAll(){
        ArrayList<Book> books = new ArrayList<Book>();

        Cursor cursor = database.query(BooksDBOpenHelper.TABLE_BOOKS,
                allcolumns, null, null, null, null, null);
        Log.i(LOGTAG, "returned " + cursor.getCount() + " rows");

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Book book = new Book();
                book.setId(cursor.getLong(cursor.getColumnIndex(BooksDBOpenHelper.COLUMN_ID)));
                book.setTitle(cursor.getString(cursor.getColumnIndex(BooksDBOpenHelper.COLUMN_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndex(BooksDBOpenHelper.COLUMN_AUTHOR)));
                book.setDateAdded(cursor.getString(cursor.getColumnIndex(BooksDBOpenHelper.COLUMN_DATE)));
                book.setBookStatus(cursor.getString(cursor.getColumnIndex(BooksDBOpenHelper.COLUMN_STATUS)));
                books.add(book);
            }
        }
        Log.i(LOGTAG, "retrieved books from database with findall() method");
        return  books;
    }

    /*public Book update(Book book){
        ContentValues values = new ContentValues();
        values.put(BooksDBOpenHelper.COLUMN_TITLE, book.getTitle());
        values.put(BooksDBOpenHelper.COLUMN_AUTHOR, book.getAuthor());
        values.put(BooksDBOpenHelper.COLUMN_STATUS, book.getBookStatus());

        database.update(BooksDBOpenHelper.TABLE_BOOKS, values,BooksDBOpenHelper.COLUMN_ID +
                "=" + book.getId(), null);

        return book;
    }*/

    public void update(Book book){

        if (database == null) {
            open();
        } else if (!database.isOpen()) {
            open();
        }

        Log.i(LOGTAG, "update called");
        ContentValues values = new ContentValues();
        values.put(BooksDBOpenHelper.COLUMN_TITLE, book.getTitle());
        values.put(BooksDBOpenHelper.COLUMN_AUTHOR, book.getAuthor());
        values.put(BooksDBOpenHelper.COLUMN_STATUS, book.getBookStatus());

        database.update(BooksDBOpenHelper.TABLE_BOOKS, values,BooksDBOpenHelper.COLUMN_ID +
                " = " + book.getId(), null);
    }

}
