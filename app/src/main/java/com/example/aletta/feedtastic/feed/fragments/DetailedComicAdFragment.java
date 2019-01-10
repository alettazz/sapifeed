package com.example.aletta.feedtastic.feed.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aletta.feedtastic.R;
import com.example.aletta.feedtastic.api.model.ComicData;
import com.example.aletta.feedtastic.util.GlideApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aletta.feedtastic.api.model.ComicOptions.COMIC_DETAIL;
import static com.example.aletta.feedtastic.api.model.ComicOptions.COMIC_URL;
import static com.example.aletta.feedtastic.api.model.ComicOptions.PARCELABLE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailedComicAdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedComicAdFragment extends Fragment {


    @BindView(R.id.headerImage)
    ImageView headerImage;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.seenIcon)
    ImageView seenIcon;
    @BindView(R.id.seenNr)
    TextView seenNr;
    @BindView(R.id.description)
    TextView description;

    private String comicId;
    private String comicUrl;
    private String seen = "0";
    private String seenNumber = "";
    private Object s = -1;


    private OnFragmentInteractionListener mListener;
    private ComicData comic;

    public DetailedComicAdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailedComicAdFragment.
     */
    public static DetailedComicAdFragment newInstance(String comicId, String comicUrl, ComicData comic) {
        DetailedComicAdFragment fragment = new DetailedComicAdFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARCELABLE,comic);
        args.putString(COMIC_DETAIL, comicId);
        args.putString(COMIC_URL, comicUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comic = getArguments().getParcelable(PARCELABLE);
            comicId = getArguments().getString(COMIC_DETAIL);
            comicUrl = getArguments().getString(COMIC_URL);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_comic_ad, container, false);
        ButterKnife.bind(this, view);

        final DatabaseReference myReference = FirebaseDatabase.getInstance().getReference();
        myReference.child("comics").child(comicId).child("seen").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }
                seenNumber = currentData.getValue().toString();
                updateSeenNumber();
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

            }
        });

        updateSeenNumber();

        setComicImageSlide();

        description.setText(comic.getDescription());



        return view;
    }

    private void setComicImageSlide() {
        GlideApp.with(getContext())
                .load(comicUrl)
                .into(headerImage);
    }

    private void updateSeenNumber() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                seenNr.setText((seenNumber));
            }
        });

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
