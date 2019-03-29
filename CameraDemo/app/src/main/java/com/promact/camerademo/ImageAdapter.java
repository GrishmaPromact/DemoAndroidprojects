package com.promact.camerademo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by grishma on 24-01-2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageBindViewHolder> {
    private Context context;
    private List<ImageModel> imageList;


    public ImageAdapter(Context context, List<ImageModel> imageModelList) {
        this.context = context;
        this.imageList = imageModelList;
    }

    @Override
    public ImageBindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_row, parent, false);
        return new ImageBindViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageBindViewHolder holder, int position) {
        bindData(holder, holder.getLayoutPosition());
    }

    private void bindData(ImageBindViewHolder holder, int position) {
        ImageModel imageModel = imageList.get(position);
        Glide.with(context)
                .load(imageModel.getImagePath())
                .override(100, 200)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        //imageView.setImageURI(Uri.parse(imageModel.getImagePath()));
        holder.imagePath.setText(imageModel.getImagePath());
    }

    @Override
    public int getItemCount() {
        return imageList.size(); // change this name to your list
    }

    public void setItems(List<ImageModel> imageModelList) {
        for (int i = 0; i < imageModelList.size(); i++) {
            imageList.add(imageModelList.get(i));
            notifyDataSetChanged();
        }
    }

    public class ImageBindViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView imagePath;

        public ImageBindViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            imagePath = (TextView) itemView.findViewById(R.id.textview);
        }

    }

}
