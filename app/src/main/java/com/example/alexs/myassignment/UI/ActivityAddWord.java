package com.example.alexs.myassignment.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alexs.myassignment.R;
import com.example.alexs.myassignment.dataBase.WordCreate;
import com.example.alexs.myassignment.dataBase.WordViewModel;

public class ActivityAddWord extends AppCompatActivity {
    public static final String EXTRA_REPLY ="com.example.android.roomwordssample.REPLY";

    private EditText mEditWordView;
    private WordViewModel mWordViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        mEditWordView = findViewById(R.id.editText);

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText primaryLanguage = findViewById(R.id.editText);
                EditText secondaryLanguage = findViewById(R.id.editText2);
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String primaryLanguageString = primaryLanguage.getText().toString();
                    String secondaryLanguageString = secondaryLanguage.getText().toString();

                    WordCreate word = new WordCreate(primaryLanguageString,secondaryLanguageString);

                    mWordViewModel.insert(word);
                }
                finish();
            }
        });
    }
}