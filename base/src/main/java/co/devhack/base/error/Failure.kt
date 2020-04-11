package co.devhack.base.error

sealed class Failure {

    class GenericError(val exception: Exception) : Failure()

    class HttpErrorUnauthorized(val exception: Exception) : Failure()
    class HttpErrorBadRequest(val exception: Exception) : Failure()
    class HttpErrorNotFound(val exception: Exception) : Failure()
    class HttpErrorForbidden(val exception: Exception) : Failure()
    class HttpErrorInternalServerError(val exception: Exception) : Failure()
    class HttpError(val exception: Exception) : Failure()

    class RoomSqlError(val exception: Exception) : Failure()
    class RepositoryError<T>(private val t: T) : Failure()

    object CustomError : Failure()
    object NetworkConnection : Failure()
}