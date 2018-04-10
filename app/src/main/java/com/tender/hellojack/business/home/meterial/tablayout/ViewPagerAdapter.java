package com.tender.hellojack.business.home.meterial.tablayout;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tender.hellojack.R;

import java.util.List;

/**
 * Created by boyu on 2018/3/27.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<String> titles;
    private Context context;

    public ViewPagerAdapter(Context context, List<String> titles) {
        this.context = context;
        this.titles = titles;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(container.getContext())
                .inflate(R.layout.hj_fragment_tab_layout_item, container, false);
        container.addView(viewGroup);
        TextView tvContent = (TextView) viewGroup.findViewById(R.id.tv_tab_layout);
        tvContent.setText(titles.get(position));
        if (position == 2) {
            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((TabLayoutActivity)context).showBottomSheetDialog();
                }
            });
        }
        return viewGroup;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
