package com.example.alexs.myassignment.dataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DAOQueries {

    @Insert
    void insert(WordCreate word);

    //This deletes all entries into the database
    @Query("DELETE FROM word_table")
    void deleteAll();

    //Deletes individual words
    @Delete
    void deleteWord(WordCreate word);

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<WordCreate>> getAllWords();

    @Query("SELECT * from word_table ORDER BY word ASC")
    List<WordCreate> getWordRevise();

    @Query("SELECT * from word_table LIMIT 1")
    WordCreate[] getAnyWord();


}