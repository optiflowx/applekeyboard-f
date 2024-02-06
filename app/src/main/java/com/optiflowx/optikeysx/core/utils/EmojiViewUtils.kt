package com.optiflowx.optikeysx.core.utils

import androidx.compose.runtime.Stable

@Stable
fun handleTitle(title: String, locale: String): String {
    return when(title) {
        "Smileys & People" -> {
            when(locale) {
                "en-US" -> "Smileys & People"
                "es" -> "Caras y Personas"
                "fr-FR" -> "Smileys et Personnes"
                "de" -> "Smileys & Personen"
                "it" -> "Faccine e Persone"
                "pt" -> "Carinhas e Pessoas"
                "pt-BR" -> "Carinhas e Pessoas"
                "nl" -> "Smileys en Mensen"
                "ru" -> "Смайлики и люди"
                else -> "Smileys & People"
            }
        }
        "Animals & Nature" -> {
            when(locale) {
                "en-US" -> "Animals & Nature"
                "es" -> "Animales y Naturaleza"
                "fr-FR" -> "Animaux et Nature"
                "de" -> "Tiere & Natur"
                "it" -> "Animali e Natura"
                "pt" -> "Animais e Natureza"
                "pt-BR" -> "Animais e Natureza"
                "nl" -> "Dieren en Natuur"
                "ru" -> "Животные и природа"
                else -> "Animals & Nature"
            }
        }
        "Food & Drink" -> {
            when(locale) {
                "en-US" -> "Food & Drink"
                "es" -> "Comida y Bebida"
                "fr-FR" -> "Nourriture et Boissons"
                "de" -> "Essen & Trinken"
                "it" -> "Cibo e Bevande"
                "pt" -> "Comida e Bebida"
                "pt-BR" -> "Comida e Bebida"
                "nl" -> "Eten en Drinken"
                "ru" -> "Еда и напитки"
                else -> "Food & Drink"
            }
        }
        "Activities" -> {
            when(locale) {
                "en-US" -> "Activities"
                "es" -> "Actividades"
                "fr-FR" -> "Activités"
                "de" -> "Aktivitäten"
                "it" -> "Attività"
                "pt" -> "Atividades"
                "pt-BR" -> "Atividades"
                "nl" -> "Activiteiten"
                "ru" -> "Мероприятия"
                else -> "Activities"
            }
        }
        "Travel & Places" -> {
            when(locale) {
                "en-US" -> "Travel & Places"
                "es" -> "Viajes y Lugares"
                "fr-FR" -> "Voyages et Lieux"
                "de" -> "Reisen & Orte"
                "it" -> "Viaggi e Luoghi"
                "pt" -> "Viagens e Lugares"
                "pt-BR" -> "Viagens e Lugares"
                "nl" -> "Reizen en Plaatsen"
                "ru" -> "Путешествия и места"
                else -> "Travel & Places"
            }
        }
        "Objects"-> {
            when(locale) {
                "en-US" -> "Objects"
                "es" -> "Objetos"
                "fr-FR" -> "Objets"
                "de" -> "Objekte"
                "it" -> "Oggetti"
                "pt" -> "Objetos"
                "pt-BR" -> "Objetos"
                "nl" -> "Objecten"
                "ru" -> "Объекты"
                else -> "Objects"
            }
        }
        "Symbols" -> {
            when(locale) {
                "en-US" -> "Symbols"
                "es" -> "Símbolos"
                "fr-FR" -> "Symboles"
                "de" -> "Symbole"
                "it" -> "Simboli"
                "pt" -> "Símbolos"
                "pt-BR" -> "Símbolos"
                "nl" -> "Symbolen"
                "ru" -> "Символы"
                else -> "Symbols"
            }
        }
        "Flags" -> {
            when(locale) {
                "en-US" -> "Flags"
                "es" -> "Banderas"
                "fr-FR" -> "Drapeaux"
                "de" -> "Flaggen"
                "it" -> "Bandiere"
                "pt" -> "Bandeiras"
                "pt-BR" -> "Bandeiras"
                "nl" -> "Vlaggen"
                "ru" -> "Флаги"
                else -> "Flags"
            }
        }
        else -> {
            when (locale) {
                "en-US" -> "Frequently Used"
                "es" -> "Frecuentemente Usado"
                "fr-FR" -> "Utilisé Fréquemment"
                "de" -> "Häufig verwendet"
                "it" -> "Usato frequentemente"
                "pt" -> "Usado com frequência"
                "pt-BR" -> "Usado com frequência"
                "nl" -> "Vaak gebruikt"
                "ru" -> "Часто используется"
                else -> "Frequently Used"
            }
        }
    }
}