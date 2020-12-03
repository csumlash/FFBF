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

public class SfListOfStreetFoodsCard extends RecyclerView.Adapter<SfListOfStreetFoodsCard.StreetFoodHolder> {

    ArrayList<StreetFood> list;
    SfListOfStreetFoodsCard.StreetFoodHolder.OnCardClickListener listener;

    public SfListOfStreetFoodsCard(ArrayList<StreetFood> list, SfListOfStreetFoodsCard.StreetFoodHolder.OnCardClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public SfListOfStreetFoodsCard() {

    }

    @NonNull
    @Override
    public StreetFoodHolder onCreateViewHolder(@NonNull ViewGroup view, int viewType) {
        View content = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_sf_list_of_street_foods_card, view, false);
        StreetFoodHolder holder = new StreetFoodHolder(content, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SfListOfStreetFoodsCard.StreetFoodHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.area.setText(list.get(position).getArea());
        Picasso.get().load(list.get(position).getPicURL()).fit().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class StreetFoodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, area;
        ImageView image;
        RatingBar stars;
        SfListOfStreetFoodsCard.StreetFoodHolder.OnCardClickListener listener;

        public StreetFoodHolder(@NonNull View itemView, SfListOfStreetFoodsCard.StreetFoodHolder.OnCardClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_sf_lop_card_sfpname);
            area = itemView.findViewById(R.id.tv_sf_lop_card_areaname);
            image = itemView.findViewById(R.id.iv_sf_lop_card_image);
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

