package net.qiujuer.tips.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD

=======
>>>>>>> 11eb2a081577acd721926bde7bd501f1ea47a1be
import com.u3k.app.external.Ad;
import com.u3k.app.external.InterstitialAd;
import com.u3k.app.external.InterstitialAdListener;

import net.qiujuer.genius.ui.widget.FloatActionButton;
import net.qiujuer.tips.Application;
import net.qiujuer.tips.LaunchActivity;
import net.qiujuer.tips.R;
import net.qiujuer.tips.common.drawable.AnimJagDrawable;
import net.qiujuer.tips.factory.cache.Cache;
import net.qiujuer.tips.factory.model.db.ContactModel;
import net.qiujuer.tips.factory.presenter.ContactDetailPresenter;
import net.qiujuer.tips.factory.view.ContactDetailView;
import net.qiujuer.tips.view.adapter.AdapterSelectCallback;
import net.qiujuer.tips.view.adapter.ContactRecordsAdapter;

import java.util.UUID;

public class ContactDetailActivity extends BlurActivity implements ContactDetailView, View.OnClickListener, AdapterSelectCallback {
    private UUID mId;
    private ContactDetailPresenter mPresenter;
    private TextView mContactsName;
    private TextView mContactsPhone;
    private TextView mContactsQq;
    private TextView mContactsRelation;
    private String[] mRelationStr;
    private FloatActionButton mFltAtnBtn,mFltnDeleteBtn;
    private ContactRecordsAdapter mAdapter;
    private int mGender;
<<<<<<< HEAD
    private InterstitialAd interstitial;
=======
>>>>>>> 11eb2a081577acd721926bde7bd501f1ea47a1be

    private View mTop;

