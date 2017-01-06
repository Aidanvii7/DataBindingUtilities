package com.aidanvii.databindingutils.imageview;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vmn.playplex.ui.aspectratio.AspectRatioAppCompatImageView;
import com.vmn.playplex.ui.aspectratio.AspectRatioGIFView;
import com.vmn.playplex.ui.aspectratio.AspectRatioImageView;
import com.vmn.playplex.ui.aspectratio.AspectRatioView;

import org.jetbrains.annotations.NotNull;

/**
 * Created by aidan.mcwilliams@vimn.com on 18/11/16.
 */
public final class GlideImageViewBindingAdapter implements ImageViewBindingAdapter {

    @Override
    public void loadImageCenterCrop(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void loadImageCenterCrop(AspectRatioAppCompatImageView aspectImageView, String imageUrl, float aspectRatio) {
        loadImageCenterCropInternal(aspectImageView, imageUrl, aspectRatio);
    }

    @Override
    public void loadImageCenterCrop(AspectRatioImageView aspectImageView, String imageUrl, float aspectRatio) {
        loadImageCenterCropInternal(aspectImageView, imageUrl, aspectRatio);
    }

    @Override
    public void loadImageCenterCrop(AspectRatioGIFView aspectImageView, String imageUrl, float aspectRatio) {
        loadGifCenterCropInternal(aspectImageView, imageUrl, aspectRatio);
    }

    private <AspectImageView extends ImageView & AspectRatioView>
    void loadImageCenterCropInternal(@NotNull AspectImageView aspectImageView, @NotNull String imageUrl, float aspectRatio) {
        aspectImageView.setAspectRatio(aspectRatio);
        Glide.with(aspectImageView.getContext())
                .load(imageUrl)
                .centerCrop()
                .into(aspectImageView);
    }

    private <AspectImageView extends ImageView & AspectRatioView>
    void loadGifCenterCropInternal(@NotNull AspectImageView aspectImageView, @NotNull String imageUrl, float aspectRatio) {
        aspectImageView.setAspectRatio(aspectRatio);
        Glide.with(aspectImageView.getContext())
                .load(imageUrl)
                .asGif()
                .centerCrop()
                .into(aspectImageView);
    }
}
