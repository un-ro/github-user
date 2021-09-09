package com.unero.githubuser.ui.widget

import android.content.Intent
import android.widget.RemoteViewsService
import org.koin.android.ext.android.inject

class StackWidgetService: RemoteViewsService() {

    private val remote: StackRemoteViewsFactory by inject()

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory = remote
}