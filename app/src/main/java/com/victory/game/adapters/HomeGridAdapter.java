package com.victory.game.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.victory.game.R;
import com.victory.game.models.HomeItemModel;

import java.util.List;

public class HomeGridAdapter extends BaseAdapter {
    Context context;
    List<HomeItemModel> homeItemModels;

    public HomeGridAdapter(Context context, List<HomeItemModel> homeItemModels) {
        this.context = context;
        this.homeItemModels = homeItemModels;
    }

    @Override
    public int getCount() {
        return homeItemModels.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HomeItemViewHolder holder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.home_items,viewGroup,false);
            holder=new HomeItemViewHolder(view);
            view.setTag(holder);

        }else{
            holder= (HomeItemViewHolder) view.getTag();
        }
        holder.description.setText(homeItemModels.get(i).getDescription());
        holder.price.setText(homeItemModels.get(i).getPrice());
        holder.imageView.setImageResource(homeItemModels.get(i).getProfile());
        return view;
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
