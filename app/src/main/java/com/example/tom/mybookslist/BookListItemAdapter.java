package com.example.tom.mybookslist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BookListItemAdapter extends BaseAdapter{
    private ArrayList<Book> bookArrayList;
    private Context context;
    private LayoutInflater inflater;

    private static final String DEBUGTAG = "DEBUG";

    public BookListItemAdapter(ArrayList<Book> list, Context context){
        this.bookArrayList = list;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bookArrayList.size();
    }

    @Override
    public Book getItem(int position) {
        return bookArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bookArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        //Check if the row is new
        if (row == null) {
            //Inflate the layout if it didn't exist yet
            row = inflater.inflate(R.layout.single_book_item, parent, false);

            //Create a new view holder instance
            holder = new ViewHolder(row);

            //set the holder as a tag so we can get it back later
            row.setTag(holder);
        } else {
            //The row isn't new so we can reuse the view holder
            holder = (ViewHolder) row.getTag();
        }

        //Populate the row
        holder.populateRow(getItem(position));

        return row;
    }

    class ViewHolder{
        private TextView title;
        private TextView author;
        private TextView status;
        private TextView date;


        //initialize the variables
        public ViewHolder(View view){
            title = (TextView) view.findViewById(R.id.bookTitle);
            author = (TextView) view.findViewById(R.id.bookAuthor);
            status = (TextView) view.findViewById(R.id.bookStatus);
            date = (TextView) view.findViewById(R.id.bookDate);

        }

        public void populateRow(Book book){
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            status.setText(book.getBookStatus());
            date.setText(book.getDateAdded());
            Log.i("I/test", "date: " + book.getDateAdded());

            /*
            Log.i(DEBUGTAG, "date added = " + book.getDateAdded());
            //Convert Date object to String by formatting it
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = format.format(book.getDateAdded());
            date.setText(dateString);
            */
        }

    }

}
