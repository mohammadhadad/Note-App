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

public class TaskEditDialog extends DialogFragment {
        private editNewTaskCallback Callback;
        private Task task;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Callback= (editNewTaskCallback) context;
        task=getArguments().getParcelable("Task");
        if (task==null)
            dismiss();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view= LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_task, null , false);
        TextInputEditText taskTitleET=view.findViewById(R.id.dialogET_EditDialog);
        taskTitleET.setText(task.getTask());
        TextInputLayout errorET=view.findViewById(R.id.dialogInputLayout_EditDialog);
        View btnSave=view.findViewById(R.id.saveBtn_EditDialog);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskTitleET.length()>0){
                    task.setTask(taskTitleET.getText().toString());
                    Callback.editTask(task);
                    dismiss();
                }else {
                    errorET.setError("Title Should Not Be Empty");
                }


            }
        });
        builder.setView(view);
        return builder.create();
    }

   public interface editNewTaskCallback{
        void editTask(Task task);
    }
}
