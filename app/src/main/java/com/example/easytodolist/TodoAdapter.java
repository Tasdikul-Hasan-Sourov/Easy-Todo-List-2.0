package com.example.easytodolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {
    private List<Todo> todos = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taskitem, parent, false);
        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Todo currentTodo = todos.get(position);
        holder.title.setText(currentTodo.getTitle());
        holder.description.setText(currentTodo.getDescription());
        holder.date.setText(String.valueOf(currentTodo.getDate()));
        holder.time.setText(String.valueOf(currentTodo.getTime()));

    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }
    public Todo getTodoAt(int position) {
        return todos.get(position);
    }
    class TodoHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView date,time;
        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.itemDes);
            title = itemView.findViewById(R.id.itemTitle);
            date = itemView.findViewById(R.id.itemDate);
            time=itemView.findViewById(R.id.itemTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(todos.get(position));
                    }

                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Todo todo);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
