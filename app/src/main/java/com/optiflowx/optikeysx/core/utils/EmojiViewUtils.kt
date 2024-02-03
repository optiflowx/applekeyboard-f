package com.optiflowx.optikeysx.core.utils

import androidx.compose.runtime.Stable

@Stable
fun handleTitle(title: String, locale: String?): String {
    return when(title) {
        "Smileys & People" -> {
            when(locale) {
                "English" -> "Smileys & People"
                "Spanish" -> "Caras y Personas"
                "French" -> "Smileys et Personnes"
                "German" -> "Smileys & Personen"
                "Italian" -> "Faccine e Persone"
                "Portuguese" -> "Carinhas e Pessoas"
                "Dutch" -> "Smileys en Mensen"
                "Russian" -> "Смайлики и люди"
                else -> "Smileys & People"
            }
        }
        "Animals & Nature" -> {
            when(locale) {
                "English" -> "Animals & Nature"
                "Spanish" -> "Animales y Naturaleza"
                "French" -> "Animaux et Nature"
                "German" -> "Tiere & Natur"
                "Italian" -> "Animali e Natura"
                "Portuguese" -> "Animais e Natureza"
                "Dutch" -> "Dieren en Natuur"
                "Russian" -> "Животные и природа"
                else -> "Animals & Nature"
            }
        }
        "Food & Drink" -> {
            when(locale) {
                "English" -> "Food & Drink"
                "Spanish" -> "Comida y Bebida"
                "French" -> "Nourriture et Boissons"
                "German" -> "Essen & Trinken"
                "Italian" -> "Cibo e Bevande"
                "Portuguese" -> "Comida e Bebida"
                "Dutch" -> "Eten en Drinken"
                "Russian" -> "Еда и напитки"
                else -> "Food & Drink"
            }
        }
        "Activities" -> {
            when(locale) {
                "English" -> "Activities"
                "Spanish" -> "Actividades"
                "French" -> "Activités"
                "German" -> "Aktivitäten"
                "Italian" -> "Attività"
                "Portuguese" -> "Atividades"
                "Dutch" -> "Activiteiten"
                "Russian" -> "Мероприятия"
                else -> "Activities"
            }
        }
        "Travel & Places" -> {
            when(locale) {
                "English" -> "Travel & Places"
                "Spanish" -> "Viajes y Lugares"
                "French" -> "Voyages et Lieux"
                "German" -> "Reisen & Orte"
                "Italian" -> "Viaggi e Luoghi"
                "Portuguese" -> "Viagens e Lugares"
                "Dutch" -> "Reizen en Plaatsen"
                "Russian" -> "Путешествия и места"
                else -> "Travel & Places"
            }
        }
        "Objects"-> {
            when(locale) {
                "English" -> "Objects"
                "Spanish" -> "Objetos"
                "French" -> "Objets"
                "German" -> "Objekte"
                "Italian" -> "Oggetti"
                "Portuguese" -> "Objetos"
                "Dutch" -> "Objecten"
                "Russian" -> "Объекты"
                else -> "Objects"
            }
        }
        "Symbols" -> {
            when(locale) {
                "English" -> "Symbols"
                "Spanish" -> "Símbolos"
                "French" -> "Symboles"
                "German" -> "Symbole"
                "Italian" -> "Simboli"
                "Portuguese" -> "Símbolos"
                "Dutch" -> "Symbolen"
                "Russian" -> "Символы"
                else -> "Symbols"
            }
        }
        "Flags" -> {
            when(locale) {
                "English" -> "Flags"
                "Spanish" -> "Banderas"
                "French" -> "Drapeaux"
                "German" -> "Flaggen"
                "Italian" -> "Bandiere"
                "Portuguese" -> "Bandeiras"
                "Dutch" -> "Vlaggen"
                "Russian" -> "Флаги"
                else -> "Flags"
            }
        }
        else -> {
            when (locale) {
                "English" -> "Frequently Used"
                "Spanish" -> "Frecuentemente Usado"
                "French" -> "Utilisé Fréquemment"
                "German" -> "Häufig verwendet"
                "Italian" -> "Usato frequentemente"
                "Portuguese" -> "Usado com frequência"
                "Dutch" -> "Vaak gebruikt"
                "Russian" -> "Часто используется"
                else -> "Frequently Used"
            }
        }
    }
}