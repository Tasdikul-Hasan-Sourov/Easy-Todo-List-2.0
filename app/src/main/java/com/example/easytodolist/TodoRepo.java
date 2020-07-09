package com.example.easytodolist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepo {
    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodo;
    public TodoRepo(Application application) {
        Tododata database = Tododata.getInstance(application);
        todoDao = database.todoDao();
        allTodo = todoDao.getAllTodo();
    }
    public void insert(Todo todo) {
        new InsertTodoAsyncTask(todoDao).execute(todo);
    }
    public void update(Todo todo) {
        new UpdateTodoAsyncTask(todoDao).execute(todo);
    }
    public void delete(Todo todo) {
        new DeleteTodoAsyncTask(todoDao).execute(todo);
    }
    public void deleteAllTodo() {
        new DeleteAllTodosAsyncTask(todoDao).execute();
    }
    public LiveData<List<Todo>> getAllTodo() {
        return allTodo;
    }

    private static class InsertTodoAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;
        private InsertTodoAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }
        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.insert(todos[0]);
            return null;
        }
    }
    private static class UpdateTodoAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;
        private UpdateTodoAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }
        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.update(todos[0]);
            return null;
        }
    }
    private static class DeleteTodoAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;
        private DeleteTodoAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }
        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.delete(todos[0]);
            return null;
        }
    }
    private static class DeleteAllTodosAsyncTask extends AsyncTask<Void, Void, Void> {
        private TodoDao todoDao;
        private DeleteAllTodosAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.deleteAllTodo();
            return null;
        }
    }
}
