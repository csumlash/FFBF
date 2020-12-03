package com.example.forfoodiesbyfoodies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

        // Getting the User object from intent passed from previous activities
        Intent i = getIntent();
        user = i.getParcelableExtra("user");

        add = findViewById(R.id.btn_sf_lop_addplace);
        sflist = findViewById(R.id.rv_sf_lop_sflist);

        sflist.setLayoutManager(new LinearLayoutManager(SfListOfStreetFoods.this));
        dbref = FirebaseDatabase.getInstance().getReference("streetfoods");
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
            for (DataSnapshot dss : snapshot.getChildren()) {
                StreetFood sf = dss.getValue(StreetFood.class);
                list.add(sf);
            }

            //Sorting the list alphabetically by Street Food place name
            Collections.sort(list, new Comparator<StreetFood>() {
                @Override
                public int compare(StreetFood o1, StreetFood o2) {
                    return o2.getName().compareTo(o1.getName());
                }
            });
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