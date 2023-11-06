package com.example.notedatabase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TaskAddDialog extends DialogFragment {
        private addNewTaskCallback Callback;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Callback= (addNewTaskCallback) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view= LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_task, null , false);
        TextInputEditText taskTitleET=view.findViewById(R.id.dialogET);
        TextInputLayout errorET=view.findViewById(R.id.dialogInputLayout);
        View btnSave=view.findViewById(R.id.saveBtn);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task=new Task();
                if (taskTitleET.length()>0){
                    task.setTask(taskTitleET.getText().toString());
                    task.setCompleted(false);
                    Callback.addNewTask(task);
                    dismiss();
                }else {
                    errorET.setError("Title Should Not Be Empty");
                }


            }
        });
        builder.setView(view);
        return builder.create();
    }

   public interface addNewTaskCallback{
        void addNewTask(Task task);
    }
}
