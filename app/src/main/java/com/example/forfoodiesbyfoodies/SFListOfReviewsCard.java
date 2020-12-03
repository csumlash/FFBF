package com.example.forfoodiesbyfoodies;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class SFListOfReviewsCard extends RecyclerView.Adapter<SFListOfReviewsCard.SFReviewHolder> {

    ArrayList<ReviewTemplate> list;
    SFListOfReviewsCard.SFReviewHolder.OnCardClickListener listener;

    public SFListOfReviewsCard(ArrayList<ReviewTemplate> list, SFListOfReviewsCard.SFReviewHolder.OnCardClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public SFListOfReviewsCard() {

    }

    @NonNull
    @Override
    public SFListOfReviewsCard.SFReviewHolder onCreateViewHolder(@NonNull ViewGroup view, int viewType) {
        View content = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_sf_list_of_reviews_card, view, false);
        SFListOfReviewsCard.SFReviewHolder holder = new SFListOfReviewsCard.SFReviewHolder(content, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SFListOfReviewsCard.SFReviewHolder holder, int position) {
        holder.writer.setText(list.get(position).getWriter());
        holder.dateOfVisit.setText(list.get(position).getDateOfVisit());
        holder.review.setText(list.get(position).getReview());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class SFReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView writer, dateOfVisit, review;
        SFListOfReviewsCard.SFReviewHolder.OnCardClickListener listener;

        public SFReviewHolder(@NonNull View itemView, SFListOfReviewsCard.SFReviewHolder.OnCardClickListener listener) {
            super(itemView);
            writer = itemView.findViewById(R.id.tv_sf_review_card_username);
            review = itemView.findViewById(R.id.tv_sf_review_card_experience);
            dateOfVisit = itemView.findViewById(R.id.tv_sf_review_card_date);
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
