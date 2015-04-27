package com.rubberduck.materialjumpstart.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rubberduck.materialjumpstart.R;
import com.rubberduck.materialjumpstart.adapters.MainRVAdapter;
import com.rubberduck.materialjumpstart.model.Dummy;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnEnterNameFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    public static final String TAG = "OffersFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    boolean mDualPane;

    private List<Dummy> dummyList;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    private OnEnterNameFragmentInteractionListener mListener;

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
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
        View rootView = inflater.inflate(R.layout.fragment_offer, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_main);
        recyclerView.setHasFixedSize(true);

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        loadSampleData();
        MainRVAdapter rvAdapter = new MainRVAdapter(getActivity(), dummyList);
        recyclerView.setAdapter(rvAdapter);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*buttonSendName = (Button) this.getView().findViewById(R.id.button_send);
        buttonSendName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });*/
    }

    public void onButtonPressed() {
        if (mListener != null) {
            //editName = (EditText) this.getView().findViewById(R.id.edit_name);
            //mListener.onEnterNameFragmentInteraction(editName.getText().toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnEnterNameFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnEnterNameFragmentInteractionListener {
        public void onEnterNameFragmentInteraction(String nameEntered);
    }

    public void loadSampleData() {
        dummyList = new ArrayList<>();

        for (int i=0; i<5; i++) {
            dummyList.add(i, new Dummy(i, "Header "+i, "HeaderText"+i, "SubHeader "+i,
                    "SubHeaderText "+i, 23));
        }
    }
}