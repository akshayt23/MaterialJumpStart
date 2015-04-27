package com.rubberduck.materialjumpstart.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubberduck.materialjumpstart.R;
import com.rubberduck.materialjumpstart.activities.DetailsActivity;
import com.rubberduck.materialjumpstart.model.Dummy;
;

import java.util.List;

/**
 * Created by akshayt on 19/04/15.
 */
public class MainRVAdapter extends
        RecyclerView.Adapter<MainRVAdapter.MainViewHolder> {

    public static final String TAG = "OffersRVAdapter";

    private Context context;
    private List<Dummy> dummyList;

    public MainRVAdapter(Context context, List<Dummy> dummyList) {
        this.context = context;
        this.dummyList = dummyList;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.main_card, parent, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Dummy currentDummy = dummyList.get(position);

        holder.header.setText(currentDummy.getHeader());
        holder.headerText.setText(currentDummy.getHeaderText());
        holder.subHeader.setText(currentDummy.getSubheader());
        holder.subHeaderText.setText(currentDummy.getSubheaderText());
        holder.imageView.setImageResource(getImageResource(currentDummy.getImageUri()));

        holder.currentDummy = currentDummy;

        //Set Top Margin for first card (XML only has bottom margin for cards)
        if (position == 0) {
            LayoutParams params = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );
            params.setMargins(getPxfromDp(12), getPxfromDp(12), getPxfromDp(12), getPxfromDp(12));
            holder.cardView.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return dummyList.size();
    }

    private int getImageResource(String uri) {
        int imageResource = context.getResources().
                getIdentifier(uri, null, context.getPackageName());

        return imageResource;
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        Dummy currentDummy;

        CardView cardView;
        TextView header;
        TextView headerText;
        TextView subHeader;
        TextView subHeaderText;
        ImageView imageView;

        MainViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_main);
            header = (TextView) itemView.findViewById(R.id.tv_header);
            headerText = (TextView) itemView.findViewById(R.id.tv_header_text);
            subHeader = (TextView) itemView.findViewById(R.id.tv_subheader);
            subHeaderText = (TextView) itemView.findViewById(R.id.tv_subheader_text);
            imageView = (ImageView) itemView.findViewById(R.id.iv_small);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick!");

                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("dummyObj", currentDummy);
                    context.startActivity(intent);
                }
            });
        }
    }

    private int getPxfromDp (int dp) {
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );

        return px;
    }
}
