package com.example.alexs.myassignment.dataBase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WordRepository {

    private DAOQueries mWordDao;
    private LiveData<List<WordCreate>> mAllWords;
    private List<WordCreate> mWordTest;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    LiveData<List<WordCreate>> getAllWords() {
        return mAllWords;
    }
    List<WordCreate> getWordRevise() { return mWordDao.getWordRevise(); }  //Don't need liveData, don't need data all the time, only when on revise activity

    protected void insert (WordCreate word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    public static class insertAsyncTask extends AsyncTask<WordCreate, Void, Void> {

        private DAOQueries mAsyncTaskDao;

        public insertAsyncTask(DAOQueries dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WordCreate... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private DAOQueries mAsyncTaskDao;

        deleteAllWordsAsyncTask(DAOQueries dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void deleteAll()  {
        new deleteAllWordsAsyncTask(mWordDao).execute();
    }

    private static class deleteOneWordAsyncTask extends AsyncTask<WordCreate, Void, Void> {
        private DAOQueries mAsyncTaskDao;

        deleteOneWordAsyncTask(DAOQueries dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WordCreate... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }

    public void deleteWord(WordCreate word)  {
        new deleteOneWordAsyncTask(mWordDao).execute(word);
    }
}