    public static void actionStart(Context context, UUID id) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        if (id != null)
            intent.putExtra("Id", id.toString());
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        mPresenter.refresh();
        mAdapter.refresh();
        super.onResume();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_contacts_detail;
    }

    @Override
    protected void onInitToolBar() {
        //
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            String id = intent.getStringExtra("Id");
            mId = UUID.fromString(id);
            Log.d("lyl","UUID--mid"+mId.toString());
        }
        mPresenter = new ContactDetailPresenter(this);
        mRelationStr = getResources().getStringArray(R.array.array_contacts_relations);

        // InitList
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_records);
        View view = findViewById(R.id.text_status);
        mAdapter = new ContactRecordsAdapter(mId, recyclerView, view, this);

        // FindId
        mContactsName = (TextView) findViewById(R.id.contacts_detail_txt_brief_name);
        mContactsPhone = (TextView) findViewById(R.id.contacts_detail_txt_phone);
        mContactsQq = (TextView) findViewById(R.id.contacts_detail_txt_QQ);
        mContactsRelation = (TextView) findViewById(R.id.contacts_detail_txt_relation);
        mFltAtnBtn = (FloatActionButton) findViewById(R.id.contacts_Detail_btn_to_edit);
        mFltnDeleteBtn= (FloatActionButton) findViewById(R.id.contacts_Detail_btn_to_delete);
        mTop = findViewById(R.id.lay_top);
        // Set onClick
        mFltAtnBtn.setOnClickListener(this);
        mFltnDeleteBtn.setOnClickListener(this);

        AnimJagDrawable drawable = new AnimJagDrawable();
        drawable.setFluCount(new Rect(0, 0, 0, 36));

        mTop.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mTop.setBackgroundDrawable(drawable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Refresh
        if (!mPresenter.refresh())
            finish();
        else {
            mAdapter.refresh();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.destroy();
        mAdapter = null;
    }

    @Override
    public void setNameStr(String name) {
        mContactsName.setText(name);
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            mContactsPhone.setVisibility(View.GONE);
        } else {
            String phone = getResources().getString(R.string.txt_contacts_detail_phone);
            phone = String.format(phone, phoneNumber);
            mContactsPhone.setText(phone);
            mContactsPhone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setQQ(String qq) {
        if (TextUtils.isEmpty(qq)) {
            mContactsQq.setVisibility(View.GONE);
        } else {
            String qqNumber = getResources().getString(R.string.txt_contacts_detail_qq);
            qqNumber = String.format(qqNumber, qq);
            mContactsQq.setText(qqNumber);
            mContactsQq.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setSex(int isMan) {
        mGender = isMan;
        Drawable drawable;
        int id;
        if (isMan == 1) {
            id = R.mipmap.ic_contact_gender_man;
        } else {
            id = R.mipmap.ic_contact_gender_woman;
        }
        drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, 3 * drawable.getMinimumWidth() / 2, 3 * drawable.getMinimumHeight() / 2);
        mContactsName.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void setRelation(int relation) {
        // "1" is man
        int mark;
        if (mGender == 1) {
            mark = relation + 6;
        } else {
            if (relation > 6) {
                mark = relation + 6;
            } else {
                mark = relation;
            }
        }
        String rlt = getResources().getString(R.string.txt_contacts_detail_relation);
        rlt = String.format(rlt, mRelationStr[mark]);
        mContactsRelation.setText(rlt);
    }

    @Override
    public UUID getId() {
        return mId;
    }

    @Override
    public void setDetailStatus(boolean status) {
        if (status) {
            Toast.makeText(this, R.string.status_opt_delete_ok, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.status_opt_delete_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setColor(int color) {
        AnimJagDrawable drawable = (AnimJagDrawable) mTop.getBackground();
        if (color != drawable.getColor()) {
            drawable.setColorUnInvalidate(color);
            drawable.setAlpha(164);
            drawable.startAnim();
        }
    }

    @Override
    public void onClick(View v) {
        final int viewID = v.getId();
        if (viewID == R.id.contacts_Detail_btn_to_edit) {
            ContactEditActivity.actionStart(ContactDetailActivity.this, mId);
            setBlur(ContactDetailActivity.this);
            Log.e("lyll","ContactDetailActivity start load ad");
<<<<<<< HEAD
            interstitial= new InterstitialAd(LaunchActivity.mAppIdKey,ContactDetailActivity.this, "u3k-1180816000032170818-20180816171102");
=======
            InterstitialAd interstitial= new InterstitialAd(LaunchActivity.token,ContactDetailActivity.this, "u3k-1180816000032170818-20180816171108");
>>>>>>> 11eb2a081577acd721926bde7bd501f1ea47a1be
            interstitial.setAdListener(new InterstitialAdListener() {
                @Override
                public void onError(Ad ad, int i, String s) {
                    Log.e("lyll","onError ad--"+ad+"  i--"+i+"  s--"+s);
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    Log.e("lyll","onAdLoaded ad--"+ad);
<<<<<<< HEAD
                    interstitial.show();
                    Log.e("lyll","onAdLoaded ad show--"+interstitial.show());
=======
>>>>>>> 11eb2a081577acd721926bde7bd501f1ea47a1be
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
        }else if (viewID== R.id.contacts_Detail_btn_to_delete){
            showDialog(ContactDetailActivity.this, R.string.txt_status_delete_notes, null, null, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(ContactDetailActivity.this,"balabala",Toast.LENGTH_LONG).show();
                    ContactModel model = new ContactModel();
                    model.setMark(mId);
                    Log.d("lyl","model--"+model.toString());
                    Cache.getInstance().delete(model);
                    finish();
                }
            }).show();
/*            Toast.makeText(ContactDetailActivity.this,"balabala",Toast.LENGTH_LONG).show();
            ContactModel model = new ContactModel();
            model.setMark(mId);
            Log.d("lyl","model--"+model.toString());
            Cache.getInstance().delete(model);
            finish();*/
        }
    }

    @Override
    public void onItemSelected(UUID id) {
        RecordDetailActivity.actionStart(this, id);
        this.setBlur(this);
    }

    @Override
    public void setLoading(boolean isLoad) {

    }
}
