package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SfListOfStreetFoods extends AppCompatActivity implements SfListOfStreetFoodsCard.StreetFoodHolder.OnCardClickListener {

    Button add;
    RecyclerView sflist;
    ArrayList<StreetFood> list = new ArrayList<>();
    DatabaseReference dbref;
    SfListOfStreetFoodsCard adapter;

    // A User type object to store the User object got from the previous activities.
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_list_of_street_foods);


        add = findViewById(R.id.btn_sf_lop_addplace);
        sflist = findViewById(R.id.rv_sf_lop_sflist);
        sflist.setLayoutManager(new LinearLayoutManager(SfListOfStreetFoods.this));
        dbref = FirebaseDatabase.getInstance().getReference("streetfood");
        dbref.addListenerForSingleValueEvent(listener);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SfListOfStreetFoods.this, SfAddNewPlace.class);
                    i.putExtra("user", user);
                    startActivity(i);
                }
            });
    }
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dss : snapshot.getChildren()){
                StreetFood sf = dss.getValue(StreetFood.class);
                list.add(sf);
            }

            adapter = new SfListOfStreetFoodsCard(list, SfListOfStreetFoods.this);
            sflist.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public void onCardClick(int i) {
        Intent intent = new Intent(SfListOfStreetFoods.this, SfAbout.class);
        intent.putExtra("user", user);
        intent.putExtra("streetfood", list.get(i));
        startActivity(intent);
    }
}