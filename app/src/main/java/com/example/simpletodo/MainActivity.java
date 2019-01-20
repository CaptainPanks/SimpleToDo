package com.example.simpletodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String>items;
    ArrayAdapter<String>itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // sets stuff up when app is loaded
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems=(ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);


        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItems = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItems.getText().toString();
        itemsAdapter.add(itemText);
        etNewItems.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
    }

    //listener for long click item removal
    private void setupListViewListener() {
        Log.i("MainActivity","Settup up listener on list view: ");
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity","Item removed form list: " + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }


    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt"); // gets file
    }

    private void readItems() {
        try { // can throw exception
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset())); //reads lines of data file, creates array list of lines
        }
        catch (IOException e){
            Log.e("MainACtivity", "Error reading file",e);
            items=new ArrayList<>();
        }

    }

    private void writeItems () {
        try {
            FileUtils.writeLines(getDataFile(), items);
        }
        catch (IOException e){
            Log.e("MainActivity","Error writing file", e);
        }

    }
}
