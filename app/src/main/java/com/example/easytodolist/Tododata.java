package com.example.easytodolist;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Todo.class},version = 1)
public abstract class Tododata extends RoomDatabase {

    public static Tododata instance;
    public abstract TodoDao todoDao();
    public static synchronized Tododata getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Tododata.class, "income_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    public static RoomDatabase.Callback roomCallback= new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private TodoDao todoDao;
        private PopulateDbAsyncTask(Tododata db) {
            todoDao = db.todoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
