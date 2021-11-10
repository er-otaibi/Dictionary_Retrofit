package com.example.dictionaryretrofit.json

data class MyDictionaryItem(
    val meanings: List<Meaning>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val word: String
)