package com.android.thoughtworks.flows;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.thoughtworks.R;
import com.android.thoughtworks.model.King;
import com.android.thoughtworks.utils.UIUtils;

import java.util.List;

import static com.android.thoughtworks.flows.KingDetailActivity.KING_EXTRA;

/**
 * Displays results of Kings in list.
 */
public class KingsListAdapter extends RecyclerView.Adapter<KingsListAdapter.ViewHolder> {

    private Context mContext;
    private List<King> mList;
    private final Object objectLock = new Object();

    public KingsListAdapter(Context context, List<King> kings) {
        this.mContext = context;
        this.mList = kings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_king, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final King king = mList.get(position);
        String name = king.getName();
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        holder.itemName.setText(name.length() > 0 ? name : "No One");
        holder.itemRating.setText("Highest Rating : " + ((int) king.getRating()));
        holder.itemStrength.setText("Battle Strength : " + king.getStrength());
        holder.itemImage.setImageDrawable(UIUtils.tintImage(ContextCompat.getDrawable(mContext, R.drawable.king_crown), king.getColor()));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        TextView itemName;
        TextView itemRating;
        TextView itemStrength;
        ImageView itemImage;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            itemImage = (ImageView) view.findViewById(R.id.item_image);
            itemName = (TextView) view.findViewById(R.id.item_name);
            itemRating = (TextView) view.findViewById(R.id.item_rating);
            itemStrength = (TextView) view.findViewById(R.id.item_strength);
        }

        @Override
        public void onClick(View view) {
            int position = (int) itemView.getTag();
            King king = mList.get(position);
            Intent intent = new Intent(mContext, KingDetailActivity.class);
            intent.putExtra(KING_EXTRA, king);
            mContext.startActivity(intent);
        }
    }

}
