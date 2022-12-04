package com.unitapplications.postmakerwithunsplashapi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unitapplications.postmakerwithunsplashapi.MainActivity;
import com.unitapplications.postmakerwithunsplashapi.Models.ImageModel;
import com.unitapplications.postmakerwithunsplashapi.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context context ;
    private ArrayList<ImageModel> imageModelArrayList;

    public ImageAdapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        ImageModel model = imageModelArrayList.get(position);
        String image_url = model.getUrls().getRegular();
        //String image_url = imageModelArrayList.get(position).getUrls().getRegular();
        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.sample_image_ic)
                .into(holder.imageView2);

        holder.imageView2.setOnClickListener(view -> {
            sendTo(model.getUrls().getRegular());
        });

    }

    public void sendTo(String url) {
        //send to read sends datat to read activity
        Intent intent;
        intent = new Intent(context, MainActivity.class);
        intent.putExtra("url_key", url);
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView2 ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView2 = itemView.findViewById(R.id.imageView2);
        }
    }
}