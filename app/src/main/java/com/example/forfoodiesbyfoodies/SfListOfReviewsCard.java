package com.example.forfoodiesbyfoodies;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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

// A review card to present reviews 1-by-1 in the recyclerview
public class SfListOfReviewsCard extends RecyclerView.Adapter<SfListOfReviewsCard.SfReviewHolder> {
    ArrayList<ReviewTemplate> list;
    SfListOfReviewsCard.SfReviewHolder.OnCardClickListener listener;
    User user;
    StreetFood streetfood;


    // Constructor to initialise the mandatory elements of proper processing
    public SfListOfReviewsCard(ArrayList<ReviewTemplate> list, SfListOfReviewsCard.SfReviewHolder.OnCardClickListener listener,
                               User user, StreetFood streetfood) {
        this.list = list;
        this.listener = listener;
        this.user = user;
        this.streetfood = streetfood;
    }

    public SfListOfReviewsCard() {

    }

    // setting up the card to be ready for further settings
    @NonNull
    @Override
    public SfListOfReviewsCard.SfReviewHolder onCreateViewHolder(@NonNull ViewGroup view, int viewType) {
        View content = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_sf_list_of_reviews_card, view, false);
        SfListOfReviewsCard.SfReviewHolder holder = new SfListOfReviewsCard.SfReviewHolder(content, listener);
        return holder;
    }

    // setting up the requested card element
    @Override
    public void onBindViewHolder(@NonNull SfListOfReviewsCard.SfReviewHolder holder, int position) {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.dateOfVisit.setText(list.get(position).getDateOfVisit());
        holder.review.setText(list.get(position).getReview());
        holder.ratingBar.setRating(list.get(position).getRating());

        /* Setting up the red X image button to be visible for the administrators and the poster
         * users only. If the proper user or admin is viewing the card then let him delete the review. */
        if (user.getUserType().equals("admin") || list.get(position).getWriter().equals(user.getUsername())) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("streetfoods");
                    Query keyToSfPlace = dbRef.orderByChild("picURL").equalTo(streetfood.getPicURL()).limitToFirst(1);
                    keyToSfPlace.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dss : snapshot.getChildren()) {
                                DatabaseReference refToDelete = FirebaseDatabase.getInstance().getReference(
                                        "streetfoods/" + dss.getKey() + "/reviews/" + list.get(position).getReviewKey()
                                );
                                refToDelete.removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Intent i = new Intent(v.getContext(), SfListOfReviews.class);
                    i.putExtra("user", user);
                    i.putExtra("streetfood", streetfood);
                    v.getContext().startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // The class of a card to link its views, elements to the code, to let them be manipulated
    public static class SfReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView writer, dateOfVisit, review;
        ImageView image;
        RatingBar ratingBar;
        ImageButton deleteBtn;
        SfListOfReviewsCard.SfReviewHolder.OnCardClickListener listener;

        public SfReviewHolder(@NonNull View itemView, SfListOfReviewsCard.SfReviewHolder.OnCardClickListener listener) {
            super(itemView);
            writer = itemView.findViewById(R.id.tv_sf_review_card_username);
            review = itemView.findViewById(R.id.tv_sf_review_card_experience);
            dateOfVisit = itemView.findViewById(R.id.tv_sf_review_card_date);
            image = itemView.findViewById(R.id.iv_sf_review_card_image);
            ratingBar = itemView.findViewById(R.id.rb_sf_lor_wr_stars);
            deleteBtn = itemView.findViewById(R.id.ib_sf_review_card_delete);

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
