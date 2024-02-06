package com.optiflowx.optikeysx.core.utils

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
class KeyboardLocale(val locale: String) {

    @Stable
    fun keyboardSettings(): String {
        return when (locale) {
            "ru" -> "Настройки клавиатуры"
            "fr-FR" -> "Paramètres du clavier"
            "es" -> "Configuración del teclado"
            "pt-PT" -> "Configurações do teclado"
            "pt-BR" -> "Configurações do teclado"
            "it" -> "Impostazioni della tastiera"
            "nl" -> "Toetsenbordinstellingen"
            "de" -> "Tastatureinstellungen"
            else -> "Keyboard settings"
        }
    }

    @Stable
    fun language(): String {
        return when (locale) {
            "ru" -> "Язык"
            "fr-FR" -> "Langue"
            "es" -> "Idioma"
            "pt-PT" -> "Idioma"
            "pt-BR" -> "Idioma"
            "it" -> "Lingua"
            "nl" -> "Taal"
            "de" -> "Sprache"
            else -> "Language"
        }
    }

    @Stable
    fun emptyClipboard() : String {
        return when(locale) {
            "ru" -> "Буфер обмена пуст.\nСкопируйте данные, чтобы начать сохранение."
            "fr-FR" -> "Le presse-papiers est vide.\nCopiez des données pour commencer à enregistrer."
            "es" -> "El portapapeles está vacío.\nCopie datos para comenzar a guardar."
            "pt-PT" -> "A área de transferência está vazia.\nCopie dados para começar a salvar."
            "pt-BR" -> "A área de transferência está vazia.\nCopie dados para começar a salvar."
            "it" -> "La clipboard è vuota.\nCopia i dati per iniziare a salvare."
            "nl" -> "Het klembord is leeg.\nKopieer gegevens om te beginnen met opslaan."
            "de" -> "Die Zwischenablage ist leer.\nKopieren Sie Daten, um mit dem Speichern zu beginnen."
            else -> "Clipboard is empty.\nCopy data to start saving."
        }
    }

    @Stable
    fun clear() : String {
        return when (locale) {
            "ru" -> "Очистить"
            "fr-FR" -> "Effacer"
            "es" -> "Borrar"
            "pt-PT" -> "Limpar"
            "pt-BR" -> "Limpar"
            "it" -> "Cancella"
            "nl" -> "Wissen"
            "de" -> "Löschen"
            else -> "Clear"
        }
    }

    @Stable
    fun clipboard(): String {
        return when (locale) {
            "ru" -> "Буфер обмена"
            "fr-FR" -> "Presse-papiers"
            "es" -> "Portapapeles"
            "pt-PT" -> "Área de transferência"
            "pt-BR" -> "Área de transferência"
            "it" -> "Appunti"
            "nl" -> "Klembord"
            "de" -> "Zwischenablage"
            else -> "Clipboard"
        }
    }

    @Stable
    fun back() : String {
        return when(locale) {
            "ru" -> "Назад"
            "fr-FR" -> "Retour"
            "es" -> "Atrás"
            "pt-PT" -> "Voltar"
            "pt-BR" -> "Voltar"
            "it" -> "Indietro"
            "nl" -> "Terug"
            "de" -> "Zurück"
            else -> "Back"
        }
    }

    @Stable
    fun searchEmoji(): String {
        return when (locale) {
            "ru" -> "Поиск смайликов"
            "fr-FR" -> "Rechercher Emoji"
            "es" -> "Buscar Emoji"
            "pt-PT" -> "Pesquisar Emoji"
            "pt-BR" -> "Pesquisar Emoji"
            "it" -> "Cerca Emoji"
            "nl" -> "Zoek Emoji"
            "de" -> "Emoji suchen"
            else -> "Search Emoji"
        }
    }

    @Stable
    fun space(): String {
        return when (locale) {
            "ru" -> "пробел"
            "fr-FR" -> "espace"
            "es" -> "espacio"
            "pt-PT" -> "espaço"
            "pt-BR" -> "espaço"
            "it" -> "spazio"
            "nl" -> "spatie"
            "de" -> "Leerzeichen"
            else -> "space"
        }
    }

    @Stable
    fun pause(): String {
        return when (locale) {
            "ru" -> "пауза"
            "fr-FR" -> "pause"
            "es" -> "pausa"
            "pt-PT" -> "pausa"
            "pt-BR" -> "pausa"
            "it" -> "pausa"
            "nl" -> "pauze"
            "de" -> "Pause"
            else -> "pause"
        }
    }

    @Stable
    fun wait(): String {
        return when (locale) {
            "ru" -> "ждать"
            "fr-FR" -> "attendre"
            "es" -> "esperar"
            "pt-PT" -> "esperar"
            "pt-BR" -> "esperar"
            "it" -> "aspettare"
            "nl" -> "wachten"
            "de" -> "warten"
            else -> "wait"
        }
    }

    @Stable
    fun action(text: String): String {
        return when (text) {
            "done" -> when (locale) {
                "en-US" -> "done"
                "ru" -> "сделано"
                "fr-FR" -> "terminé"
                "es" -> "aceptar"
                "pt-PT" -> "concluído"
                "pt-BR" -> "concluído"
                "it" -> "fatto"
                "nl" -> "gedaan"
                "de" -> "erledigt"
                else -> "done"
            }

            "go" -> when (locale) {
                "ru" -> "идти"
                "fr-FR" -> "aller"
                "es" -> "ir"
                "pt-PT" -> "ir"
                "pt-BR" -> "ir"
                "it" -> "andare"
                "nl" -> "gaan"
                "de" -> "gehen"
                else -> "go"
            }

            "search" -> when (locale) {
                "ru" -> "поиск"
                "fr-FR" -> "rechercher"
                "es" -> "buscar"
                "pt-PT" -> "buscar"
                "pt-BR" -> "buscar"
                "it" -> "ricerca"
                "nl" -> "zoeken"
                "de" -> "suche"
                else -> "search"
            }

            "next" -> when (locale) {
                "en-US" -> "next"
                "ru" -> "следующий"
                "fr-FR" -> "suivant"
                "es" -> "próximo"
                "pt-PT" -> "próximo"
                "pt-BR" -> "próximo"
                "it" -> "prossimo"
                "nl" -> "volgende"
                "de" -> "nächster"
                else -> "next"
            }

            "send" -> when (locale) {
                "ru" -> "послать"
                "fr-FR" -> "envoyer"
                "es" -> "enviar"
                "pt-PT" -> "enviar"
                "pt-BR" -> "enviar"
                "it" -> "inviare"
                "nl" -> "verzenden"
                "de" -> "senden"
                else -> "send"
            }

            else -> when (locale) {
                "ru" -> "возврат"
                "fr-FR" -> "retour"
                "es" -> "intro"
                "pt-PT" -> "retorno"
                "pt-BR" -> "retorno"
                "it" -> "ritorno"
                "nl" -> "terug"
                "de" -> "zurück"
                else -> "return"
            }
        }
    }
}