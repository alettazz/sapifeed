package com.example.aletta.feedtastic.registration;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aletta.feedtastic.MainActivity;
import com.example.aletta.feedtastic.R;
import com.example.aletta.feedtastic.models.User;
import com.example.aletta.feedtastic.util.FireBaseUserManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileFragment extends Fragment {


    @BindView(R.id.header_cover_image)
    ImageView headerCoverImage;
    @BindView(R.id.edit)
    ImageView edit;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.designation)
    TextView nickname;
    @BindView(R.id.user_profile_photo)
    ImageButton userProfilePhoto;
    @BindView(R.id.likedNr)
    TextView likedNr;
    @BindView(R.id.countriCode)
    TextView countriCode;
    @BindView(R.id.joinedDate)
    TextView joinedDate;
    @BindView(R.id.mobileNumber)
    TextView mobileNumber;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.reportedNr)
    TextView reportedNr;
    @BindView(R.id.buttonLogout)
    Button buttonLogout;
    @BindView(R.id.bottomContainer)
    ConstraintLayout bottomContainer;
    @BindView(R.id.userNameEditText)
    TextInputLayout userNameEditText;
    @BindView(R.id.nicknameEditText)
    TextInputLayout nicknameEditText;
    @BindView(R.id.emaiEditText)
    TextInputLayout emaiEditText;
    @BindView(R.id.buttonUpdate)
    Button buttonUpdate;
    @BindView(R.id.editContainer)
    ConstraintLayout editContainer;


    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        ButterKnife.bind(this, view);

        setupLogOut();

        setupFields();

        initEdit();

        return view;
    }

    private void initEdit() {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomContainer.setVisibility(View.INVISIBLE);
                editContainer.setVisibility(View.VISIBLE);
                buttonUpdate.bringToFront();
                emaiEditText.setVisibility(View.VISIBLE);
                userNameEditText.setVisibility(View.VISIBLE);
                nicknameEditText.setVisibility(View.VISIBLE);
                buttonUpdate.setVisibility(View.VISIBLE);
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"update",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupFields() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {

                    nickname.setText(user.getNickname());
                    name.setText(user.getSecondname()+user.getFirstname());
                    likedNr.setText(user.getHearted());
                    countriCode.setText(FirebaseAuth.getInstance().getCurrentUser().getProviderId());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("nickname").setValue(nickname.getText());
                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("email").setValue(email.getText());
                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("phonenumber").setValue(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
            }
        });
        }
        buttonUpdate.setVisibility(View.INVISIBLE);
        bottomContainer.setVisibility(View.VISIBLE);
        emaiEditText.setVisibility(View.INVISIBLE);
        nicknameEditText.setVisibility(View.INVISIBLE);
        userNameEditText.setVisibility(View.INVISIBLE);


    }

    private void setupLogOut() {
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }
        });
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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
