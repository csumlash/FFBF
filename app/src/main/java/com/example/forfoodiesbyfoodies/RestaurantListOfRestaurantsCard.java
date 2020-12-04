package com.example.forfoodiesbyfoodies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/* This class is responsible to fill up 1 Card of the RecyclerView by the got list */
public class RestaurantListOfRestaurantsCard extends RecyclerView.Adapter<RestaurantListOfRestaurantsCard.RestaurantHolder> {
    ArrayList<Restaurant> list;
    RestaurantHolder.OnCardClickListener listener;

    // This Constructor initialise the Card object in the Recycler view and get a list of Restaurant object and where they will be used (adapter)
    public RestaurantListOfRestaurantsCard(ArrayList<Restaurant> list, RestaurantHolder.OnCardClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    // A default constructor that makes the class initialisable but represents missing details
    public RestaurantListOfRestaurantsCard() {
    }

    // This method links the Card template view/activity defined in .xml to this code
    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup view, int viewType) {
        View content = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_restaurant_list_of_restaurants_card, view, false);
        RestaurantHolder holder = new RestaurantHolder(content, listener);
        return holder;
    }

    // The following method fills up the the card with data from the list by the got position number
    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.area.setText(list.get(position).getArea());
        Picasso.get().load(list.get(position).getPicURL()).into(holder.image);
    }

    // This method provides the amount of list items in the list of Restaurants
    @Override
    public int getItemCount() {
        return list.size();
    }

    /* This class:
     * - provides the ability to fill up 1 card with one restaurant details
     * - to provide identification/indexing ability for the recycler view to know why card is clicked */
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

        @Override
        public void onClick(View v) {
            this.listener.onCardClick(getAdapterPosition());
        }

        public interface OnCardClickListener {
            public void onCardClick(int i);
        }
    }
}
