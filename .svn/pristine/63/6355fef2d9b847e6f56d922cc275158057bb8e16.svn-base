package net.qiujuer.tips.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.tips.R;
import net.qiujuer.tips.factory.presenter.AppPresenter;

import java.util.Timer;
import java.util.TimerTask;

public class SpreadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spread);
        final Intent intent0=new Intent(this,GuideActivity.class);
        final Intent intent1=new Intent(this,MainActivity.class);
/*        Timer timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
                if (AppPresenter.isFirstUse()) {
                    startActivity(intent0);
                } else {
                    startActivity(intent1);
                }
            }
        };
        timer.schedule(task,5000);*/
        Run.getUiHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                skipByDelay();
            }
        }, 3000);
    }
    public void skipByDelay(){
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
