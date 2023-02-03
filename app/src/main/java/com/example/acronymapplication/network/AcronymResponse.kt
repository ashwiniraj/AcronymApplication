package com.example.acronymapplication.network

data class AcronymResponse(
    val sf: String,
    val lfs: List<LongForms>
)

data class LongForms(
    val lf: String,
    val freq: Int,
    val since: Int,
    val vars: List<Variations>
)

data class Variations(
    val lf: String,
    val freq: Int,
    val since: Int
)
