package uk.co.atomicmedia.developertest.presentation.features.mvi

interface BaseIntentionListener<T> {
    fun acceptIntention(intention : T)
}