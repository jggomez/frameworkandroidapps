package co.devhack.presentation

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import co.devhack.base.error.Failure

abstract class BaseFragment : Fragment() {

    private var permitCode: Int? = null
    private lateinit var lstRequestPermissions: Array<String>
    private lateinit var blockAcceptPermissions: () -> Unit

    abstract fun layoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutId(), container, false)


    abstract fun hideProgress()

    abstract fun showProgress()

    open fun showPermissionExplain() {
        throw NotImplementedError("Should be override this method and customize the permission explain")
    }

    fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnection -> notifyError(
                messageIdRes = R.string.failure_network_connection
            )
            is Failure.CustomError -> notifyError(
                messageIdRes = R.string.failure_unavailable
            )
            is Failure.HttpError,
            is Failure.HttpErrorInternalServerError,
            is Failure.HttpErrorForbidden,
            is Failure.HttpErrorBadRequest,
            is Failure.HttpErrorUnauthorized,
            is Failure.RoomSqlError -> notifyError(
                messageIdRes = R.string.failure_unavailable
            )

            else -> notifyError(
                messageIdRes = R.string.failure_unavailable_unknown
            )
        }
    }

    open fun notifyError(@StringRes messageIdRes: Int? = null, message: String? = null) {
        NotifyUtil.notify(messageIdRes, message, R.color.error_snackbar, view!!)
    }

    fun notifyWarning(@StringRes messageIdRes: Int? = null, message: String? = null) {
        NotifyUtil.notify(
            messageIdRes,
            message,
            R.color.warning_snackbar,
            view!!
        )
    }

    fun notifySuccess(@StringRes messageIdRes: Int? = null, message: String? = null, @ColorRes colorId: Int) {
        NotifyUtil.notify(messageIdRes, message, colorId, view!!)
    }

    open fun notifyWithAction(
        @StringRes message: Int,
        @StringRes actionText: Int,
        @ColorRes colorId: Int,
        @ColorRes colorActionId: Int,
        action: () -> Any
    ) {
        NotifyUtil.notifyWithAction(
            message,
            actionText,
            colorId,
            colorActionId,
            view!!,
            activity!!,
            action
        )
    }

    fun requestPermissions(
        requestPermissions: Array<String>,
        codeRequestPermission: Int,
        blockAcceptPermissions: () -> Unit
    ) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            blockAcceptPermissions.invoke()
            return
        }

        this.lstRequestPermissions = requestPermissions
        this.blockAcceptPermissions = blockAcceptPermissions

        var permissionsDone = true

        lstRequestPermissions.forEach {
            if (ActivityCompat.checkSelfPermission(
                    activity!!,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsDone = false
            }
        }

        if (permissionsDone) {
            blockAcceptPermissions.invoke()
            return
        }

        var hasRequestPermissions = false

        lstRequestPermissions.forEach {
            if (shouldShowRequestPermissionRationale(it)) {
                hasRequestPermissions = true
                permitCode = codeRequestPermission

                notifyWithAction(
                    R.string.request_permissions,
                    android.R.string.ok,
                    R.color.warning_snackbar,
                    R.color.warning_snackbar
                )
                {
                    requestPermissions(
                        lstRequestPermissions,
                        codeRequestPermission
                    )
                }
            }
        }

        if (!hasRequestPermissions) {
            permitCode = codeRequestPermission

            requestPermissions(
                lstRequestPermissions,
                codeRequestPermission
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == permitCode ?: 0) {
            if (grantResults.size == lstRequestPermissions.size) {
                var allPermissionsDone = true

                grantResults.forEach {
                    if (it != PackageManager.PERMISSION_GRANTED) {
                        allPermissionsDone = false
                    }
                }

                if (!allPermissionsDone) {
                    showPermissionExplain()
                    return
                }

                Toast.makeText(
                    activity!!, R.string.permissions_granted,
                    Toast.LENGTH_SHORT
                ).show()

                this.blockAcceptPermissions.invoke()
            }
        }

    }


}