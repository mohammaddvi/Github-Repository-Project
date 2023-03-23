package com.example.githubrepositories.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <R, T> Flow<List<R>>.flowList(mapper: (R) -> T): Flow<List<T>> =
    this.map { list -> list.map { mapper(it) } }