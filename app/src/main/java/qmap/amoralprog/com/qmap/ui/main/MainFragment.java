package qmap.amoralprog.com.qmap.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qmap.amoralprog.com.qmap.R;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private RecyclerView questsList;
    private QuestsAdapter questsAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        questsList = rootView.findViewById(R.id.rv_list_quests);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        questsList.setLayoutManager(layoutManager);
        questsList.setHasFixedSize(true);

        questsAdapter = new QuestsAdapter(10);
        questsList.setAdapter(questsAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

}
