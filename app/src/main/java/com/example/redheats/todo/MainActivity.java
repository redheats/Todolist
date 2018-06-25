package com.example.redheats.todo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_CODE = 20;
    String result;
    int pos;

    private ListView listView;
    private ArrayList<String> listItem;
    private ArrayAdapter<String> adapter;

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        //listItem = new ArrayList<>();
        readData();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
        listView.setAdapter(adapter);

        editText = findViewById(R.id.item_insert);
        button = findViewById(R.id.add_item);
        button.setOnClickListener(this);
        listModifier();

    }

    private void listModifier() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //alertPopup("Do you want to delete " + adapter.getItem(i) + "?", i);
                listItem.remove(i);
                adapter.notifyDataSetChanged();
                writeData();

                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("item", adapter.getItem(i));
                pos = i;
                startActivityForResult(intent, REQUEST_CODE);
                //startActivity(new Intent(getApplicationContext(), EditActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            result = data.getStringExtra("item");
            listItem.set(pos, result);
            writeData();
            adapter.notifyDataSetChanged();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        if(view == button){
            addItem();
        }
    }

    private void addItem() {
        if(!editText.getText().toString().trim().isEmpty()){
            listItem.add(editText.getText().toString().trim());
            writeData();
            adapter.notifyDataSetChanged();
            editText.setText(null);
        }

    }
    public void readData(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            listItem = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            listItem = new ArrayList<>();
        }
    }
    public void writeData(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, listItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
