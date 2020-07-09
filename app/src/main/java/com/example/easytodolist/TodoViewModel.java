package com.example.easytodolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepo repository;
    private LiveData<List<Todo>> allTodo;
    public TodoViewModel(@NonNull Application application) {
        super(application);
        repository = new TodoRepo(application);
        allTodo = repository.getAllTodo();
    }
    public void insert(Todo todo) {
        repository.insert(todo);
    }
    public void update(Todo todo) {
        repository.update(todo);
    }
    public void delete(Todo todo) {
        repository.delete(todo);
    }
    public void deleteAllIncome() {
        repository.deleteAllTodo();
    }
    public LiveData<List<Todo>> getAllTodo() {
        return allTodo;
    }
}
