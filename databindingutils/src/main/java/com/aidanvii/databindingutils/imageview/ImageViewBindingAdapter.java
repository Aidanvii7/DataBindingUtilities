package com.aidanvii.databindingutils.imageview;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.vmn.playplex.ui.aspectratio.AspectRatioAppCompatImageView;
import com.vmn.playplex.ui.aspectratio.AspectRatioGIFView;
import com.vmn.playplex.ui.aspectratio.AspectRatioImageView;

/**
 * Created by aidan.mcwilliams@vimn.com on 18/10/16.
 * <p> TODO perhaps change the name of this class as it clashes with {@link android.databinding.adapters.ImageViewBindingAdapter}</p>
 */
public interface ImageViewBindingAdapter {

    @BindingAdapter({"bind_srcUrlCenterCrop"})
    void loadImageCenterCrop(ImageView imageView, String imageUrl);

    /**
     * Loads an image into an {@link ImageView} and center crops it
     *
     * @param aspectImageView the {@link AspectRatioAppCompatImageView} where the image will be loaded
     * @param imageUrl        the url of the image
     * @param aspectRatio     the aspectRatio of the image
     */
    @BindingAdapter({"bind_srcUrlCenterCrop", "bind_aspectRatio"})
    void loadImageCenterCrop(AspectRatioAppCompatImageView aspectImageView, String imageUrl, float aspectRatio);


    /**
     * Loads an image into an {@link ImageView} and center crops it
     *
     * @param aspectImageView the {@link AspectRatioImageView} where the image will be loaded
     * @param imageUrl        the url of the image
     * @param aspectRatio     the aspectRatio of the image
     */
    @BindingAdapter({"bind_srcUrlCenterCrop", "bind_aspectRatio"})
    void loadImageCenterCrop(AspectRatioImageView aspectImageView, String imageUrl, float aspectRatio);

    /**
     * Loads an image into an {@link ImageView} and center crops it
     *
     * @param aspectImageView the {@link AspectRatioImageView} where the image will be loaded
     * @param imageUrl        the url of the image
     * @param aspectRatio     the aspectRatio of the image
     */
    @BindingAdapter({"bind_srcUrlCenterCrop", "bind_aspectRatio"})
    void loadImageCenterCrop(AspectRatioGIFView aspectImageView, String imageUrl, float aspectRatio);
}