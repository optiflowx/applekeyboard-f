package com.optiflowx.applekeyboard.utils

fun handleTitle(title: String, locale: String?): String {
    return when(title) {
        "Smileys & People" -> {
            when(locale) {
                KeyboardLanguage.ENGLISH.name -> "Smileys & People"
                KeyboardLanguage.SPANISH.name -> "Caras y Personas"
                KeyboardLanguage.FRENCH.name -> "Smileys et Personnes"
                KeyboardLanguage.GERMAN.name -> "Smileys & Personen"
                KeyboardLanguage.ITALIAN.name -> "Faccine e Persone"
                KeyboardLanguage.PORTUGUESE.name -> "Carinhas e Pessoas"
                KeyboardLanguage.DUTCH.name -> "Smileys en Mensen"
                KeyboardLanguage.RUSSIAN.name -> "Смайлики и люди"
                else -> "Smileys & People"
            }
        }
        "Animals & Nature" -> {
            when(locale) {
                KeyboardLanguage.ENGLISH.name -> "Animals & Nature"
                KeyboardLanguage.SPANISH.name -> "Animales y Naturaleza"
                KeyboardLanguage.FRENCH.name -> "Animaux et Nature"
                KeyboardLanguage.GERMAN.name -> "Tiere & Natur"
                KeyboardLanguage.ITALIAN.name -> "Animali e Natura"
                KeyboardLanguage.PORTUGUESE.name -> "Animais e Natureza"
                KeyboardLanguage.DUTCH.name -> "Dieren en Natuur"
                KeyboardLanguage.RUSSIAN.name -> "Животные и природа"
                else -> "Animals & Nature"
            }
        }
        "Food & Drink" -> {
            when(locale) {
                KeyboardLanguage.ENGLISH.name -> "Food & Drink"
                KeyboardLanguage.SPANISH.name -> "Comida y Bebida"
                KeyboardLanguage.FRENCH.name -> "Nourriture et Boissons"
                KeyboardLanguage.GERMAN.name -> "Essen & Trinken"
                KeyboardLanguage.ITALIAN.name -> "Cibo e Bevande"
                KeyboardLanguage.PORTUGUESE.name -> "Comida e Bebida"
                KeyboardLanguage.DUTCH.name -> "Eten en Drinken"
                KeyboardLanguage.RUSSIAN.name -> "Еда и напитки"
                else -> "Food & Drink"
            }
        }
        "Activities" -> {
            when(locale) {
                KeyboardLanguage.ENGLISH.name -> "Activities"
                KeyboardLanguage.SPANISH.name -> "Actividades"
                KeyboardLanguage.FRENCH.name -> "Activités"
                KeyboardLanguage.GERMAN.name -> "Aktivitäten"
                KeyboardLanguage.ITALIAN.name -> "Attività"
                KeyboardLanguage.PORTUGUESE.name -> "Atividades"
                KeyboardLanguage.DUTCH.name -> "Activiteiten"
                KeyboardLanguage.RUSSIAN.name -> "Мероприятия"
                else -> "Activities"
            }
        }
        "Travel & Places" -> {
            when(locale) {
                KeyboardLanguage.ENGLISH.name -> "Travel & Places"
                KeyboardLanguage.SPANISH.name -> "Viajes y Lugares"
                KeyboardLanguage.FRENCH.name -> "Voyages et Lieux"
                KeyboardLanguage.GERMAN.name -> "Reisen & Orte"
                KeyboardLanguage.ITALIAN.name -> "Viaggi e Luoghi"
                KeyboardLanguage.PORTUGUESE.name -> "Viagens e Lugares"
                KeyboardLanguage.DUTCH.name -> "Reizen en Plaatsen"
                KeyboardLanguage.RUSSIAN.name -> "Путешествия и места"
                else -> "Travel & Places"
            }
        }
        "Objects"-> {
            when(locale) {
                KeyboardLanguage.ENGLISH.name -> "Objects"
                KeyboardLanguage.SPANISH.name -> "Objetos"
                KeyboardLanguage.FRENCH.name -> "Objets"
                KeyboardLanguage.GERMAN.name -> "Objekte"
                KeyboardLanguage.ITALIAN.name -> "Oggetti"
                KeyboardLanguage.PORTUGUESE.name -> "Objetos"
                KeyboardLanguage.DUTCH.name -> "Objecten"
                KeyboardLanguage.RUSSIAN.name -> "Объекты"
                else -> "Objects"
            }
        }
        "Symbols" -> {
            when(locale) {
                KeyboardLanguage.ENGLISH.name -> "Symbols"
                KeyboardLanguage.SPANISH.name -> "Símbolos"
                KeyboardLanguage.FRENCH.name -> "Symboles"
                KeyboardLanguage.GERMAN.name -> "Symbole"
                KeyboardLanguage.ITALIAN.name -> "Simboli"
                KeyboardLanguage.PORTUGUESE.name -> "Símbolos"
                KeyboardLanguage.DUTCH.name -> "Symbolen"
                KeyboardLanguage.RUSSIAN.name -> "Символы"
                else -> "Symbols"
            }
        }
        "Flags" -> {
            when(locale) {
                KeyboardLanguage.ENGLISH.name -> "Flags"
                KeyboardLanguage.SPANISH.name -> "Banderas"
                KeyboardLanguage.FRENCH.name -> "Drapeaux"
                KeyboardLanguage.GERMAN.name -> "Flaggen"
                KeyboardLanguage.ITALIAN.name -> "Bandiere"
                KeyboardLanguage.PORTUGUESE.name -> "Bandeiras"
                KeyboardLanguage.DUTCH.name -> "Vlaggen"
                KeyboardLanguage.RUSSIAN.name -> "Флаги"
                else -> "Flags"
            }
        }
        else -> {
            when (locale) {
                KeyboardLanguage.ENGLISH.name -> "Frequently Used"
                KeyboardLanguage.SPANISH.name -> "Frecuentemente Usado"
                KeyboardLanguage.FRENCH.name -> "Utilisé Fréquemment"
                KeyboardLanguage.GERMAN.name -> "Häufig verwendet"
                KeyboardLanguage.ITALIAN.name -> "Usato frequentemente"
                KeyboardLanguage.PORTUGUESE.name -> "Usado com frequência"
                KeyboardLanguage.DUTCH.name -> "Vaak gebruikt"
                KeyboardLanguage.RUSSIAN.name -> "Часто используется"
                else -> "Frequently Used"
            }
        }
    }
}