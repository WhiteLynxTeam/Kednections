package com.kednections.domain.models

data class City(
    val id: String,
    val name: String,
) {
    override fun toString(): String = name
}