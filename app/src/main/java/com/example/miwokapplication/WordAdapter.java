package com.example.miwokapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.MyViewHolder> {

    private OnItemClickListener mListener;
    ArrayList<Word> wordList;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public WordAdapter(ArrayList<Word> wordList) {
        this.wordList = wordList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.englishTextView.setText(wordList.get(position).getmEnglishWord());
        holder.miwokTextView.setText(wordList.get(position).getmMiwokWord());
        if (wordList.get(position).getmImageId() != 0){
            holder.imageView.setImageResource(wordList.get(position).getmImageId());
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
            holder.imageView.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return wordList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView englishTextView, miwokTextView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            englishTextView = itemView.findViewById(R.id.english_text_view);
            miwokTextView = itemView.findViewById(R.id.miwok_text_view);
            imageView = itemView.findViewById(R.id.word_icon);
            itemView.setOnClickListener(v -> {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            });

        }
    }
}
