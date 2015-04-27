package com.rubberduck.materialjumpstart.adapters;

/**
 * Created by akshayt on 23/04/15.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubberduck.materialjumpstart.R;
import com.rubberduck.materialjumpstart.model.Dummy;

public class DetailsRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "OfferDetailsRVAdapter";

    private Context context;
    private Dummy currentDummy;
    private View headerView;

    private enum CARD_TYPE {
        HEADER, CARD1, CARD2, CARD3;

        public static CARD_TYPE fromIndex(int i) {
            switch (i) {
                case 0:
                    return HEADER;
                case 1:
                    return CARD1;
                case 2:
                    return CARD2;
                case 3:
                    return CARD3;
                default:
                    return null;
            }
        }
    };

    public DetailsRVAdapter(Context context, Dummy currentDummy, View headerView) {
        this.context = context;
        this.currentDummy = currentDummy;
        this.headerView = headerView;
    }

    @Override
    public int getItemCount() {
        return CARD_TYPE.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        CARD_TYPE viewType = CARD_TYPE.CARD1;

        switch (position) {
            case 0:
                viewType = CARD_TYPE.HEADER;
                break;
            case 1:
                viewType = CARD_TYPE.CARD1;
                break;
            case 2:
                viewType = CARD_TYPE.CARD2;
                break;
            case 3:
                viewType = CARD_TYPE.CARD3;
                break;
            default:
                Log.d(TAG, "Wrong card number");
        }

        return viewType.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup viewGroup;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        CARD_TYPE cardType = CARD_TYPE.fromIndex(viewType);

        switch (cardType) {
            case HEADER:
                return new HeaderViewHolder(headerView);
            case CARD1:
                viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.card1, parent, false);
                return new Card1ViewHolder(viewGroup);
            case CARD2:
                viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.card2, parent, false);
                return new Card2ViewHolder(viewGroup);
            case CARD3:
                viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.card3, parent, false);
                return new Card3ViewHolder(viewGroup);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        /*if (viewHolder instanceof OfferTNCViewHolder) {
            OfferTNCViewHolder offerTNCViewHolder = (OfferTNCViewHolder) viewHolder;
        }*/
        CARD_TYPE cardType = CARD_TYPE.fromIndex(holder.getItemViewType());

        switch (cardType) {
            case CARD1:
                Card1ViewHolder card1ViewHolder =
                        (Card1ViewHolder) holder;
                card1ViewHolder.header.setText(currentDummy.getHeader());
                card1ViewHolder.headerText.setText(currentDummy.getHeaderText());
                card1ViewHolder.imageView.setImageResource
                      (getImageResource(currentDummy.getImageUri()));
                card1ViewHolder.likeCount.setText(String.valueOf(currentDummy.getLikesCount()));
                card1ViewHolder.subHeader.setText(currentDummy.getSubheader());
                card1ViewHolder.subHeaderText.setText(currentDummy.getSubheaderText());

                break;
            case CARD2:
                Card2ViewHolder card2ViewHolder = (Card2ViewHolder) holder;
                break;
            case CARD3:
                Card3ViewHolder card3ViewHolder = (Card3ViewHolder) holder;
                break;
            default:
                break;
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    static class Card1ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView, likeButton;
        TextView header, subHeader, likeCount, headerText, subHeaderText;
        ImageButton like, share, favorite, report;

        Card1ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cv_card1);
            imageView = (ImageView) itemView.findViewById(R.id.iv_small);
            header = (TextView) itemView.findViewById(R.id.tv_card1_header);
            headerText = (TextView) itemView.findViewById(R.id.tv_card1_headerText);
            likeButton = (ImageView) itemView.findViewById(R.id.iv_like);
            likeCount = (TextView) itemView.findViewById(R.id.tv_like_count);
            subHeader = (TextView) itemView.findViewById(R.id.tv_card1_subheader);
            subHeaderText = (TextView) itemView.findViewById(R.id.tv_card1_subheader_text);
            like = (ImageButton) itemView.findViewById(R.id.ib_like);
            share = (ImageButton) itemView.findViewById(R.id.ib_share);
            favorite = (ImageButton) itemView.findViewById(R.id.ib_favorite);
            report = (ImageButton) itemView.findViewById(R.id.ib_report);

        }
    }


    static class Card2ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView header, details, phoneNo;
        ImageButton callIcon;

        Card2ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_card2);
            header = (TextView) itemView.findViewById(R.id.tv_card2_header);
            details = (TextView) itemView.findViewById(R.id.tv_card2_details);
            phoneNo = (TextView) itemView.findViewById(R.id.tv_card2_phone);
            callIcon = (ImageButton) itemView.findViewById(R.id.ib_call);
        }
    }

    static class Card3ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView header, details;

        Card3ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_card3);
            header = (TextView) itemView.findViewById(R.id.tv_card3_header);
            details = (TextView) itemView.findViewById(R.id.tv_card3_details);
        }
    }

    private int getImageResource(String uri) {
        int imageResource = context.getResources().
                getIdentifier(uri, null, context.getPackageName());

        return imageResource;
    }

}