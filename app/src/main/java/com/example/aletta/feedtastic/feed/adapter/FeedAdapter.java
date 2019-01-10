package com.example.aletta.feedtastic.feed.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aletta.feedtastic.R;
import com.example.aletta.feedtastic.api.model.ComicData;
import com.example.aletta.feedtastic.util.GlideApp;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ComicViewHolder> {


    private List<ComicData> mData;
    private ItemClickListener mClickListener;
    private Context context;
    private StorageReference storageReference;
    private OpenDetailListener openDetailedPageListener;


    public FeedAdapter(Context context, List<ComicData> data) {
        this.mData = data;
        this.context = context;
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, null);
        return new ComicViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ComicViewHolder holder, int position) {
        final ComicData ad = mData.get(position);
        holder.adName.setText(ad.getTitle());
        holder.adShortDesc.setText(ad.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailedPage(ad);
            }
        });
        holder.adShortDesc.setText(ad.getDescription());
        holder.adName.setText(ad.getTitle());
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeAd(holder.like, ad);
            }
        });


        GlideApp.with(holder.itemView.getContext())
                .load(ad.getComicImage().getImagPathFul())
                .into(holder.adPhoto);
    }

    private void likeAd(ImageView like, ComicData ad) {

        if (ad.isLiked()) {
            GlideApp.with(like.getContext())
                    .load(R.drawable.like)
                    .into(like);
        } else {
            GlideApp.with(like.getContext())
                    .load(R.drawable.liked)
                    .into(like);
        }
        ad.setLiked(!ad.isLiked());
    }

    private void openDetailedPage(ComicData ad) {

        openDetailedPageListener.onOpen(ad);

    }

    public void setOpenDetailedPageListener(OpenDetailListener openDetailedPageListener) {
        this.openDetailedPageListener = openDetailedPageListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    String getItem(int id) {
        return String.valueOf(mData.get(id));
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.seen_number)
        TextView seenNumber;
        @BindView(R.id.ad_photo)
        ImageView adPhoto;
        @BindView(R.id.ad_name)
        TextView adName;
        @BindView(R.id.ad_short_desc)
        TextView adShortDesc;
        @BindView(R.id.seen)
        ImageView seen;
        @BindView(R.id.like)
        ImageView like;
        @BindView(R.id.report)
        ImageView report;
        @BindView(R.id.card_view)
        CardView cardView;

        ComicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}