package com.example.aletta.feedtastic.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.aletta.feedtastic.api.model.ComicData;
import com.example.aletta.feedtastic.feed.adapter.FeedAdapter;
import com.example.aletta.feedtastic.feed.model.MyComicData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FeedRepository {
    private static final FeedRepository ourInstance = new FeedRepository();
    private ArrayList<ComicData> dataSet = new ArrayList<>();
    private ArrayList<MyComicData> ownsDataSet = new ArrayList<>();
    private Object image;
    private ArrayList<MyComicData> myComics;

    public static FeedRepository getInstance() {
        return ourInstance;
    }

    private FeedRepository() {}

    public void setFeedData(List<ComicData> results) {
        this.dataSet.addAll(results);
    }

    public ArrayList<ComicData> getDataSet() {
        return dataSet;
    }

    public void getMycomicsFromFireBase(){
        FirebaseDatabase.getInstance().getReference().child("my_comics").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MyComicData value = dataSnapshot.getValue(MyComicData.class);
                FeedRepository.getInstance().addToOwn(value);
                //myComics.add(value);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void setImage(Object o){
        this.image = o;
    }

    public Object getImage() {
        return image;
    }

    public ArrayList<MyComicData> getOwnsDataSet() {
        return ownsDataSet;
    }

    public void setOwnsDataSet(ArrayList<MyComicData> ownsDataSet) {
        this.ownsDataSet = ownsDataSet;
    }

    public void addToOwn(MyComicData comicData){
        if (ownsDataSet != null) {
            ownsDataSet.add(comicData);
        }
    }

    public ArrayList<String> getCategories() {

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Action Comics");
        categories.add("Adult Comics");
        categories.add("Adventure Comics");
        categories.add("Autobiographical Comics");
        categories.add("Aviation Comics");
        categories.add("British Comics");
        categories.add("Christmas Comics");
        categories.add("Comedy Comics");
        categories.add("Crime Comics");
        categories.add("Detective Comics");
        categories.add("Disney Comics");
        categories.add("Educational Comics");
        categories.add("Fantasy Comics");
        categories.add("Feminist Comics");
        categories.add("Historical Comics");
        categories.add("Horror Comics");
        categories.add("Humor Comics");
        categories.add("Jungle Comics");
        categories.add("Mystery Comics");
        categories.add("Nautical Comics");
        categories.add("Non-fiction Comics");
        categories.add("Pirate Comics");
        categories.add("Romance Comics");
        categories.add("Spy Comics");
        categories.add("Sports Comics");
        categories.add("Thriller Comics");
        categories.add("Western Comics");

        return categories;
    }
}
