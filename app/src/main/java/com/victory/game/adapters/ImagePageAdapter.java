package com.victory.game.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.victory.game.R;

import java.util.List;

public class ImagePageAdapter extends RecyclerView.Adapter<ImagePageAdapter.MViewHolder> {
    Context context;
    List<Integer> imageList;

    public ImagePageAdapter(Context context, List<Integer> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent,false);

        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {

        holder.imageView.setImageResource(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static  class MViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.row_slider_image);
        }
    }
}
