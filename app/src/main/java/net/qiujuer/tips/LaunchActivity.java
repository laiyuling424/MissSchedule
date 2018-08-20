package net.qiujuer.tips;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.u3k.app.external.Ad;
import com.u3k.app.external.InterstitialAd;
import com.u3k.app.external.InterstitialAdListener;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.tips.factory.presenter.AppPresenter;
import net.qiujuer.tips.view.activity.BaseActivity;
import net.qiujuer.tips.view.activity.GuideActivity;
import net.qiujuer.tips.view.activity.MainActivity;
import net.qiujuer.tips.view.util.AnimationListener;

import java.util.Timer;
import java.util.TimerTask;


public class LaunchActivity extends BaseActivity {
    private int mDoneCount = 0;
    private boolean mAlreadySkip = false;
    private LinearLayout contentAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        iconIn();

        Thread thread = new Thread("GraveTips-Launch-InitThread") {
            @Override
            public void run() {
                Application application = (Application) getApplication();
                application.init();
                skipOnDone();
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void iconIn() {
        skipOnDone();
        Log.e("lyll","LaunchActivity start load ad");
        Log.e("lyll","token--"+ Application.token);
        final String token=Application.token;
        final Context context=getApplicationContext();
        final Context cot=LaunchActivity.this;
        Log.e("lyll","context--"+ context.toString());
        Log.e("lyll","context--"+ cot.toString());
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final InterstitialAd interstitial= new InterstitialAd(token,cot, "u3k-1180816000032170818-20180816171101");
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
                });
            }
        },3000);

/*        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_launch_item_scale_in);
        anim.setStartOffset(480);
        anim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);

                Animation anim = AnimationUtils.loadAnimation(LaunchActivity.this, R.anim.anim_launch_item_scale_in);
                anim.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        skipOnDone();
                    }
                });
                View view = findViewById(R.id.launch_icon);
                view.setVisibility(View.INVISIBLE);
                view.startAnimation(anim);
            }
        });
        findViewById(R.id.content).startAnimation(anim);*/
    }

    private void skipOnDone() {
        if (mAlreadySkip || ((++mDoneCount) < 2))
            return;

        mAlreadySkip = true;

        Run.getUiHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                skipByDelay();
            }
        }, 5000);

    }

    private void skipByDelay() {
        Intent intent;
        if (AppPresenter.isFirstUse()) {
            Toast.makeText(this, R.string.app_welcome, Toast.LENGTH_LONG).show();
            intent = new Intent(this, GuideActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
