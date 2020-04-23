package com.example.alexs.myassignment;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.example.alexs.myassignment.UI.ActivityAddWord;
import com.example.alexs.myassignment.UI.ActivityAskLang;
import com.example.alexs.myassignment.UI.ActivityRevise;
import com.example.alexs.myassignment.dataBase.ActivityNotes;
import com.example.alexs.myassignment.dataBase.WordViewModel;

public class MainActivity extends AppCompatActivity {

    private Button buttonReview, buttonArchive, buttonReset;    //Buttons to open revise, notes and reset languages activity
    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);  //Associates UI with WordViewModel
        checkLang();    //Always calls on first time use

        //listens for a person to touch the 'add' floating action button
        FloatingActionButton fab = findViewById(R.id.fabMenu);  //Creates fab, fabMenu is the button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAdd();
            }
        });

        //listens for a person to touch the revise button
        buttonReview = (Button) findViewById(R.id.buttonRevise);    //buttonRevise is the xml button id
        buttonReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityRevise();
            }
        });

        //Listens for a person to touch 'notes' button
        buttonArchive = (Button) findViewById(R.id.buttonNotes);    //buttonNotes is the xml button id
        buttonArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityNotes();
            }
        });

        //Listens for a person to touch 'change language' button
        buttonReset = (Button) findViewById(R.id.buttonChangeLanguage);    //buttonNotes is the xml button id
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupLang();    //Are you sure? popup
            }
        });

        //Listens for a person to touch 'delete language settings' button
        buttonReset = (Button) findViewById(R.id.buttonDeleteNote);    //buttonDeleteLanguage is the xml button id
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupLangDel();
            }
        });
    }

    //Asks if we want to go ahead and change language. If so, empties prefs and DB and opens ActivityAskLang
    public void popupLang() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:   //If button pressed to proceed:
                        deleteLang();                      //Calls method to delete prefs and empty database
                        openActivityAskLang();          //Opens Change Language activity
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:   //If button pressed to cancel:
                        break;                          //Do nothing
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to change language? This will erase your Notes.").setPositiveButton("Change", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    //Asks if we want to go ahead and delete language settings. If so, empties prefs and DB.
    //Problem: openActivityAskLang needs to be activated by case statement, not by pressing the button directly.
    //This is because the UI does not wait for us to press 'change' or'no' in popup. Therefore, two separate methods.
    public void popupLangDel() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:   //If button pressed to proceed:
                        deleteLang();                      //Calls method to delete prefs and empty database
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:   //If button pressed to cancel:
                        break;                          //Do nothing
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete language settings? This will erase your Notes.").setPositiveButton("Change", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    //Delete stored language data (lang type & DB)
    public void deleteLang() {
        //Delete preferences. Need to delete before going to ActivityAskLang class. Otherwise will exit to main menu immediately.
        deletePreferences();

        //Delete SQL table
        mWordViewModel.deleteAll();
    }

    //Deletes preferences
    private void deletePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(ActivityAskLang.MY_PREFS_NAME, MODE_PRIVATE);    //Fetches primary/secondary language variables
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.remove(getString(R.string.asklang_preferences_primary_key));       //Clears primary language
        spEditor.remove(getString(R.string.asklang_preferences_secondary_key));     //Clears Secondary language
        spEditor.apply();
    }

    //Checks if we have language preferences set. If not, go to page that asks for them.
    public void checkLang() {
        SharedPreferences sharedPreferences = getSharedPreferences(ActivityAskLang.MY_PREFS_NAME, MODE_PRIVATE);    //Checks if the language preferences are empty.
        if (sharedPreferences.getString(getString(R.string.asklang_preferences_primary_key), null) == null
                && sharedPreferences.getString(getString(R.string.asklang_preferences_secondary_key), null) == null) {
            Toast toast = Toast.makeText(getApplicationContext(),       //Print a toast to ask user to enter their chosen languages.
                    "Please enter your languages",
                    Toast.LENGTH_LONG);

            toast.show();
            openActivityAskLang();
        }
    }

    //OPEN PAGE METHODS
    //Opens revise page
    public void openActivityRevise() {
        Intent intent = new Intent(this, ActivityRevise.class);
        startActivity(intent);
    }
    //Opens add note page
    public void openActivityAdd() {
        Intent intent = new Intent(this, ActivityAddWord.class);
        startActivity(intent);
    }
    //Opens notes page
    public void openActivityNotes() {
        Intent intent = new Intent(this, ActivityNotes.class);
        startActivity(intent);
    }
    //Opens ask language page
    public void openActivityAskLang() {
        Intent intent = new Intent(this, ActivityAskLang.class);
        startActivity(intent);
    }
}
//Activity -> WordLstAdapter -> WordViewModel -> WordRepository -> WordRoomDatabase -? WordCreate -> DAOQueries
