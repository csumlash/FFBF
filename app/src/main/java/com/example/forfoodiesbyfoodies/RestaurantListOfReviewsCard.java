package com.example.forfoodiesbyfoodies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RestaurantListOfReviewsCard extends RecyclerView.Adapter<RestaurantListOfReviewsCard.RestaurantReviewHolder> {

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
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");
        Query userDetails = dbRef.orderByChild("username").equalTo(list.get(position).getWriter()).limitToFirst(1);
        userDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss : snapshot.getChildren()) {
                    String firstName = dss.child("firstName").getValue().toString();
                    String lastName = dss.child("lastName").getValue().toString();
                    holder.writer.setText(firstName + " " + lastName);
                    if (dss.child("url").getValue() != null) {
                        Picasso.get().load(dss.child("url").getValue().toString()).into(holder.image);
                    } else {
                        holder.image.setImageResource(R.drawable.ic_baseline_add_photo_image);
                    }
                }
                holder.dateOfVisit.setText(list.get(position).getDateOfVisit());
                holder.review.setText(list.get(position).getReview());
                holder.ratingBar.setRating(list.get(position).getRating());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RestaurantReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView writer, dateOfVisit, review;
        ImageView image;
        RatingBar ratingBar;
        RestaurantListOfReviewsCard.RestaurantReviewHolder.OnCardClickListener listener;

        public RestaurantReviewHolder(@NonNull View itemView, RestaurantListOfReviewsCard.RestaurantReviewHolder.OnCardClickListener listener) {
            super(itemView);
            writer = itemView.findViewById(R.id.tv_rest_loreview_card_username);
            review = itemView.findViewById(R.id.tv_rest_loreview_card_experience);
            dateOfVisit = itemView.findViewById(R.id.tv_rest_loreview_card_date);
            image = itemView.findViewById(R.id.iv_rest_loreview_card_image);
            ratingBar = itemView.findViewById(R.id.rb_rest_loreview_card_rating);

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
