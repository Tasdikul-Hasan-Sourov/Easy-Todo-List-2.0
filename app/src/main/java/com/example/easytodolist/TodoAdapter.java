package com.example.easytodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> implements Filterable {
    private List<Todo> todos;
    private ArrayList<Todo> arrayList = new ArrayList<>();;
    private OnItemClickListener listener;
    private Context context;

    public TodoAdapter(Context context) {
        this.context=context;
    }

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
        holder.date.setText(currentTodo.getDate());
        holder.time.setText(currentTodo.getTime());

    }

    @Override
    public int getItemCount() {
        return todos == null ? 0 : todos.size();
    }
  public void setTodos(List<Todo> todos) {
        this.todos =todos ;
        arrayList.addAll(todos);
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                constraint = constraint.toString().toLowerCase().trim();
                todos.clear();
                if (constraint.length() == 0) {
                    todos.addAll(arrayList);
                } else {
                    for (Todo item : arrayList) {
                        if (item.getTitle().toLowerCase(Locale.getDefault()).contains(constraint))
                                 {
                            todos.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = todos;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               notifyDataSetChanged();
            }
        };
    }
}
