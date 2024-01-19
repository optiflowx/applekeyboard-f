package com.optiflowx.applekeyboard.utils

import androidx.compose.runtime.Immutable


@Immutable
class KeyboardLocale {
    fun searchEmoji(locale: String?): String {
        return when (locale) {
            KeyboardLanguage.ENGLISH.name -> "Search Emoji"
            KeyboardLanguage.RUSSIAN.name -> "Поиск смайликов"
            KeyboardLanguage.FRENCH.name -> "Rechercher Emoji"
            KeyboardLanguage.SPANISH.name -> "Buscar Emoji"
            KeyboardLanguage.PORTUGUESE.name -> "Pesquisar Emoji"
            KeyboardLanguage.ITALIAN.name -> "Cerca Emoji"
            KeyboardLanguage.DUTCH.name -> "Zoek Emoji"
            KeyboardLanguage.GERMAN.name -> "Emoji suchen"
            else -> "Search Emoji"
        }
    }

    fun space(locale: String?): String {
        return when (locale) {
            KeyboardLanguage.ENGLISH.name -> "space"
            KeyboardLanguage.RUSSIAN.name -> "пробел"
            KeyboardLanguage.FRENCH.name -> "espace"
            KeyboardLanguage.SPANISH.name -> "espacio"
            KeyboardLanguage.PORTUGUESE.name -> "espaço"
            KeyboardLanguage.ITALIAN.name -> "spazio"
            KeyboardLanguage.DUTCH.name -> "spatie"
            KeyboardLanguage.GERMAN.name -> "Leerzeichen"
            else -> "space"
        }
    }

    fun pause(locale: String?): String {
        return when (locale) {
            KeyboardLanguage.ENGLISH.name -> "pause"
            KeyboardLanguage.RUSSIAN.name -> "пауза"
            KeyboardLanguage.FRENCH.name -> "pause"
            KeyboardLanguage.SPANISH.name -> "pausa"
            KeyboardLanguage.PORTUGUESE.name -> "pausa"
            KeyboardLanguage.ITALIAN.name -> "pausa"
            KeyboardLanguage.DUTCH.name -> "pauze"
            KeyboardLanguage.GERMAN.name -> "Pause"
            else -> "pause"
        }
    }

    fun wait(locale: String?): String {
        return when (locale) {
            KeyboardLanguage.ENGLISH.name -> "wait"
            KeyboardLanguage.RUSSIAN.name -> "ждать"
            KeyboardLanguage.FRENCH.name -> "attendre"
            KeyboardLanguage.SPANISH.name -> "esperar"
            KeyboardLanguage.PORTUGUESE.name -> "esperar"
            KeyboardLanguage.ITALIAN.name -> "aspettare"
            KeyboardLanguage.DUTCH.name -> "wachten"
            KeyboardLanguage.GERMAN.name -> "warten"
            else -> "wait"
        }
    }


    fun action(text: String, locale: String?): String {
        return when (text) {
            "done" -> when (locale) {
                KeyboardLanguage.ENGLISH.name -> "done"
                KeyboardLanguage.RUSSIAN.name -> "сделано"
                KeyboardLanguage.FRENCH.name -> "terminé"
                KeyboardLanguage.SPANISH.name -> "aceptar"
                KeyboardLanguage.PORTUGUESE.name -> "concluído"
                KeyboardLanguage.ITALIAN.name -> "fatto"
                KeyboardLanguage.DUTCH.name -> "gedaan"
                KeyboardLanguage.GERMAN.name -> "erledigt"
                else -> "done"
            }

            "go" -> when (locale) {
                KeyboardLanguage.ENGLISH.name -> "go"
                KeyboardLanguage.RUSSIAN.name -> "идти"
                KeyboardLanguage.FRENCH.name -> "aller"
                KeyboardLanguage.SPANISH.name -> "ir"
                KeyboardLanguage.PORTUGUESE.name -> "ir"
                KeyboardLanguage.ITALIAN.name -> "andare"
                KeyboardLanguage.DUTCH.name -> "gaan"
                KeyboardLanguage.GERMAN.name -> "gehen"
                else -> "go"
            }

            "search" -> when (locale) {
                KeyboardLanguage.ENGLISH.name -> "search"
                KeyboardLanguage.RUSSIAN.name -> "поиск"
                KeyboardLanguage.FRENCH.name -> "rechercher"
                KeyboardLanguage.SPANISH.name -> "buscar"
                KeyboardLanguage.PORTUGUESE.name -> "buscar"
                KeyboardLanguage.ITALIAN.name -> "ricerca"
                KeyboardLanguage.DUTCH.name -> "zoeken"
                KeyboardLanguage.GERMAN.name -> "suche"
                else -> "search"
            }

            "next" -> when (locale) {
                KeyboardLanguage.ENGLISH.name -> "next"
                KeyboardLanguage.RUSSIAN.name -> "следующий"
                KeyboardLanguage.FRENCH.name -> "suivant"
                KeyboardLanguage.SPANISH.name -> "próximo"
                KeyboardLanguage.PORTUGUESE.name -> "próximo"
                KeyboardLanguage.ITALIAN.name -> "prossimo"
                KeyboardLanguage.DUTCH.name -> "volgende"
                KeyboardLanguage.GERMAN.name -> "nächster"
                else -> "next"
            }

            "send" -> when (locale) {
                KeyboardLanguage.ENGLISH.name -> "send"
                KeyboardLanguage.RUSSIAN.name -> "послать"
                KeyboardLanguage.FRENCH.name -> "envoyer"
                KeyboardLanguage.SPANISH.name -> "enviar"
                KeyboardLanguage.PORTUGUESE.name -> "enviar"
                KeyboardLanguage.ITALIAN.name -> "inviare"
                KeyboardLanguage.DUTCH.name -> "verzenden"
                KeyboardLanguage.GERMAN.name -> "senden"
                else -> "send"
            }

            else -> when (locale) {
                KeyboardLanguage.ENGLISH.name -> "return"
                KeyboardLanguage.RUSSIAN.name -> "возврат"
                KeyboardLanguage.FRENCH.name -> "retour"
                KeyboardLanguage.SPANISH.name -> "intro"
                KeyboardLanguage.PORTUGUESE.name -> "retorno"
                KeyboardLanguage.ITALIAN.name -> "ritorno"
                KeyboardLanguage.DUTCH.name -> "terug"
                KeyboardLanguage.GERMAN.name -> "zurück"
                else -> "return"
            }
        }
    }
}