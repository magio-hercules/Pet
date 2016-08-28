package magio.ohmypet.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ScrollView;

import magio.ohmypet.Fragment.CustomMapFragment;
import magio.ohmypet.R;

/**
 * Created by mini on 2016-08-27.
 */
public class AdoptDetailActivity extends AppCompatActivity {


    ScrollView scrollViewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_detail);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initActionBar();

        initMap();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();

//        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setTitle(R.string.adopt_detail);
    }

    public void initMap() {
        scrollViewDetail = (ScrollView)findViewById(R.id.adopt_detail_scroll);

        // Google map init block
        CustomMapFragment customMapFragmentDetail = ((CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.adopt_detail_map));
        customMapFragmentDetail.setOnTouchListener(new CustomMapFragment.OnTouchListener(){
            @Override
            public void onTouch() {
                scrollViewDetail.requestDisallowInterceptTouchEvent(true);
            }
        });
    }
}
