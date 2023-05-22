package com.example.studyflow.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyflow.Model.NoteModel;
import com.example.studyflow.NoteDetailsActivity;
import com.example.studyflow.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteAdapter extends FirestoreRecyclerAdapter<NoteModel, NoteAdapter.NoteViewHolder> {

    Context context;
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<NoteModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull NoteModel note) {
        String timestampFormat = new SimpleDateFormat("MM/dd/yyyy").format(note.timestamp.toDate());
        holder.titleTextView.setText(note.title);
        holder.contentTextView.setText(note.content);
        holder.timestampTextView.setText(timestampFormat);

        int color = getRandomColor();
        int colorRes = ContextCompat.getColor(holder.itemView.getContext(), color);
        holder.noteLayout.setBackgroundColor(colorRes);

        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, NoteDetailsActivity.class);
            intent.putExtra("title", note.title);
            intent.putExtra("content", note.content);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler_note_item, parent, false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentTextView, timestampTextView;
        LinearLayout noteLayout;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.note_title_text_view);
            contentTextView = itemView.findViewById(R.id.note_content_text_view);
            timestampTextView = itemView.findViewById(R.id.note_timestamp_text_view);
            noteLayout = itemView.findViewById(R.id.noteLayout);
        }
    }

    private int getRandomColor() {
        List<Integer> colors = new ArrayList<>();
        colors.add(R.color.pastel_pink);
        colors.add(R.color.pastel_blue);
        colors.add(R.color.pastel_green);
        colors.add(R.color.pastel_yellow);
        colors.add(R.color.pastel_purple);
        colors.add(R.color.pastel_orange);
        colors.add(R.color.pastel_mint);
        colors.add(R.color.pastel_lavender);
        colors.add(R.color.pastel_coral);
        colors.add(R.color.pastel_peach);
        colors.add(R.color.pastel_gray);

        Random random = new Random();
        int code = random.nextInt(colors.size());
        return colors.get(code);
    }

}
