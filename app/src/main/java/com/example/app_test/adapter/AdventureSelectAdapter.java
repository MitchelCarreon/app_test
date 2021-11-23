package com.example.app_test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_test.R;

import java.util.List;

public class AdventureSelectAdapter extends RecyclerView.Adapter {
    private OnItemClickListener onItemClickListener;
    private List<Adventure_overview> adventuresList;
    private Context context;





    public AdventureSelectAdapter(List<Adventure_overview> adventuresList, Context context) {
        this.adventuresList = adventuresList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(int position) throws ClassNotFoundException;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder =
                new AdventureViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adventure_choice_row, parent, false));
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AdventureViewHolder viewHolder = (AdventureViewHolder) holder;

        Adventure_overview adventure_item = this.adventuresList.get(position);
        viewHolder.adventure_title.setText(adventure_item.getTitle());
        viewHolder.adventure_desc.setText(adventure_item.getDescription());
        viewHolder.itemView.setBackground(adventure_item.getBackground());

    }

    @Override
    public int getItemCount() {
        return this.adventuresList.size();
    }


    public class AdventureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView adventure_title;
        public TextView adventure_desc;
        public View layout_parent;

        public AdventureViewHolder(@NonNull View itemView) {
            super(itemView);
            this.adventure_title = itemView.findViewById(R.id.adventure_title);
            this.adventure_desc = itemView.findViewById(R.id.adventure_desc);
            this.layout_parent = itemView.findViewById(R.id.layout_parent);
            this.layout_parent.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            try {
                onItemClickListener.onItemClick(getLayoutPosition());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
