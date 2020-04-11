package co.devhack.androidextensions.ui

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.setImageVector(resId: Int) {
    setImageDrawable(ContextCompat.getDrawable(this.context, resId))
}

fun ImageView.setUrl(url: String, @DrawableRes placeholderId: Int) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeholderId)
        .into(this)
}

fun ImageView.setUrlCircle(url: String, @DrawableRes placeholderId: Int) {
    Glide.with(this.context)
        .load(url)
        .circleCrop()
        .placeholder(placeholderId)
        .into(this)
}

fun ImageView.setThumbnailCircleCallback(
    url: String,
    scaleMultiplierSize: Float = 0.7f,
    execute: () -> Unit
) {
    Glide.with(this.context)
        .load(url)
        .circleCrop()
        .thumbnail(scaleMultiplierSize)
        .transition(DrawableTransitionOptions.withCrossFade())
        .listener(listener(execute))
        .into(this)
}

fun ImageView.setUrlCallback(url: String, execute: (() -> Unit)) {
    Glide.with(this.context)
        .load(url)
        .listener(listener(execute))
        .into(this)
}

fun ImageView.setGrayScale(
    isSaturationOn: Boolean,
    saturationOff: Float = 0f,
    saturationOn: Float = 1f
) {
    val colorMatrix = ColorMatrix()
    val saturation = if (isSaturationOn) saturationOn else saturationOff

    colorMatrix.setSaturation(saturation)

    colorFilter = ColorMatrixColorFilter(colorMatrix)
}

fun ImageView.setThumbnail(
    url: String,
    scaleMultiplierSize: Float = 0.7f,
    @DrawableRes placeholderId: Int
) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeholderId)
        .thumbnail(scaleMultiplierSize)
        .centerCrop()
        .into(this)
}

fun ImageView.setGif(resource: Int) {
    Glide.with(this.context)
        .asGif()
        .load(resource)
        .into(this)
}

fun listener(execute: () -> Unit): RequestListener<Drawable>? {
    return object : RequestListener<Drawable> {
        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            execute.invoke()
            return false
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }
    }
}