package com.example.aletta.feedtastic.feed.fragments;

import android.net.Uri;

import com.example.aletta.feedtastic.api.model.ComicData;
import com.example.aletta.feedtastic.base.BasePresenter;
import com.example.aletta.feedtastic.base.BaseView;
import com.example.aletta.feedtastic.feed.model.MyComicData;

import java.util.ArrayList;

public interface AddNewComicContract {
    interface AddNewView extends BaseView {

        void onFieldsValidated(MyComicData comicData);

        void onNewAdded();

        void onFieldsPopulated(ArrayList<String> catList);

        void onValidationError(String string);
    }

    interface AddNewPresenter extends BasePresenter<AddNewView> {

        void validateFields(MyComicData comicData, Uri upladedImage);

        void addNewComic(MyComicData comicData, Uri upladedImage);

        void populateCategories();
    }
}
