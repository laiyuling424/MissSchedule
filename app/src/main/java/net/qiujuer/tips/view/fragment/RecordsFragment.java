package net.qiujuer.tips.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import net.qiujuer.tips.R;
import net.qiujuer.tips.view.activity.MainActivity;
import net.qiujuer.tips.view.activity.RecordDetailActivity;
import net.qiujuer.tips.view.adapter.AdapterSelectCallback;
import net.qiujuer.tips.view.adapter.RecordsAdapter;

import java.util.UUID;


public class RecordsFragment extends Fragment implements AdapterSelectCallback {
    private RecordsAdapter mAdapter;
    private LinearLayout contentAdView;
    private Boolean isad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_records, container, false);

        RecyclerView mRecycler = (RecyclerView) view.findViewById(R.id.time_line_recycler);
        TextView mStatus = (TextView) view.findViewById(R.id.text_status);
        //Log.d("lyll","222");

        mAdapter = new RecordsAdapter(mRecycler, mStatus, this);

        return view;
    }

    @Override
    public void onResume() {
        Log.d("lyll","444");
/*        Intent intent=getActivity().getIntent();
        String data=intent.getStringExtra("isShow");
        Log.d("lyl","data--"+data);*/
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //Log.d("lyl","2----"+isVisibleToUser);
        isad=isVisibleToUser;
        //Log.d("lyl","2.1--isad--"+isad);
        //Log.d("lyll","111");
        super.setUserVisibleHint(isVisibleToUser);
        onActivityCreated(null);
        if(isVisibleToUser){
            Log.e("lyll","RecordsFragment start load ad");
            InterstitialAd interstitial= new InterstitialAd(Application.token,getActivity().getApplicationContext(), "u3k-1180816000032170818-20180816171102");
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
        Log.d("lyll","333");
        Log.d("lyl","2.2--isad--"+isad);


    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.d("lyll","555");
        Intent intent=new Intent();
        String data=intent.getStringExtra("isShow");
        //Log.d("lyl","data--"+data);
        mAdapter.refresh();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAdapter.destroy();
    }

    @Override
    public void onItemSelected(UUID id) {
        MainActivity activity = (MainActivity) getActivity();

        RecordDetailActivity.actionStart(activity, id);

        activity.setBlur(activity);
    }

    @Override
    public void setLoading(boolean isLoad) {

    }
}
