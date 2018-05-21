package com.seno.snote.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seno.snote.R;
import com.seno.snote.serviceModel.noteModel.TblNote;
import com.seno.snote.utility.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class AdapterNote extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<TblNote> items = new ArrayList<>();

    private Context ctx;
    private AdapterNote.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, TblNote obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterNote(Context contex , List<TblNote> items){
        this.ctx = contex;
        this.items = items;
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtDate;
        public TextView txtIsi;
        public ImageView img;
        public View lytParent;

        public OriginalViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
            txtDate = (TextView)itemView.findViewById(R.id.txtDate);
            txtIsi = (TextView)itemView.findViewById(R.id.txtIsi);
            img = (ImageView)itemView.findViewById(R.id.img);
            lytParent = (View)itemView.findViewById(R.id.lytParentNote);


        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof OriginalViewHolder){
            OriginalViewHolder view = (OriginalViewHolder) holder;
            TblNote note = items.get(position);
            view.txtTitle.setText(note.getTitle().toString());
            view.txtDate.setText(note.getDateChanged().toString());
            view.txtIsi.setText(note.getCotents().toString());
            ImageUtil.displayImage(view.img,note.getImg(),null);
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
