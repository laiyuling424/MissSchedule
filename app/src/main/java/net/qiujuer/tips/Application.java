package net.qiujuer.tips;


import android.app.Activity;


import com.u3k.app.SdkMain;

import net.qiujuer.tips.factory.cache.Cache;
import net.qiujuer.tips.factory.model.Model;
import net.qiujuer.tips.factory.presenter.AppPresenter;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

<<<<<<< HEAD
public class Application extends android.app.Application {
    private List<Activity> mActivities = new ArrayList<Activity>();
    public static String mAppIdKey;
=======
public class Application extends AnalyticsApplication {
    private List<Activity> mActivities = new ArrayList<Activity>();
    public static String token;
>>>>>>> 11eb2a081577acd721926bde7bd501f1ea47a1be

    @Override
    public void onCreate() {
        super.onCreate();
<<<<<<< HEAD
        mAppIdKey = SdkMain.init(this, "1180816000032170818", "notFb");

=======

        token= SdkMain.init(getApplicationContext(), "1180816000032170818", "notFb");//1180816000032170818
>>>>>>> 11eb2a081577acd721926bde7bd501f1ea47a1be
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/FZLanTingHeiS-L-GB-Regular.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Model.setApplication(this);
    }

    public void init() {
        AppPresenter.setApplication(this);
        Cache.init();
    }



    public void dispose() {
        AppPresenter.dispose();

        // Model
        Model.dispose();
    }

    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        mActivities.remove(activity);
    }

    public void exit() {
        Cache.destroy();
        for (Activity activity : mActivities) {
            if (!activity.isFinishing())
                activity.finish();
        }
    }
}
