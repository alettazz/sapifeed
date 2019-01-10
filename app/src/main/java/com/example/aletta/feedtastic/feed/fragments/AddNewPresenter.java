package com.example.aletta.feedtastic.feed.fragments;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.aletta.feedtastic.R;
import com.example.aletta.feedtastic.ResourceProvider;
import com.example.aletta.feedtastic.api.model.ComicData;
import com.example.aletta.feedtastic.feed.fragments.adapter.MyComicAdapter;
import com.example.aletta.feedtastic.feed.model.MyComicData;
import com.example.aletta.feedtastic.util.FeedRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddNewPresenter implements AddNewComicContract.AddNewPresenter {

    private AddNewComicContract.AddNewView view;
    private boolean successful;

    public AddNewPresenter(AddNewComicContract.AddNewView view) {
        this.view = view;
    }

    @Override
    public void validateFields(MyComicData comic, Uri upladedImage) {
        if (view != null) {
            if (comic.getOwnCategory() == "-1" || upladedImage == null) {
                view.onValidationError(ResourceProvider.getInstance().getString(R.string.validationError));
            } else {
                view.onFieldsValidated(comic);
            }
        }
    }

    @Override
    public void addNewComic(MyComicData comic, Uri upladedImage) {

        if (view != null) {
            FirebaseDatabase.getInstance().getReference().child("my_comics").child(comic.getId()).setValue(comic).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        successful = true;
                    }
                }
            });

            StorageReference riversRef = FirebaseStorage.getInstance().getReference().child(comic.getId()).child(upladedImage.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(upladedImage);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    if (successful) {
                        view.onNewAdded();
                    }
                }
            });

        }

    }

    @Override
    public void populateCategories() {
        if (view != null) {
            view.onFieldsPopulated(FeedRepository.getInstance().getCategories());
        }
    }

}
