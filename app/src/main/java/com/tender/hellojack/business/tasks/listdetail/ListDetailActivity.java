package com.tender.hellojack.business.tasks.listdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.ActivityUtils;

/**
 * Created by boyu
 */
public class ListDetailActivity extends BaseActivity {

    private ListDetailFragment contentFragment;

    private EditActionViewHolder editViewHolder;

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_model);
    }

    @Override
    protected void initToolbar() {

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra(IntentConst.IRParam.List_ITEM_TITLE);
            updateTitle(title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentFragment = (ListDetailFragment) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
        if (contentFragment == null) {
            contentFragment = new ListDetailFragment();
            new ListDetailPresenter(Injection.provideRepository(), contentFragment, Injection.provideSchedule());
            ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.tender.tools.R.menu.hj_tools_menu_edit_title_and_delete, menu);

        MenuItem editItem = menu.findItem(com.tender.tools.R.id.menu_edit);
        final View actionView = editItem.getActionView();
        editViewHolder = new EditActionViewHolder(actionView, editItem);
        MenuItemCompat.setOnActionExpandListener(editItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                editViewHolder.showCurrentTitle(contentFragment.getListTitle());
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                contentFragment.setListTitle(editViewHolder.getCurrentText());
                updateTitle(editViewHolder.getCurrentText());
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == com.tender.tools.R.id.menu_delete) {
            contentFragment.deleteList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}