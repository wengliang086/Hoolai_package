package com.android.hoolai.pack.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hoolai.pack.MainActivity;
import com.android.hoolai.pack.R;
import com.android.hoolai.pack.domain.Product;
import com.android.hoolai.pack.domain.Version;
import com.android.hoolai.pack.utils.T;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class VersionAdapter extends RecyclerView.Adapter<VersionAdapter.MyViewHolder> {

    private List<Version> versionList;
    private Context mContext;

    public VersionAdapter(Context context, List<Version> versions) {
        this.mContext = context;
        this.versionList = versions;
    }

    @Override
    public VersionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_version, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VersionAdapter.MyViewHolder holder, final int position) {
        Version version = versionList.get(position);
        holder.id.setText(version.getId() + "");
        holder.name.setText(version.getVersionName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T.show(mContext, position + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return versionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView id;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            id = (TextView) itemView.findViewById(R.id.id_p_id);
            name = (TextView) itemView.findViewById(R.id.id_p_name);
        }
    }
}
