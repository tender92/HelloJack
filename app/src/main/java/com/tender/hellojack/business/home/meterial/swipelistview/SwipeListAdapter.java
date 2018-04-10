package com.tender.hellojack.business.home.meterial.swipelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.tools.utils.ui.UIUtil;

import java.util.List;

/**
 * Created by boyu on 2018/4/10.
 */

public class SwipeListAdapter extends BaseAdapter {

    private List<CardTransBean> transBeanList;
    private Context mContext;

    public SwipeListAdapter(List<CardTransBean> transBeanList, Context mContext) {
        this.transBeanList = transBeanList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return transBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return transBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.hj_tools_swipe_list_item, null, false);
            bindView(convertView, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCardNo.setText(transBeanList.get(position).getCardNo());
        viewHolder.tvTransMoney.setText(transBeanList.get(position).getTransMoney());
        viewHolder.tvTransTime.setText(transBeanList.get(position).getTransTime());
        viewHolder.tvTransStatus.setText(transBeanList.get(position).getTransStatus());
        viewHolder.tvTransStatus.setTextColor("成功".equals(transBeanList.get(position).getTransStatus()) ?
                UIUtil.getColor(R.color.hj_tools_gray16) : UIUtil.getColor(R.color.hj_tools_red1));
        viewHolder.tvBackOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transBeanList.get(position).getRunnableRight().run();
            }
        });
        viewHolder.tvBackTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transBeanList.get(position).getRunnableLeft().run();
            }
        });
        viewHolder.llTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transBeanList.get(position).getRunnableItem().run();
            }
        });
        return convertView;
    }

    private void bindView(View convertView, ViewHolder holder) {
        holder.tvCardNo = (TextView) convertView.findViewById(R.id.tv_swipe_list_item_card_no);
        holder.tvTransMoney = (TextView) convertView.findViewById(R.id.tv_swipe_list_item_money);
        holder.tvTransTime = (TextView) convertView.findViewById(R.id.tv_swipe_list_item_time);
        holder.tvTransStatus = (TextView) convertView.findViewById(R.id.tv_swipe_list_item_status);
        holder.tvBackOne = (TextView) convertView.findViewById(R.id.tv_swipe_list_one);
        holder.tvBackTwo = (TextView) convertView.findViewById(R.id.tv_swipe_list_two);
        holder.llTrans = (LinearLayout) convertView.findViewById(R.id.ll_swipe_list_trans);
        convertView.setTag(holder);
    }

    private class ViewHolder {
        public TextView tvCardNo;
        public TextView tvTransMoney;
        public TextView tvTransTime;
        public TextView tvTransStatus;
        public TextView tvBackOne;
        public TextView tvBackTwo;
        public LinearLayout llTrans;
    }
}
