package com.seno.snote.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seno.snote.R;
import com.seno.snote.serviceModel.categoryModel.TblKatagoryNote;

import java.util.ArrayList;
import java.util.List;

public class AdapterKategori extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TblKatagoryNote> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, TblKatagoryNote obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterKategori(Context context, List<TblKatagoryNote> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView txtkategori;
        public View lytParent;

        public OriginalViewHolder(View itemView) {
            super(itemView);
            txtkategori =(TextView) itemView.findViewById(R.id.kategori);
            lytParent = (View)itemView.findViewById(R.id.lytParent);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kategori,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof OriginalViewHolder){
            OriginalViewHolder view = (OriginalViewHolder) holder;
            TblKatagoryNote kategori = items.get(position);
            view.txtkategori.setText(kategori.getCategory());
            view.lytParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
