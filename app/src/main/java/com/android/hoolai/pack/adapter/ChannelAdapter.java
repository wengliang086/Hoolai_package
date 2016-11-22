package com.android.hoolai.pack.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hoolai.pack.R;
import com.android.hoolai.pack.domain.Channel;
import com.android.hoolai.pack.utils.T;
import com.gc.materialdesign.views.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.MyViewHolder> {

    private List<Channel> channelList;
    private Context mContext;
    private List<Integer> isSelected = new ArrayList<>();

    public ChannelAdapter(Context context, List<Channel> channels) {
        this.mContext = context;
        this.channelList = channels;
    }

    @Override
    public ChannelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_channel, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChannelAdapter.MyViewHolder holder, final int position) {
        Channel channel = channelList.get(position);
        holder.id.setText(channel.getId() + "");
        holder.name.setText(channel.getChannel());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T.show(mContext, position + "");
            }
        });
        holder.checkBox.setOncheckListener(new CheckBox.OnCheckListener() {
            @Override
            public void onCheck(CheckBox view, boolean check) {
                if (check) {
                    isSelected.add(position);
                } else {
                    isSelected.remove(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public int getFirstCheckedChannelId() {
        if (isSelected.size() == 0) {
            return -1;
        }
        Integer pos = isSelected.get(0);
        return channelList.get(pos).getId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView id;
        TextView name;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            id = (TextView) itemView.findViewById(R.id.id_p_id);
            name = (TextView) itemView.findViewById(R.id.id_p_name);
            checkBox = (CheckBox) itemView.findViewById(R.id.id_select_all);
        }
    }
}
