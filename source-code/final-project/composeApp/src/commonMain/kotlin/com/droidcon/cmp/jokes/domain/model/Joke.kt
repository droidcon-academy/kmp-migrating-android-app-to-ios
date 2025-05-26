package com.droidcon.cmp.jokes.domain.model

data class Joke(
    val id: Int,
    val question: String,
    val answer: String,

    val isFavorite: Boolean = false
)
