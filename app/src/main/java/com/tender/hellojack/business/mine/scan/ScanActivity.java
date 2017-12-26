package com.tender.hellojack.business.mine.scan;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.utils.ActivityUtils;

/**
 * Created by boyu
 */
public class ScanActivity extends BaseActivity {

    private ScanFragment contentFragment;

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_model);
    }

    @Override
    protected void initToolbar() {
        updateTitle("扫一扫");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentFragment = (ScanFragment) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
        if (contentFragment == null) {
            contentFragment = new ScanFragment();
            new ScanPresenter(Injection.provideRepository(), contentFragment, Injection.provideSchedule());
            ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(com.tender.tools.R.menu.hj_tools_menu_more, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (android.R.id.home == itemId) {
           finish();
        } else if (com.tender.tools.R.id.menu_item_more == itemId) {
            contentFragment.showPopUpMenu();
        }
        return super.onOptionsItemSelected(item);
    }

}