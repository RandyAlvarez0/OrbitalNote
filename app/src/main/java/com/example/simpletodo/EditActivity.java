package com.example.simpletodo;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    EditText etItem;
    Button btnSave;
    AnimationDrawable animDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etItem = findViewById(R.id.etItem);
        btnSave = findViewById(R.id.btnSave);

        animDrawable = (AnimationDrawable) btnSave.getBackground();
        animDrawable.setEnterFadeDuration(3);
        animDrawable.setExitFadeDuration(3000);
        animDrawable.start();

        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Item");

        etItem.setText(getIntent().getStringExtra(Application.KEY_ITEM_TEXT));

        btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Application.KEY_ITEM_TEXT, etItem.getText().toString());
                intent.putExtra(Application.KEY_ITEM_POSITION, getIntent().getExtras().getInt(Application.KEY_ITEM_POSITION));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}