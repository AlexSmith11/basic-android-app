package com.example.alexs.myassignment.dataBase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//Database
@Entity(tableName = "word_table")
public class WordCreate {

    @PrimaryKey
    @NonNull
    //First column - stores first language word
    @ColumnInfo(name = "word")
    private String mWord;

    //First column - stores second translated word
    @ColumnInfo(name = "wordS")
    private String sWord;

    public WordCreate(@NonNull String mWord, String sWord) { this.mWord = mWord; this.sWord = sWord;}

    public String getWord(){return this.mWord;}

    public String getSWord(){return this.sWord;}
}