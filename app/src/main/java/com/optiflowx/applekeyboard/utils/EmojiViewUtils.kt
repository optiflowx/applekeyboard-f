package com.optiflowx.applekeyboard.utils

fun handleTitle(title: String, locale: String?): String {
    return when(title) {
        "Smileys & People" -> {
            when(locale) {
                "es" -> "Caras y Personas"
                "fr" -> "Smileys et Personnes"
                "de" -> "Smileys & Personen"
                "it" -> "Faccine e Persone"
                "pt" -> "Carinhas e Pessoas"
                "ru" -> "Смайлики и люди"
                "ja" -> "顔文字と人々"
                "ko" -> "스마일리 및 사람들"
                "zh" -> "表情符号和人物"
                else -> "Smileys & People"
            }
        }
        "Animals & Nature" -> {
            when(locale) {
                "es" -> "Animales y Naturaleza"
                "fr" -> "Animaux et Nature"
                "de" -> "Tiere & Natur"
                "it" -> "Animali e Natura"
                "pt" -> "Animais e Natureza"
                "ru" -> "Животные и природа"
                "ja" -> "動物と自然"
                "ko" -> "동물 및 자연"
                "zh" -> "动物和自然"
                else -> "Animals & Nature"
            }
        }
        "Food & Drink" -> {
            when(locale) {
                "es" -> "Comida y Bebida"
                "fr" -> "Nourriture et Boissons"
                "de" -> "Essen & Trinken"
                "it" -> "Cibo e Bevande"
                "pt" -> "Comida e Bebida"
                "ru" -> "Еда и напитки"
                "ja" -> "食べ物と飲み物"
                "ko" -> "음식 및 음료"
                "zh" -> "食物和饮料"
                else -> "Food & Drink"
            }
        }
        "Activities" -> {
            when(locale) {
                "es" -> "Actividades"
                "fr" -> "Activités"
                "de" -> "Aktivitäten"
                "it" -> "Attività"
                "pt" -> "Atividades"
                "ru" -> "Деятельность"
                "ja" -> "アクティビティ"
                "ko" -> "활동"
                "zh" -> "活动"
                else -> "Activities"
            }
        }
        "Travel & Places" -> {
            when(locale) {
                "es" -> "Viajes y Lugares"
                "fr" -> "Voyages et Lieux"
                "de" -> "Reisen & Orte"
                "it" -> "Viaggi e Luoghi"
                "pt" -> "Viagens e Locais"
                "ru" -> "Путешествия и места"
                "ja" -> "旅行と場所"
                "ko" -> "여행 및 장소"
                "zh" -> "旅行和地点"
                else -> "Travel & Places"
            }
        }
        "Objects" -> {
            when(locale) {
                "es" -> "Objetos"
                "fr" -> "Objets"
                "de" -> "Objekte"
                "it" -> "Oggetti"
                "pt" -> "Objetos"
                "ru" -> "Объекты"
                "ja" -> "オブジェクト"
                "ko" -> "물건"
                "zh" -> "物品"
                else -> "Objects"
            }
        }
        "Symbols" -> {
            when(locale) {
                "es" -> "Símbolos"
                "fr" -> "Symboles"
                "de" -> "Symbole"
                "it" -> "Simboli"
                "pt" -> "Símbolos"
                "ru" -> "Символы"
                "ja" -> "シンボル"
                "ko" -> "기호"
                "zh" -> "符号"
                else -> "Symbols"
            }
        }
        "Flags" -> {
            when(locale) {
                "es" -> "Banderas"
                "fr" -> "Drapeaux"
                "de" -> "Flaggen"
                "it" -> "Bandiere"
                "pt" -> "Bandeiras"
                "ru" -> "Флаги"
                "ja" -> "旗"
                "ko" -> "깃발"
                "zh" -> "旗帜"
                else -> "Flags"
            }
        }
        else -> {
            when(locale) {
                "es" -> "Frecuentemente Usado"
                "fr" -> "Utilisé Fréquemment"
                "de" -> "Häufig verwendet"
                "it" -> "Usato frequentemente"
                "pt" -> "Usado com frequência"
                "ru" -> "Часто используется"
                "ja" -> "よく使う"
                "ko" -> "자주 사용"
                "zh" -> "经常使用"
                else -> "Frequently Used"
            }
        }
    }
}