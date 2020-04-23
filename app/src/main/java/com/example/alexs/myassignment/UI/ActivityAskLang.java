package com.example.alexs.myassignment.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alexs.myassignment.MainActivity;
import com.example.alexs.myassignment.R;

public class ActivityAskLang extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyLangs";
    public Button buttonSaveLang;
    EditText editTextLang, editTextLang2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_lang);

        final SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        if (sharedPreferences.getString(getString(R.string.asklang_preferences_primary_key), null) != null
                && sharedPreferences.getString(getString(R.string.asklang_preferences_secondary_key), null) != null) {
            openActivityMain(); //go to menu
        } else {
            buttonSaveLang = (Button) findViewById(R.id.buttonChangeLanguage);    //buttonNotes is the xml button id
            buttonSaveLang.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editTextLang = (EditText) findViewById(R.id.editTextLang);       //Takes what is entered in the editTextLang xml box, stores in editTextLang
                    editTextLang2 = (EditText) findViewById(R.id.editTextLang2);
                    String primaryLanguage = editTextLang.getText().toString();
                    String translatedLanguage = editTextLang2.getText().toString();

                    SharedPreferences.Editor spEditor = sharedPreferences.edit();
                    spEditor.putString(getString(R.string.asklang_preferences_primary_key), primaryLanguage);
                    spEditor.putString(getString(R.string.asklang_preferences_secondary_key), translatedLanguage);

                    spEditor.apply();

                    openActivityMain(); //go to menu

                }
            });
        }
    }

    public void openActivityMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

