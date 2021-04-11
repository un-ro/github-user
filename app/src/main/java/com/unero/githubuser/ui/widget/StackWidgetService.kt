package com.unero.githubuser.ui.widget

import android.content.Intent
import android.widget.RemoteViewsService
import kotlinx.coroutines.InternalCoroutinesApi

class StackWidgetService: RemoteViewsService() {
    @InternalCoroutinesApi
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory = StackRemoteViewsFactory(this.applicationContext)
}