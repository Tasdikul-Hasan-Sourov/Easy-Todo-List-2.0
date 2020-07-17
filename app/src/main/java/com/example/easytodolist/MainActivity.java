package com.example.easytodolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TODO_REQUEST = 1;
    public static final int EDIT_TODO_REQUEST = 2;
    private TodoViewModel todoViewModel;
    private TodoAdapter todoAdapter;
    TextView emptxt,note;
    ImageView empimg;
    int backbuttoncount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptxt=findViewById(R.id.emptxt);
        empimg=findViewById(R.id.no_task);
        note=findViewById(R.id.notech);
        todoAdapter = new TodoAdapter(this);
        FloatingActionButton buttonAddNote = findViewById(R.id.adinFloat);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                startActivityForResult(intent, ADD_TODO_REQUEST);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(todoAdapter);
        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getAllTodo().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                todoAdapter.setTodos(todos);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                DeleteTask(todoAdapter.getTodoAt(viewHolder.getAdapterPosition()));

            }
        }).attachToRecyclerView(recyclerView);
        todoAdapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                intent.putExtra(AddEdit.EXTRA_ID, todo.getId());
                intent.putExtra(AddEdit.EXTRA_TITLE, todo.getTitle());
                intent.putExtra(AddEdit.EXTRA_DESCRIPTION, todo.getDescription());
                intent.putExtra(AddEdit.EXTRA_DATE, todo.getDate());
                intent.putExtra(AddEdit.EXTRA_TIME, todo.getTime());
                startActivityForResult(intent, EDIT_TODO_REQUEST);
            }
        });
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                emptxt.setVisibility(View.INVISIBLE);
                empimg.setVisibility(view.INVISIBLE);

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                emptxt.setVisibility(View.VISIBLE);
                empimg.setVisibility(view.VISIBLE);


            }
        });

    }



    private void DeleteTask(final Todo position) {

        new AlertDialog.Builder(this)
                .setMessage("Do you want to delete the task?")
                .setTitle("WARNING!!!")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        todoViewModel.delete(position);
                        Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Intent nn=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(nn);
                        finish();
                    }
                })
                .show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEdit.EXTRA_TITLE);
            String description = data.getStringExtra(AddEdit.EXTRA_DESCRIPTION);
            String date = data.getStringExtra(AddEdit.EXTRA_DATE);
            String time = data.getStringExtra(AddEdit.EXTRA_TIME);
            Todo todo = new Todo(title, description, date, time);
            todoViewModel.insert(todo);
            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEdit.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Task can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEdit.EXTRA_TITLE);
            String description = data.getStringExtra(AddEdit.EXTRA_DESCRIPTION);
            String date = data.getStringExtra(AddEdit.EXTRA_DATE);
            String time = data.getStringExtra(AddEdit.EXTRA_TIME);
            Todo todo = new Todo(title, description, date, time);
            todo.setId(id);
            todoViewModel.update(todo);
            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.searchtiew);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                todoAdapter.getFilter().filter(newText);
                return false;
            }

        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_task:
                todoViewModel.deleteAllTodo();
                Toast.makeText(this, "All Tasks deleted", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    public void onBackPressed() {
        if(backbuttoncount >= 1){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);}
        else{
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            backbuttoncount++;
        }
    }

}