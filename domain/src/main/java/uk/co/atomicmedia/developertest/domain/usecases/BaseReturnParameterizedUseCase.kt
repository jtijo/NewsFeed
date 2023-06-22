package uk.co.atomicmedia.developertest.domain.usecases

abstract class BaseReturnParameterizedUseCase<in P,R> {
    abstract suspend fun execute(params : P) : R
}