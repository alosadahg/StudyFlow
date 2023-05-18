package com.example.studyflow;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTask";

    private EditText txtNewTask;
    private Button btnSave;
    private FirebaseFirestore firestore;
    Context context;

    public static AddNewTask newInstance() {
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task, container, false);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        txtNewTask = (EditText) view.findViewById(R.id.txtNewTask);
//        btnSave = (Button) view.findViewById(R.id.btnSave);
//
//        firestore = FirebaseFirestore.getInstance();

//        txtNewTask.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(charSequence.toString().equals("")) {
//                    btnSave.setEnabled(false);
//                } else {
//                    btnSave.setEnabled(true);
//                    btnSave.setBackgroundColor(getResources().getColor(R.color.main_color));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String task = txtNewTask.getText().toString();
//
//                if(task.isEmpty()) {
//                    Toast.makeText(context, "Cannot add empty task", Toast.LENGTH_SHORT).show();
//                } else {
//                    Map<String, Object> taskMap = new HashMap<>();
//                    taskMap.put("task", task);
//                    taskMap.put("status", 0);
//
//                    firestore.collection("task").add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            if(task.isSuccessful()) {
//                                Toast.makeText(context, "Task Saved", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                dismiss();
//            }
//        });
    //}

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        this.context = context;
//    }
//
//    @Override
//    public void onDismiss(@NonNull DialogInterface dialog) {
//        super.onDismiss(dialog);
//        Activity activity = getActivity();
//        if(activity instanceof OnDialogCloseListner) {
//
//        }
//    }
}
