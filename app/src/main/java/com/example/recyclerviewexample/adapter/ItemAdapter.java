package com.example.recyclerviewexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.modal.Modal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements Filterable {
    Context context;
    List<Modal> modalList;
    List<Modal> modalListAll;

    RecyclerItemClick recyclerItemClick;

    public ItemAdapter(Context context, List<Modal> modalList,RecyclerItemClick recyclerItemClick) {
        this.context = context;
        this.modalList = modalList;
        this.recyclerItemClick=recyclerItemClick;
        this.modalListAll = new ArrayList<>(modalList);
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        //run on bground thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Modal> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filterList.addAll(modalListAll);
            }else{
                for(Modal modal1 : modalListAll){
                    if (modal1.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterList.add(modal1);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filterList;
            return results;
        }

        //run on ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modalList.clear();
            modalList.addAll((Collection<? extends Modal>) results.values);
            notifyDataSetChanged();
        }
    };

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
