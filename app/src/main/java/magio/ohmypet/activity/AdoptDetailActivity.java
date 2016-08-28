package magio.ohmypet.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import magio.ohmypet.R;

/**
 * Created by mini on 2016-08-27.
 */
public class AdoptDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_detail);

        setCustomActionbar();
    }


    private void setCustomActionbar() {
        ActionBar actionBar = getSupportActionBar();

//        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

//        View customView = LayoutInflater.from(this).inflate(R.layout.actionbar_adoption, null);
//        actionBar.setCustomView(customView);
//
//        Toolbar parent = (Toolbar)customView.getParent();
//        parent.setContentInsetsAbsolute(30, 0);
//
////        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.a));
//
//        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
//                ActionBar.LayoutParams.WRAP_CONTENT);
//        actionBar.setCustomView(customView, params);
    }
}
