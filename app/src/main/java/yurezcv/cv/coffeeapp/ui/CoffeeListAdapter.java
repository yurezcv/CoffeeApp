package yurezcv.cv.coffeeapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import yurezcv.cv.coffeeapp.R;
import yurezcv.cv.coffeeapp.Utils;
import yurezcv.cv.coffeeapp.types.Coffee;

public class CoffeeListAdapter extends BaseAdapter {

    private Context mContext;

    private final LayoutInflater mInflater;

    private List<Coffee> mData;

    public CoffeeListAdapter(Context context, List<Coffee> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Coffee getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;
        ViewHolder holder;
        // create UI item or get a reference of already existing one
        if (convertView == null) {
            v = mInflater.inflate(R.layout.item_coffee_list, parent, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            v = convertView;
            holder = (ViewHolder) v.getTag();
        }
        // bind data to UI
        Coffee item = mData.get(position);
        holder.tvTitle.setText(item.getName());
        holder.tvDetails.setText(item.getDesc());
        if (Utils.isEmpty(item.getImageURL())) {
            holder.ivImage.setVisibility(View.GONE);
        } else {
            holder.ivImage.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(item.getImageURL())
                    .into(holder.ivImage);
        }
        return v;
    }

    private static class ViewHolder {

        protected TextView tvTitle;
        protected TextView tvDetails;
        protected ImageView ivImage;

        public ViewHolder(View view) {
            tvTitle = (TextView) view.findViewById(R.id.tvListItemTitle);
            tvDetails = (TextView) view.findViewById(R.id.tvListItemDetails);
            ivImage = (ImageView) view.findViewById(R.id.ivListItemImage);
        }

    }
}
