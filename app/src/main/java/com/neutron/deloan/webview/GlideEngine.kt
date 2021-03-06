package com.neutron.deloan.webview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.ImageViewTarget

import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.listener.OnImageCompleteCallback
import com.luck.picture.lib.tools.MediaUtils
import com.luck.picture.lib.widget.longimage.ImageSource
import com.luck.picture.lib.widget.longimage.ImageViewState
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView
import com.neutron.deloan.R

class GlideEngine private constructor() : ImageEngine {
    override fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    override fun loadImage(
        context: Context, url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView, callback: OnImageCompleteCallback?
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : ImageViewTarget<Bitmap?>(imageView) {
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    if (callback != null) {
                        callback.onShowLoading()
                    }
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    if (callback != null) {
                        callback.onHideLoading()
                    }
                }

                protected override fun setResource(resource: Bitmap?) {
                    if (callback != null) {
                        callback.onHideLoading()
                    }
                    if (resource != null) {
                        val eqLongImage: Boolean = MediaUtils.isLongImg(
                            resource.getWidth(),
                            resource.getHeight()
                        )
                        longImageView.setVisibility(if (eqLongImage) View.VISIBLE else View.GONE)
                        imageView.visibility = if (eqLongImage) View.GONE else View.VISIBLE
                        if (eqLongImage) {
                            // ????????????
                            longImageView.setQuickScaleEnabled(true)
                            longImageView.setZoomEnabled(true)
                            longImageView.setDoubleTapZoomDuration(100)
                            longImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                            longImageView.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
                            longImageView.setImage(
                                ImageSource.bitmap(resource),
                                ImageViewState(0F, PointF(0F, 0F), 0)
                            )
                        } else {
                            // ????????????
                            imageView.setImageBitmap(resource)
                        }
                    }
                }
            })
    }



    override  fun loadImage(
        context: Context, url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : ImageViewTarget<Bitmap?>(imageView) {
                protected override fun setResource(resource: Bitmap?) {
                    if (resource != null) {
                        val eqLongImage: Boolean = MediaUtils.isLongImg(
                            resource.getWidth(),
                            resource.getHeight()
                        )
                        longImageView.setVisibility(if (eqLongImage) View.VISIBLE else View.GONE)
                        imageView.visibility = if (eqLongImage) View.GONE else View.VISIBLE
                        if (eqLongImage) {
                            // ????????????
                            longImageView.setQuickScaleEnabled(true)
                            longImageView.setZoomEnabled(true)
                            longImageView.setDoubleTapZoomDuration(100)
                            longImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                            longImageView.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
                            longImageView.setImage(
                                ImageSource.bitmap(resource),
                                ImageViewState(0F, PointF(0F, 0F), 0)
                            )
                        } else {
                            // ????????????
                            imageView.setImageBitmap(resource)
                        }
                    }
                }
            })
    }


    /**
     * ??????????????????
     *
     * @param context   ?????????
     * @param url       ????????????
     * @param imageView ????????????ImageView
     */
    override fun loadFolderImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .override(180, 180)
            .centerCrop()
            .sizeMultiplier(0.5f)
            .apply(RequestOptions().placeholder(R.drawable.picture_image_placeholder))
            .into(object : BitmapImageViewTarget(imageView) {
                protected override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable: RoundedBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.cornerRadius = 8f
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
    }

    /**
     * ??????gif
     *
     * @param context   ?????????
     * @param url       ????????????
     * @param imageView ????????????ImageView
     */
    override fun loadAsGifImage(
        context: Context, url: String,
        imageView: ImageView
    ) {
        Glide.with(context)
            .asGif()
            .load(url)
            .into(imageView)
    }

    /**
     * ????????????????????????
     *
     * @param context   ?????????
     * @param url       ????????????
     * @param imageView ????????????ImageView
     */
    override fun loadGridImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .override(200, 200)
            .centerCrop()
            .apply(RequestOptions().placeholder(R.drawable.picture_image_placeholder))
            .into(imageView)
    }

    companion object {
        var instance: GlideEngine? = null
            get() {
                if (null == field) {
                    synchronized(GlideEngine::class.java) {
                        if (null == field) {
                            field = GlideEngine()
                        }
                    }
                }
                return field
            }
            private set
    }
}