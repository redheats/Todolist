package com.example.redheats.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText;
    private Button edit_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        String item = getIntent().getStringExtra("item");

        editText = findViewById(R.id.edit_item);
        editText.setText(item);

        edit_done = findViewById(R.id.edit_done);
        edit_done.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == edit_done){

            Intent intent = new Intent(this, MainActivity.class);
            String text = editText.getText().toString().trim();

            intent.putExtra("item", text);

            setResult(RESULT_OK, intent);
            this.finish();
        }
    }
}
