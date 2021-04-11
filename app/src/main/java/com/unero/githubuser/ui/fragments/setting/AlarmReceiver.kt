package com.unero.githubuser.ui.fragments.setting

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.unero.githubuser.MainActivity
import com.unero.githubuser.R
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val ALARM_TYPE = "repeat"
        const val ALARM_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        private const val ID_ALARM = 100

        private const val TIME_FORMAT = "HH:mm"
    }

    override fun onReceive(context: Context, intent: Intent) {
        showReminder(context)
    }

    // Show Notification
    private fun showReminder(context: Context) {
        val channelId = "ch-1"
        val channelName = "GithubUSer Channel"

        // Pending Intent
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context)
                .addParentStack(MainActivity::class.java)
                .addNextIntent(intent)
                .getPendingIntent(ID_ALARM, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon_notification)
            .setContentIntent(pendingIntent)
            .setContentTitle("Github User")
            .setContentText("BALEK O COK!")
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(ID_ALARM, notification)
    }

    fun setRepeatingAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val repeatingIntent: PendingIntent =
                Intent(context, AlarmReceiver::class.java).let { intent ->
                    PendingIntent.getBroadcast(context, ID_ALARM, intent, 0)
                }

        val repeatingTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                repeatingTime.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                repeatingIntent
        )

        Toast.makeText(
                context,
                "Daily Reminder Activated",
                Toast.LENGTH_SHORT
        ).show()
    }

    fun cancelRepeatingAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val cancelIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_ALARM, cancelIntent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(
                context,
                "Daily Reminder Deactivated",
                Toast.LENGTH_SHORT
        ).show()
    }
}