package com.example.mvvmprac.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmprac.data.db.AppDatabase
import com.example.mvvmprac.data.db.entities.Quote
import com.example.mvvmprac.data.network.MyApi
import com.example.mvvmprac.data.network.SafeApiRequest
import com.example.mvvmprac.data.preferences.PreferenceProvider
import com.example.mvvmprac.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private const val MINIMUM_INTERVAL = 6

class QuoteRepository(
    private val myApi: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes() {
        val lastSavedAt = prefs.getLastSavedAt()
        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            val response = apiRequest { myApi.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }


    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            prefs.saveLastSavedAt(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuote(quotes)
        }

    }

}