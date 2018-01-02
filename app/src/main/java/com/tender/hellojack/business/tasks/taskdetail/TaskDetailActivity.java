package com.tender.hellojack.business.tasks.taskdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.business.tasks.listdetail.EditActionViewHolder;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.ActivityUtils;
import com.tender.tools.utils.UIUtil;

/**
 * Created by boyu
 */
public class TaskDetailActivity extends BaseActivity {

    private TaskDetailFragment contentFragment;

    private EditActionViewHolder editViewHolder;

    private int modle = 0;//0 待编辑； 1 编辑完成

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_model);
    }

    @Override
    protected void initToolbar() {
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra(IntentConst.IRParam.TASK_ITEM_TITLE);
            updateTitle(title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
        if (contentFragment == null) {
            contentFragment = new TaskDetailFragment();
            new TaskDetailPresenter(Injection.provideRepository(), contentFragment, Injection.provideSchedule());
            ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.tender.tools.R.menu.hj_tools_menu_edit_and_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == com.tender.tools.R.id.menu_delete) {
            contentFragment.deleteTask();
            return true;
        } else if (itemId == com.tender.tools.R.id.menu_edit) {
            if (modle == 0) {
                modle = 1;
                item.setIcon(com.tender.tools.R.mipmap.hj_tools_done_black);
                contentFragment.onEditTask();
            } else if (modle == 1){
                modle = 0;
                item.setIcon(com.tender.tools.R.mipmap.hj_tools_edit_black);
                contentFragment.onEditTaskDone();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}