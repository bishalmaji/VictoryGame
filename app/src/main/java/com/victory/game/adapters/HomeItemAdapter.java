package com.victory.game.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.victory.game.R;
import com.victory.game.models.HomeItemModel;

import java.util.List;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeItemViewHolder> {
    Context context;
    List<HomeItemModel> homeItemModels;

    public HomeItemAdapter(Context context, List<HomeItemModel> homeItemModels) {
        this.context = context;
        this.homeItemModels = homeItemModels;
    }

    @NonNull
    @Override
    public HomeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items,parent,false);

        return new HomeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemViewHolder holder, int position) {
        holder.description.setText(homeItemModels.get(position).getDescription());
        holder.price.setText(homeItemModels.get(position).getPrice());
        holder.imageView.setImageResource(homeItemModels.get(position).getProfile());

    }

    @Override
    public int getItemCount() {
        return homeItemModels.size();
    }

    static  class HomeItemViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView description, price;
        public HomeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item_home_profile);
            description=itemView.findViewById(R.id.item_home_description);
            price=itemView.findViewById(R.id.item_home_price);
        }
    }
}


