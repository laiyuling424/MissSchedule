package net.qiujuer.tips.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.qiujuer.tips.R;
import net.qiujuer.tips.view.activity.ContactDetailActivity;
import net.qiujuer.tips.view.activity.MainActivity;
import net.qiujuer.tips.view.adapter.AdapterSelectCallback;
import net.qiujuer.tips.view.adapter.ContactsAdapter;

import java.util.UUID;


public class ContactsFragment extends Fragment implements AdapterSelectCallback {
    private ContactsAdapter mAdapter;
    private LinearLayout contentAdView;
    private Boolean isad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        RecyclerView mRecycler = (RecyclerView) view.findViewById(R.id.time_line_recycler);
        TextView mStatus = (TextView) view.findViewById(R.id.text_status);

        mAdapter = new ContactsAdapter(mRecycler, mStatus, this);
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //Log.d("lyl","3----"+isVisibleToUser);
        isad=isVisibleToUser;
        //Log.d("lyl","3.1--isad--"+isad);
        super.setUserVisibleHint(isVisibleToUser);
        onActivityCreated(null);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // Log.d("lyl","3.2--isad--"+isad);

    }

    @Override
    public void onResume() {
        super.onResume();

        ContactsAdapter adapter = mAdapter;
        if (adapter != null)
            adapter.refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ContactsAdapter adapter = mAdapter;
        mAdapter = null;
        if (adapter != null)
            adapter.refresh();
    }

    @Override
    public void onItemSelected(UUID id) {
        MainActivity activity = (MainActivity) getActivity();
        ContactDetailActivity.actionStart(activity, id);
        activity.setBlur(activity);
    }

    @Override
    public void setLoading(boolean isLoad) {

    }
}
