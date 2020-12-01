package com.example.forfoodiesbyfoodies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantListOfRestaurantsCard extends RecyclerView.Adapter<RestaurantListOfRestaurantsCard.RestaurantHolder> {
    ArrayList<Restaurant> list;
    RestaurantHolder.OnCardClickListener listener;

    public RestaurantListOfRestaurantsCard(ArrayList<Restaurant> list, RestaurantHolder.OnCardClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public RestaurantListOfRestaurantsCard(){

    }

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup view, int viewType) {
        View content = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_restaurant_list_of_restaurant_card, view , false);
        RestaurantHolder holder = new RestaurantHolder(content, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.area.setText(list.get(position).getArea());
        Picasso.get().load(list.get(position).getPicURL()).fit().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class RestaurantHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, area;
        ImageView image;
        RestaurantHolder.OnCardClickListener listener;

        public RestaurantHolder(@NonNull View itemView, OnCardClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_rest_lor_card_restaurantname);
            area = itemView.findViewById(R.id.tv_rest_lor_card_areaname);
            image = itemView.findViewById(R.id.iv_rest_lor_card_image);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        public interface OnCardClickListener {
            public void onCardClick(int i);
        }

        @Override
        public void onClick(View v) {
            this.listener.onCardClick(getAdapterPosition());
        }
    }
}
