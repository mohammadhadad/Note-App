package com.example.notedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAddDialog.addNewTaskCallback , TaskAdapter.itemEventListener , TaskEditDialog.editNewTaskCallback{
    private TaskAdapter taskAdapter=new TaskAdapter(this);
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDao=AppDataBase.getAppDataBase(this).getTaskDao();

        EditText searchEt=findViewById(R.id.searchEt);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length()>0){
                        List<Task> tasks=taskDao.search(charSequence.toString());
                        taskAdapter.searchTask(tasks);
                    }else {
                        List<Task> tasks=taskDao.gettask();
                        taskAdapter.searchTask(tasks);
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        RecyclerView recyclerView=findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this , RecyclerView.VERTICAL , false));
        recyclerView.setAdapter(taskAdapter);
        List<Task> tasks =taskDao.gettask();
        taskAdapter.addAllItems(tasks);


        View addNewBtn=findViewById(R.id.addNewTaskBtn);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskAddDialog taskDialog=new TaskAddDialog();
                taskDialog.show(getSupportFragmentManager() , null);

            }
        });

        ImageView clearAllBtn=findViewById(R.id.clearAllBtn);
        clearAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDao.deleteAll();
                taskAdapter.deleteAll();

            }
        });

    }

    @Override
    public void addNewTask(Task task) {
        long taskId=taskDao.add(task);
        if (taskId!=-1){
            task.setId(taskId);
            taskAdapter.addTask(task);
        }
    }

    @Override
    public void onDeleteClick(Task task) {
        int result=taskDao.delete(task);
        if (result>0)
            taskAdapter.deleteTask(task);
    }

    @Override
    public void onItemLongPress(Task task) {
        TaskEditDialog editDialog=new TaskEditDialog();
        Bundle bundle=new Bundle();
        bundle.putParcelable("Task" , task);
        editDialog.setArguments(bundle);
        editDialog.show(getSupportFragmentManager(),null);

    }

    @Override
    public void onItemCheckChange(Task task) {
        taskDao.update(task);


    }

    @Override
    public void editTask(Task task) {
        int result=taskDao.update(task);
        if (result>0){
            taskAdapter.updateTask(task);
        }


    }
}