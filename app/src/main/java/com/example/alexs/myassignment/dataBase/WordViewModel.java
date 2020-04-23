package com.example.alexs.myassignment.dataBase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.alexs.myassignment.dataBase.WordCreate;
import com.example.alexs.myassignment.dataBase.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;

    private LiveData<List<WordCreate>> mAllWords;
    private List<WordCreate> mWordTest;

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<WordCreate>> getAllWords() { return mAllWords; }  //Passes words for Recycler View
    public List<WordCreate> getWordTest() { return mRepository.getWordRevise(); }   //Passes for Revision/Test

    public void insert(WordCreate word) { mRepository.insert(word); }

    public void deleteAll() {mRepository.deleteAll();}  //Deletes all words in database
    public void deleteWord(WordCreate word) {mRepository.deleteWord(word);}  //Deletes One word from DB
}