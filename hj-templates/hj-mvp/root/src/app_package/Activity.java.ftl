package ${packageName};

import android.os.Bundle;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.utils.ActivityUtils;

/**
 * Created by boyu
 */
public class ${activityName} extends BaseActivity {

    private ${fragmentName} contentFragment;

    @Override
    protected void initLayout() {
        <#if generateActivityLayout>
                setContentView(R.layout.${activityLayoutName});
        </#if>
        <#if !generateActivityLayout>
                setContentView(R.layout.hj_activity_model);
        </#if>
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       contentFragment = (${fragmentName}) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
               if (contentFragment == null) {
                   contentFragment = new ${fragmentName}();
                   new ${presenterName}(Injection.provideRepository(), contentFragment, Injection.provideSchedule());
                   ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
               }
    }

}