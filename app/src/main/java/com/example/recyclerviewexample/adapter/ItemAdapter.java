package com.example.recyclerviewexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.modal.Modal;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Context context;
    List<Modal> modalList;
    RecyclerItemClick recyclerItemClick;

    public ItemAdapter(Context context, List<Modal> modalList,RecyclerItemClick recyclerItemClick) {
        this.context = context;
        this.modalList = modalList;
        this.recyclerItemClick=recyclerItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Modal modal = modalList.get(position);
        holder.nameText.setText(modal.getName());
        holder.titleTxt.setText(modal.getTitle());


    }

    @Override
    public int getItemCount() {
        return modalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView titleTxt,nameText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt =itemView.findViewById(R.id.titleText);
            nameText=itemView.findViewById(R.id.nameText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerItemClick.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerItemClick.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });

        }
    }

}
