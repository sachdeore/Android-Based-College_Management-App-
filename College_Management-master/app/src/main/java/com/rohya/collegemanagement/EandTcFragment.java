package com.rohya.collegemanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EandTcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EandTcFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // My Declares
    ImageSlider imageSlider;
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    View rootView;
    private List<MainModel> dataList = new ArrayList<>();


    public EandTcFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EandTcFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EandTcFragment newInstance(String param1, String param2) {
        EandTcFragment fragment = new EandTcFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_eand_tc, container, false);
        ImageSlider();
        retriveData();

        return rootView;
    }
    private void ImageSlider() {
        imageSlider = rootView.findViewById(R.id.imageslider);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.img_1,  ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img_1,  ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img_1,  ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img_1,  ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img_1,  ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);
    }

    private void retriveData() {
        recyclerView = rootView.findViewById(R.id.asFragmentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("teachers").orderByChild("courses").startAt("ME").endAt("ME"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options, false);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}