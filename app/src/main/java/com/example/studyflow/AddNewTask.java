package com.example.studyflow;


import android.app.Activity;
import android.content.Context;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studyflow.Database.TaskDBHelper;

import com.example.studyflow.Model.ToDoModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTask";

    //widgets
    private EditText mEditText;
    private Button mSaveButton;

    private TaskDBHelper myDb;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_task , container , false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText = view.findViewById(R.id.txtNewTask);
        mSaveButton = view.findViewById(R.id.btnSave);

        myDb = new TaskDBHelper(getActivity());

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            mEditText.setText(task);

            if (task.length() > 0 ){
                mSaveButton.setEnabled(false);
            }

        }
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    mSaveButton.setEnabled(false);
                    mSaveButton.setBackgroundColor(Color.GRAY);
                }else{
                    mSaveButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final boolean finalIsUpdate = isUpdate;
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("current_user", Context.MODE_PRIVATE);
                String savedUsername = sharedPreferences.getString("username","");

                if (finalIsUpdate){
                    myDb.updateTask(bundle.getInt("id") , text);
                }else{
                    ToDoModel item = new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    item.setUsername(savedUsername);
                    myDb.insertTask(item);
                }
                dismiss();

            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }
    }
}
