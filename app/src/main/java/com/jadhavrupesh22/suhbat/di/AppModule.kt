package com.jadhavrupesh22.suhbat.di

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.jadhavrupesh22.suhbat.R
import com.jadhavrupesh22.suhbat.repo.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(@ApplicationContext context: Context) =
        Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
        )

//    @BindingAdapter("app:imageUrl")
//    fun loadImage(view: CircleImageView, url: String) {
//        Log.e(HomeRepository.TAG, "loadImage: $url")
//        val requestOptions = RequestOptions()
//        requestOptions.placeholder(R.drawable.ic_broken_image)
//        requestOptions.error(R.drawable.ic_broken_image)
//        // Load image
//        Glide.with(view.getContext())
//            .load(url)
//            .apply(requestOptions)
//            .into(view)
//    }

}