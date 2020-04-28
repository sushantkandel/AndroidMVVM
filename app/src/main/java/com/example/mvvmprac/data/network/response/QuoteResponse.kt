package com.example.mvvmprac.data.network.response

import com.example.mvvmprac.data.db.entities.Quote


data class QuoteResponse(
    val isSuccessful:Boolean?,
    val quotes:List<Quote>
)