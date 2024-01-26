package com.optiflowx.applekeyboard.core.utils

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
class KeyboardLocale {

    @Stable
    fun emptyClipboard(locale: String?) : String {
        return when(locale) {
            "Russian" -> "Буфер обмена пуст.\nСкопируйте данные, чтобы начать сохранение."
            "French" -> "Le presse-papiers est vide.\nCopiez des données pour commencer à enregistrer."
            "Spanish" -> "El portapapeles está vacío.\nCopie datos para comenzar a guardar."
            "Portuguese" -> "A área de transferência está vazia.\nCopie dados para começar a salvar."
            "Italian" -> "La clipboard è vuota.\nCopia i dati per iniziare a salvare."
            "Dutch" -> "Het klembord is leeg.\nKopieer gegevens om te beginnen met opslaan."
            "German" -> "Die Zwischenablage ist leer.\nKopieren Sie Daten, um mit dem Speichern zu beginnen."
            else -> "Clipboard is empty.\nCopy data to start saving."
        }
    }

    @Stable
    fun clear(locale: String?) : String {
        return when (locale) {
            "Russian" -> "Очистить"
            "French" -> "Effacer"
            "Spanish" -> "Borrar"
            "Portuguese" -> "Limpar"
            "Italian" -> "Cancella"
            "Dutch" -> "Wissen"
            "German" -> "Löschen"
            else -> "Clear"
        }
    }

    @Stable
    fun clipboard(locale: String?): String {
        return when (locale) {
            "Russian" -> "Буфер обмена"
            "French" -> "Presse-papiers"
            "Spanish" -> "Portapapeles"
            "Portuguese" -> "Área de transferência"
            "Italian" -> "Appunti"
            "Dutch" -> "Klembord"
            "German" -> "Zwischenablage"
            else -> "Clipboard"
        }
    }

    @Stable
    fun back(locale: String?) : String {
        return when(locale) {
            "Russian" -> "Назад"
            "French" -> "Retour"
            "Spanish" -> "Atrás"
            "Portuguese" -> "Voltar"
            "Italian" -> "Indietro"
            "Dutch" -> "Terug"
            "German" -> "Zurück"
            else -> "Back"
        }
    }

    @Stable
    fun searchEmoji(locale: String?): String {
        return when (locale) {
            "Russian" -> "Поиск смайликов"
            "French" -> "Rechercher Emoji"
            "Spanish" -> "Buscar Emoji"
            "Portuguese" -> "Pesquisar Emoji"
            "Italian" -> "Cerca Emoji"
            "Dutch" -> "Zoek Emoji"
            "German" -> "Emoji suchen"
            else -> "Search Emoji"
        }
    }

    @Stable
    fun space(locale: String?): String {
        return when (locale) {
            "Russian" -> "пробел"
            "French" -> "espace"
            "Spanish" -> "espacio"
            "Portuguese" -> "espaço"
            "Italian" -> "spazio"
            "Dutch" -> "spatie"
            "German" -> "Leerzeichen"
            else -> "space"
        }
    }

    @Stable
    fun pause(locale: String?): String {
        return when (locale) {
            "Russian" -> "пауза"
            "French" -> "pause"
            "Spanish" -> "pausa"
            "Portuguese" -> "pausa"
            "Italian" -> "pausa"
            "Dutch" -> "pauze"
            "German" -> "Pause"
            else -> "pause"
        }
    }

    @Stable
    fun wait(locale: String?): String {
        return when (locale) {
            "Russian" -> "ждать"
            "French" -> "attendre"
            "Spanish" -> "esperar"
            "Portuguese" -> "esperar"
            "Italian" -> "aspettare"
            "Dutch" -> "wachten"
            "German" -> "warten"
            else -> "wait"
        }
    }

    @Stable
    fun action(text: String, locale: String?): String {
        return when (text) {
            "done" -> when (locale) {
                "English" -> "done"
                "Russian" -> "сделано"
                "French" -> "terminé"
                "Spanish" -> "aceptar"
                "Portuguese" -> "concluído"
                "Italian" -> "fatto"
                "Dutch" -> "gedaan"
                "German" -> "erledigt"
                else -> "done"
            }

            "go" -> when (locale) {
                "Russian" -> "идти"
                "French" -> "aller"
                "Spanish" -> "ir"
                "Portuguese" -> "ir"
                "Italian" -> "andare"
                "Dutch" -> "gaan"
                "German" -> "gehen"
                else -> "go"
            }

            "search" -> when (locale) {
                "Russian" -> "поиск"
                "French" -> "rechercher"
                "Spanish" -> "buscar"
                "Portuguese" -> "buscar"
                "Italian" -> "ricerca"
                "Dutch" -> "zoeken"
                "German" -> "suche"
                else -> "search"
            }

            "next" -> when (locale) {
                "English" -> "next"
                "Russian" -> "следующий"
                "French" -> "suivant"
                "Spanish" -> "próximo"
                "Portuguese" -> "próximo"
                "Italian" -> "prossimo"
                "Dutch" -> "volgende"
                "German" -> "nächster"
                else -> "next"
            }

            "send" -> when (locale) {
                "Russian" -> "послать"
                "French" -> "envoyer"
                "Spanish" -> "enviar"
                "Portuguese" -> "enviar"
                "Italian" -> "inviare"
                "Dutch" -> "verzenden"
                "German" -> "senden"
                else -> "send"
            }

            else -> when (locale) {
                "Russian" -> "возврат"
                "French" -> "retour"
                "Spanish" -> "intro"
                "Portuguese" -> "retorno"
                "Italian" -> "ritorno"
                "Dutch" -> "terug"
                "German" -> "zurück"
                else -> "return"
            }
        }
    }
}