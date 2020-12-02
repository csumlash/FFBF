package com.example.forfoodiesbyfoodies;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class RestaurantListOfReviewsCard  extends RecyclerView.Adapter<RestaurantListOfReviewsCard.RestaurantReviewHolder> {

    ArrayList<ReviewTemplate> list;
    RestaurantListOfReviewsCard.RestaurantReviewHolder.OnCardClickListener listener;

    public RestaurantListOfReviewsCard(ArrayList<ReviewTemplate> list, RestaurantListOfReviewsCard.RestaurantReviewHolder.OnCardClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public RestaurantListOfReviewsCard() {

    }

    @NonNull
    @Override
    public RestaurantListOfReviewsCard.RestaurantReviewHolder onCreateViewHolder(@NonNull ViewGroup view, int viewType) {
        View content = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_restaurant_list_of_reviews_card, view, false);
        RestaurantListOfReviewsCard.RestaurantReviewHolder holder = new RestaurantListOfReviewsCard.RestaurantReviewHolder(content, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListOfReviewsCard.RestaurantReviewHolder holder, int position) {
        holder.writer.setText(list.get(position).getWriter());
        holder.dateOfVisit.setText(list.get(position).getDateOfVisit());
        holder.review.setText(list.get(position).getReview());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class RestaurantReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView writer, dateOfVisit, review;
        RestaurantListOfReviewsCard.RestaurantReviewHolder.OnCardClickListener listener;

        public RestaurantReviewHolder(@NonNull View itemView, RestaurantListOfReviewsCard.RestaurantReviewHolder.OnCardClickListener listener) {
            super(itemView);
            writer = itemView.findViewById(R.id.tv_rest_loreview_card_username);
            review = itemView.findViewById(R.id.tv_rest_loreview_card_experience);
            dateOfVisit = itemView.findViewById(R.id.tv_rest_loreview_card_date);
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
