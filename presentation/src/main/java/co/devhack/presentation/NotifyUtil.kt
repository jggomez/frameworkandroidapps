package co.devhack.presentation

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

object NotifyUtil {

    private const val NOT_FOUND_MESSAGE = "THE MESSAGE IS NOT FOUND"

    fun notify(
        @StringRes messageIdRes: Int? = null,
        message: String? = null,
        @ColorRes colorId: Int,
        view: View
    ) {
        when {
            messageIdRes != null -> Snackbar.make(
                view,
                messageIdRes,
                Snackbar.LENGTH_SHORT
            ).apply {
                view.setBackgroundColor(ContextCompat.getColor(context, colorId))
                show()
            }
            message != null -> Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_SHORT
            ).apply {
                view.setBackgroundColor(ContextCompat.getColor(context, colorId))
                show()
            }
            else -> Snackbar.make(
                view,
                NOT_FOUND_MESSAGE,
                Snackbar.LENGTH_SHORT
            ).apply {
                view.setBackgroundColor(ContextCompat.getColor(context, colorId))
                show()
            }
        }
    }

    fun notifyWithAction(
        @StringRes message: Int,
        @StringRes actionText: Int,
        @ColorRes colorId: Int,
        @ColorRes colorActionId: Int,
        view: View,
        context: Context,
        action: () -> Any
    ) {
        view.rootView?.let {
            val snackBar = Snackbar.make(
                it,
                message,
                Snackbar.LENGTH_INDEFINITE
            ).apply {
                view.setBackgroundColor(ContextCompat.getColor(context, colorId))
            }

            snackBar.setAction(actionText) { action.invoke() }
            snackBar.setActionTextColor(
                ContextCompat.getColor(
                    context,
                    colorActionId
                )
            ).show()
        }
    }

}