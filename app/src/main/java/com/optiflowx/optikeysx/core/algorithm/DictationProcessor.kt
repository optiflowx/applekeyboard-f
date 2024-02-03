package com.optiflowx.optikeysx.core.algorithm

import androidx.compose.runtime.Immutable
import java.util.stream.IntStream
import kotlin.streams.asSequence

@Immutable
class DictationProcessor(dictionary: Set<String>) {
    private val dict: MutableMap<String, Int> = HashMap()

    init {
        dictionary.forEach { word ->
            dict.compute(word) { _, v -> v?.plus(1) ?: 1 }
        }
    }

    private fun edits1(word: String): Sequence<String> {
        val alphabets = "abcdefghijklmnopqrstuvwxyz"

        val deletes = IntStream.range(0, word.length).mapToObj { i -> word.substring(0, i) + word.substring(i + 1) }.asSequence()

        val replaces = IntStream.range(0, word.length)
            .mapToObj { i -> i }
            .flatMap { i ->
                alphabets.chars()
                    .mapToObj { c -> word.substring(0, i) + c.toChar() + word.substring(i + 1) }
            }.asSequence()

        val inserts = IntStream.range(0, word.length + 1)
            .mapToObj { i -> i }
            .flatMap { i ->
                alphabets.chars()
                    .mapToObj { c -> word.substring(0, i) + c.toChar() + word.substring(i) }
            }.asSequence()

        val transposes = IntStream.range(0, word.length - 1)
            .mapToObj { i -> word.substring(0, i) + word.substring(i + 1, i + 2) + word[i] + word.substring(i + 2) }.asSequence()

        return sequenceOf(deletes, replaces, inserts, transposes).flatMap { it }
    }

    private fun known(words: Sequence<String>): Sequence<String> {
        return words.filter { word -> dict.containsKey(word) }
    }

    fun correct(word: String): String {
        val e1 = known(edits1(word.lowercase())).maxByOrNull { dict[it] ?: 0 }
        val e2 = known(edits1(word.lowercase()).flatMap { w2 -> edits1(w2) }).maxByOrNull { dict[it] ?: 0 }
        return if (dict.containsKey(word)) word else e1 ?: e2 ?: word
    }
}