package uk.co.atomicmedia.developertest.domain.usecases

abstract class BaseReturnUseCase<R> {
    abstract suspend fun execute(): R
}