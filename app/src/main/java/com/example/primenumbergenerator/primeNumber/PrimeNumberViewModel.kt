package com.example.primenumbergenerator.primeNumber

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.floor

class PrimeNumberViewModel : ViewModel() {
    private fun isPrimeNumber(number: Int): Boolean {
        if(number <= 1) {
            return false
        }
        for (i in 2 until number) {
            if (number % i == 0) {
                return false
            }
        }
        return true
    }

    suspend fun generatePrimeNumbers(start: Int, end: Int): MutableList<Int> {

        val primeNumbers = mutableListOf<Int>()
        val lock = Mutex()

        if(end - start > 10000){
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            val jobs = mutableListOf<Job>()
            val step = floor((end - start) / 10000.0).toInt()
            for (i in start until end + 1 step step) {
                jobs.add(coroutineScope.launch {
                    for (j in i until i + step) {
                        if (isPrimeNumber(j)) {
                            lock.withLock {
                                primeNumbers.add(j)
                            }
                        }
                    }
                })
            }
            jobs.forEach { it.join() }
            primeNumbers.sort()

            return primeNumbers
        }else{
            for (i in start until end + 1) {
                if (isPrimeNumber(i)) {
                    primeNumbers.add(i)
                }
            }
            primeNumbers.sort()
            return primeNumbers
        }
    }
}