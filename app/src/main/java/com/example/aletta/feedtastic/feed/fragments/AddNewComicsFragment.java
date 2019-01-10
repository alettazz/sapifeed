package com.example.aletta.feedtastic.feed.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aletta.feedtastic.R;
import com.example.aletta.feedtastic.api.model.ComicData;
import com.example.aletta.feedtastic.base.BaseFragment;
import com.example.aletta.feedtastic.feed.fragments.adapter.CustomAdapter;
import com.example.aletta.feedtastic.feed.model.MyComicData;
import com.example.aletta.feedtastic.util.CameraUtil;
import com.example.aletta.feedtastic.util.FeedRepository;
import com.example.aletta.feedtastic.util.GlideApp;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aletta.feedtastic.util.CameraUtil.REQUEST_CAPTURE_IMAGE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewComicsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewComicsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewComicsFragment extends BaseFragment<AddNewComicContract.AddNewPresenter> implements AddNewComicContract.AddNewView, AdapterView.OnItemSelectedListener {


    @BindView(R.id.camera)
    View camera;
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.addImage)
    ImageView addImage;
    @BindView(R.id.newTitle)
    EditText newTitle;
    @BindView(R.id.newDescription)
    EditText newDescription;
    @BindView(R.id.categorySpinner)
    Spinner categorySpinner;
    @BindView(R.id.displayName)
    TextView displayName;
    @BindView(R.id.addButton)
    ImageView uploadNewButton;
    @BindView(R.id.progresssUpload)
    ProgressBar uploadProgress;
    private AddNewComicContract.AddNewPresenter presenter;
    private ArrayList<String> categories;
    private int badges[];
    private MyComicData comicDataToAdd;
    private int catNr;
    private Uri upladedImage;



    public AddNewComicsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddNewComicsFragment.
     */

    public static AddNewComicsFragment newInstance() {
        AddNewComicsFragment fragment = new AddNewComicsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AddNewPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_add_new_comic, container, false);
        ButterKnife.bind(this, inflate);

        setUpFields();

        presenter.populateCategories();

        setupAddButton();
        return inflate;
    }

    private void setupAddButton() {
        uploadNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comicDataToAdd = new MyComicData(String.valueOf(System.currentTimeMillis()),newTitle.getText().toString(),newDescription.getText().toString(),String.valueOf(catNr));
                presenter.validateFields(comicDataToAdd,upladedImage);
                uploadProgress.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setUpFields() {

    }

    private void setUpSpinner() {
        badges = new int[]{R.drawable.action,
                R.drawable.adult,
                R.drawable.adventure,
                R.drawable.autobiographies,
                R.drawable.aviation,
                R.drawable.british,
                R.drawable.christmas,
                R.drawable.comedy,
                R.drawable.crime,
                R.drawable.detective,
                R.drawable.disney,
                R.drawable.educational,
                R.drawable.fantasy,
                R.drawable.feminist,
                R.drawable.historical,
                R.drawable.horror,
                R.drawable.humor,
                R.drawable.jungle,
                R.drawable.mystery,
                R.drawable.nautical,
                R.drawable.nonfiction,
                R.drawable.pirate,
                R.drawable.romance,
                R.drawable.spy,
                R.drawable.sport,
                R.drawable.thriller,
                R.drawable.western
        };

        categorySpinner.setOnItemSelectedListener(this);

        CustomAdapter customAdapter = new CustomAdapter(getContext(), badges, categories);
        categorySpinner.setAdapter(customAdapter);


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    CameraUtil.dispatchTakePictureIntent(getActivity());
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAPTURE_IMAGE) {
            upladedImage = CameraUtil.getImageFilePath();
            GlideApp.with(this).load(upladedImage).circleCrop().into(addImage);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        catNr = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        catNr=-1;
    }

    @Override
    public void onFieldsValidated(MyComicData comicData) {
        presenter.addNewComic(comicData,upladedImage);
    }

    @Override
    public void onNewAdded() {
        //FeedRepository.getInstance().addToOwn(comicDataToAdd);
        uploadProgress.setVisibility(View.INVISIBLE);
        Toast.makeText(getContext(),"Added",Toast.LENGTH_SHORT).show();
        comicDataToAdd.setUploadedImage(upladedImage);
        FeedRepository.getInstance().addToOwn(comicDataToAdd);

    }

    @Override
    public void onFieldsPopulated(ArrayList<String> catList) {
        categories = new ArrayList<>();
        categories.addAll(catList);
        setUpSpinner();

    }

    @Override
    public void onValidationError(String string) {
        //TODO DIALOG
        Toast.makeText(getContext(),string,Toast.LENGTH_SHORT).show();
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
