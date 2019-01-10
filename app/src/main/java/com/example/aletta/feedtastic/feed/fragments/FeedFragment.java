package com.example.aletta.feedtastic.feed.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aletta.feedtastic.R;
import com.example.aletta.feedtastic.api.AuthHashGenerator;
import com.example.aletta.feedtastic.api.IDataProvider;
import com.example.aletta.feedtastic.api.RemoteDataProvider;
import com.example.aletta.feedtastic.api.model.ComicData;
import com.example.aletta.feedtastic.api.response.ComicResponse;
import com.example.aletta.feedtastic.feed.adapter.FeedAdapter;
import com.example.aletta.feedtastic.feed.adapter.OpenDetailListener;
import com.example.aletta.feedtastic.feed.fragments.adapter.MyComicAdapter;
import com.example.aletta.feedtastic.feed.model.Ads;
import com.example.aletta.feedtastic.feed.model.MyComicData;
import com.example.aletta.feedtastic.models.User;
import com.example.aletta.feedtastic.util.FeedRepository;
import com.example.aletta.feedtastic.util.FireBaseUserManager;
import com.example.aletta.feedtastic.util.SharedPrefManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;
import static com.example.aletta.feedtastic.api.Keys.PRIVATE_API_KEY;
import static com.example.aletta.feedtastic.api.Keys.PUBLIC_API_KEY;
import static com.example.aletta.feedtastic.feed.model.ComicUtil.GENERAL;
import static com.example.aletta.feedtastic.feed.model.ComicUtil.OWN;
import static com.example.aletta.feedtastic.util.Consants.USER;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {

    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.addFAB)
    FloatingActionButton addFAB;
    @BindView(R.id.progressbarF)
    ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;
    private List<Ads> allAds = new ArrayList<>();
    private MyComicAdapter recyclerViewAdapterOwn;
    private FeedAdapter recyclerViewAdapter;
    private ArrayList<ComicData> feedData;
    private FragmentTransaction ft;
    private String dataSetType;
    private ArrayList<MyComicData> myComics = new ArrayList<>();


    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FeedFragment.
     */
    public static FeedFragment newInstance(String own) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(OWN, own);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataSetType = getArguments().getString(OWN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);

        FireBaseUserManager.getInstance().write(SharedPrefManager.getInstance().getData(USER, User.class).getPhonenumber());
        userName.setText(SharedPrefManager.getInstance().getData(USER, User.class).getPhonenumber());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        feedData = new ArrayList<>();
        String hash = null;
        String timestamp = String.valueOf(System.currentTimeMillis());
        AuthHashGenerator authHashGenerator = new AuthHashGenerator();

        try {
            hash = authHashGenerator.generateHash(timestamp, PUBLIC_API_KEY, PRIVATE_API_KEY);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.VISIBLE);
        if (dataSetType.equals(GENERAL)) {
            if (FeedRepository.getInstance().getDataSet().isEmpty()) {
                RemoteDataProvider.instance().getComic("100", timestamp, PUBLIC_API_KEY, hash, new IDataProvider.DataListener<ComicResponse>() {
                    @Override
                    public void onSuccess(ComicResponse comicResponse) {

                        feedData.addAll(filterValuableContent(comicResponse));
                        FeedRepository.getInstance().setFeedData(feedData);
                        setupAdapter();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.d(TAG, "onError() called with: errorMessage = [" + errorMessage + "]");
                    }
                });
            }else{
                setupAdapter();
            }
        } else {
            setupAdapter();
        }


        setupFAB();
        return view;
    }

    private ArrayList<ComicData> filterValuableContent(ComicResponse comicResponse) {
        ArrayList<ComicData> dataSet = new ArrayList<>();
        for (ComicData comicData : comicResponse.getData().getResults()) {
            if (comicData.getDescription() != null && !TextUtils.isEmpty(comicData.getDescription())) {
                dataSet.add(comicData);
            }
        }
        return dataSet;
    }

    private void setupFAB() {
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container, AddNewComicsFragment.newInstance());
                    ft.addToBackStack("a");
                    ft.commit();
                }
            }
        });
    }

    private void setupAdapter() {
        if (dataSetType.equals(OWN)) {
            recyclerViewAdapterOwn = new MyComicAdapter(getContext(), FeedRepository.getInstance().getOwnsDataSet());

            recyclerView.setAdapter(recyclerViewAdapterOwn);


        } else {
            recyclerViewAdapter = new FeedAdapter(getContext(), FeedRepository.getInstance().getDataSet());
            recyclerViewAdapter.setOpenDetailedPageListener(new OpenDetailListener() {
                @Override
                public void onOpen(ComicData comicData) {
                    if (getFragmentManager() != null && comicData.getComicImage() != null) {
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.container, DetailedComicAdFragment.newInstance(comicData.getId(), comicData.getComicImage().getImagPathFul(), comicData));
                        ft.addToBackStack("a");
                        ft.commit();
                    }
                }
            });
            recyclerView.setAdapter(recyclerViewAdapter);
        }
        progressBar.setVisibility(View.INVISIBLE);

    }

    private void getFirebaseEvents() {
        FirebaseDatabase.getInstance().getReference().child("events").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getAllTask(dataSnapshot);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getAllTask(dataSnapshot);

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

    private void getAllTask(DataSnapshot dataSnapshot) {
        Ads ads = dataSnapshot.getValue(Ads.class);
        ads.setAdId(dataSnapshot.getKey());
        allAds.add(ads);

        //  recyclerViewAdapter = new FeedAdapter(getContext(), allAds);
        recyclerView.setAdapter(recyclerViewAdapter);
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
