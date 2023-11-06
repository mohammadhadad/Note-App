package com.example.notedatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks=new ArrayList<>();
    private itemEventListener eventListener;
    public TaskAdapter(itemEventListener eventListener){
        this.eventListener=eventListener;
    }


    public void addTask(Task task){
        tasks.add(0,task);
        notifyItemInserted(0);
    }


    public void searchTask(List<Task> task){
        this.tasks=task;
        notifyDataSetChanged();
    }


    public void addAllItems(List<Task> task){
        this.tasks.addAll(task);
        notifyDataSetChanged();
    }

    public void deleteAll(){
        this.tasks.clear();
        notifyDataSetChanged();
    }

    public void deleteTask(Task task){

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId()==task.getId()){
                tasks.remove(i);
                notifyItemRemoved(i);
                break;
            }

        }
    }

    public void updateTask(Task task){

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId()==task.getId()){
                tasks.set(i,task);
                notifyItemChanged(i);
                break;
            }

        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindTask(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBox;
        private ImageView deleteBtn;



        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox=itemView.findViewById(R.id.checkTask);
            deleteBtn=itemView.findViewById(R.id.deleteBtn);
        }

        public void bindTask(Task task){
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setText(task.getTask());
            checkBox.setChecked(task.isCompleted());
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventListener.onDeleteClick(task);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    eventListener.onItemLongPress(task);
                    return false;
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    task.setCompleted(b);
                    eventListener.onItemCheckChange(task);

                }
            });

        }
    }


    public interface itemEventListener{
        void onDeleteClick(Task task);
        void onItemLongPress(Task task);

        void onItemCheckChange(Task task);

    }


}
