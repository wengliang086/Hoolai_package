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

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> products;
    private Context mContext;

    public ProductAdapter(Context context, List<Product> products) {
        this.mContext = context;
        this.products = products;
    }

    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.MyViewHolder holder, final int position) {
        final Product product = products.get(position);
        holder.id.setText(product.getProductId() + "");
        holder.name.setText(product.getProductName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("productId", product.getProductId());
                intent.putExtra("product", product);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
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
