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

import com.u3k.app.external.Ad;
import com.u3k.app.external.InterstitialAd;
import com.u3k.app.external.InterstitialAdListener;

import net.qiujuer.tips.Application;
import net.qiujuer.tips.LaunchActivity;
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
        if(isVisibleToUser){
            Log.e("lyll","ContactsFragment start load ad");
            InterstitialAd interstitial= new InterstitialAd(LaunchActivity.token,getActivity().getApplicationContext(), "u3k-1180816000032170818-20180816171102");
            //Log.d("lyll","token--"+LaunchActivity.token);
            //Log.d("lyll","interstitial--"+interstitial.toString());
            interstitial.setAdListener(new InterstitialAdListener() {
                @Override
                public void onError(Ad ad, int i, String s) {
                    Log.e("lyll","onError ad--"+ad+"  i--"+i+"  s--"+s);
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    Log.e("lyll","onAdLoaded ad--"+ad);
                }

                @Override
                public void onAdClicked(Ad ad) {
                    Log.e("lyll","onAdClicked");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    Log.d("lyll","onLoggingImpression");
                }

                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    Log.d("lyll","onInterstitialDisplayed");
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    Log.d("lyll","onInterstitialDismissed");
                }
            });
            interstitial.loadAd();
        }
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
