package com.vp.detail.mapper

interface Mapper<in I, out O> {
    fun map(input: I): O
}