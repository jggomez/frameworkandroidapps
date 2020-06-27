package co.devhack.androidextensions.exception

import android.database.sqlite.SQLiteException
import co.devhack.base.error.Failure
import retrofit2.HttpException

const val HttpCodeErrorUnauthorized = 401
const val HttpCodeErrorBadRequest = 400
const val HttpCodeErrorNotFound = 404
const val HttpCodeErrorForbidden = 403
const val HttpCodeErrorInternalServerError = 500

fun Exception.toCustomExceptions() = when (this) {
    is HttpException -> {
        when (this.code()) {
            HttpCodeErrorUnauthorized -> Failure.HttpErrorUnauthorized(this)
            HttpCodeErrorBadRequest -> Failure.HttpErrorBadRequest(this)
            HttpCodeErrorNotFound -> Failure.HttpErrorNotFound(this)
            HttpCodeErrorForbidden -> Failure.HttpErrorForbidden(this)
            HttpCodeErrorInternalServerError -> Failure.HttpErrorInternalServerError(this)
            else -> Failure.HttpError(this as Exception)
        }
    }

    is SQLiteException -> Failure.RoomSqlError(this)

    else -> Failure.GenericError(this)
}
