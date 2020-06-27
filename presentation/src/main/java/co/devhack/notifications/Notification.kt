package co.devhack.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.Random
import java.util.UUID

class Notification(
    private val context: Context,
    private val channelName: String,
    private val title: String,
    private val description: String,
    private val smallIconResource: Int?,
    private val bigTextStyle: Boolean,
    private val openActivity: Class<*>?
) {

    fun show() =
        create()?.apply {
            val notificationId = Random().nextInt(1000000)
            NotificationManagerCompat.from(context).notify(notificationId, this.build())
        }

    private fun addAction(notificationCompat: NotificationCompat.Builder) {
        openActivity?.let {
            val intent = Intent(this.context, openActivity).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            notificationCompat.setContentIntent(
                PendingIntent.getActivity(this.context, 0, intent, 0)
            )
        }
    }

    private fun createChannel(): String {
        val channelId = UUID.randomUUID().toString()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            context.getSystemService(NotificationManager::class.java)
                ?.createNotificationChannel(canal)
        }

        return channelId
    }

    private fun create() =
        NotificationCompat.Builder(context, createChannel())
            .setContentTitle(this.title)
            .setContentText(this.description)?.apply {
                priority = NotificationCompat.PRIORITY_HIGH
                smallIconResource?.let {
                    this.setSmallIcon(it)
                }
                if (bigTextStyle) {
                    this.setStyle(
                        NotificationCompat.BigTextStyle()
                    )
                }
                addAction(this)
            }
}

class NotificationBuilder(private val context: Context) {

    private companion object {
        const val DEFAULT_NAME_CHANNEL = "default_name"
    }

    var channelName: String = DEFAULT_NAME_CHANNEL
    var title: String = ""
    var description: String = ""
    var smallIconResource: Int? = null
    var bigTextStyle: Boolean = false
    var openActivity: Class<*>? = null

    fun build() = Notification(
        this.context,
        this.channelName,
        this.title,
        this.description,
        this.smallIconResource,
        this.bigTextStyle,
        openActivity
    )
}

fun notification(context: Context, block: NotificationBuilder.() -> Unit) =
    NotificationBuilder(context).apply(block).build()
