package com.droidcon.myandroidapp.jokes.domain.model

data class Joke(
    val id: Int,
    val question: String,
    val answer: String,

    val isFavorite: Boolean = false
)

