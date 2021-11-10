package com.example.dictionaryretrofit.json

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)