package com.unero.githubuser.ui.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.unero.githubuser.R
import com.unero.githubuser.data.Repository
import com.unero.githubuser.data.local.model.Favorite

internal class StackRemoteViewsFactory(
    private val mContext: Context,
    private val repository: Repository
): RemoteViewsService.RemoteViewsFactory {

    private val listFavorite = mutableListOf<Favorite>()

    override fun onCreate() {}

    override fun onDataSetChanged() {
        val list = repository.getItemWidget()
        listFavorite.addAll(list)
    }

    override fun onDestroy() {}

    override fun getCount(): Int = listFavorite.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        // Set image
        val image = Glide.with(mContext)
            .asBitmap()
            .load(listFavorite[position].avatar)
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.iv_widget, image)

        // Toast username
        val extras = bundleOf(
            FavoriteWidget.EXTRA_ITEM to listFavorite[position].username
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.iv_widget, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}