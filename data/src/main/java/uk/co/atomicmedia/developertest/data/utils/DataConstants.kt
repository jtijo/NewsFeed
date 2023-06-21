package uk.co.atomicmedia.developertest.data.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*


//Room DB
const val ROOM_DATABASE_NAME = "newsfeed_db"
const val STORY_ENTITY = "story_entity"
const val HEADLINE_ENTITY = "headline_entity"

//Repository

const val ERROR_CODE_NETWORK = 700
const val ERROR_MSG_NO_NETWORK = "Please check your network connection. No offline data found"

const val ERROR_CODE_HTTP_ERROR = 701
const val ERROR_MSG_HTTP_ERROR = "Failed to fetch data from server. No offline data found"

const val ERROR_CODE_UNSPECIFIED_ERROR = 702
const val ERROR_MSG = "Something went wrong. Please try again."

fun Instant.convertToDateString(): String {
    val date = Date.from(this)
    val formatter = SimpleDateFormat("dd MM,yyyy HH:mm")
    return formatter.format(date)
}

