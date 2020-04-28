package com.example.mvvmprac.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.example.mvvmprac.data.repositories.QuoteRepository
import com.example.mvvmprac.util.lazyDeferred

class QuotesViewModel(repository: QuoteRepository) : ViewModel() {
    val quotes by lazyDeferred {
        repository.getQuotes()
    }

}
