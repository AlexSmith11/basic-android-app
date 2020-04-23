package com.example.alexs.myassignment.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alexs.myassignment.R;
import com.example.alexs.myassignment.dataBase.WordCreate;
import com.example.alexs.myassignment.dataBase.WordViewModel;

import java.util.List;


public class ActivityRevise extends AppCompatActivity {
    private WordViewModel mWordDao;

    private Button submit;
    EditText answer;
    TextView ask;
    TextView score;

    int positionCounter;
    int questionCounter;
    int correctAnswers; //Counts how many answers are correct
    String correctAnswer;
    String answerString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise);

        answer = findViewById(R.id.editTextRevise);
        ask = findViewById(R.id.textViewRevise);    //Prints the question
        score = findViewById(R.id.textViewScore);   //Prints score

        mWordDao = ViewModelProviders.of(this).get(WordViewModel.class);
        new ActivityRevise.insertAsyncTask(mWordDao).execute();
    }

    private class insertAsyncTask extends AsyncTask<Void, Void, List<WordCreate>> {

        private WordViewModel mAsyncTaskDao;

        public insertAsyncTask(WordViewModel dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<WordCreate> doInBackground(final Void... params) {
            return mAsyncTaskDao.getWordTest();
        }

        @Override
        protected void onPostExecute(List<WordCreate> ReviseList) {  //puts list of all words in database into ReviseList
            startRevise(ReviseList);
        }
    }

    public void startRevise(final List<WordCreate> ReviseList) {

        askQuestion(ReviseList);


        positionCounter = 0;    //Counts which pair of words we are on so we don't get the same pair
        questionCounter = ReviseList.size();    //Holds number of questions we have

        //Takes answer from edit text, turns into string to compare with DB
        final Button submit = findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                answerString = answer.getText().toString();     //Takes answer, puts into string for comparison
                correctAnswer = ReviseList.get(positionCounter).getSWord();     //gets the word to compare against from DB
                if (positionCounter < questionCounter -1 ) {        //-1 because position counter starts at 0
                    if (answerString.equals(correctAnswer)) {
                        correctAnswers++;                            //reward for getting question correct
                        positionCounter++;          //Move to next word in DB
                        askQuestion(ReviseList);    //Prompt user with next word

                    } else {
                        positionCounter++;
                        askQuestion(ReviseList);    //Move onto next word
                        //But no reward
                    }
                }
            }
        });
    }

    private void askQuestion(List<WordCreate> ReviseList) {
        ask.setText("What is the translation of: " + ReviseList.get(positionCounter).getWord());   //prints word to be translated
        score.setText(correctAnswers + "/" + positionCounter);
    }

}
