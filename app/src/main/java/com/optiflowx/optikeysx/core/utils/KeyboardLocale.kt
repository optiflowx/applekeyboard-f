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
    fun emptyClipboard(): String {
        return when (locale) {
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
    fun noModel(): String {
        return when (locale) {
            "ru" -> "Модели не найдены. Пожалуйста, загрузите голосовую модель для вашего языка в настройках речи клавиатуры."
            "fr-FR" -> "Aucun modèle trouvé. Veuillez télécharger un modèle vocal pour votre langue dans les paramètres de la parole du clavier."
            "es" -> "No se encontraron modelos. Descargue un modelo de voz para su idioma en la configuración de voz del teclado."
            "pt-PT" -> "Nenhum modelo encontrado. Por favor, baixe um modelo de voz para o seu idioma nas configurações de voz do teclado."
            "pt-BR" -> "Nenhum modelo encontrado. Por favor, baixe um modelo de voz para o seu idioma nas configurações de voz do teclado."
            "it" -> "Nessun modello trovato. Si prega di scaricare un modello vocale per la propria lingua nelle impostazioni della voce della tastiera."
            "nl" -> "Geen modellen gevonden. Download een spraakmodel voor uw taal in de spraakinstellingen van het toetsenbord."
            "de" -> "Keine Modelle gefunden. Bitte laden Sie ein Sprachmodell für Ihre Sprache in den Tastatureinstellungen herunter."
            else -> "No models found. Please download a voice model  for your language in the keyboard voice settings."
        }
    }

    @Stable
    fun recognizerError() : String {
        return when (locale) {
            "ru" -> "Ошибка распознавания"
            "fr-FR" -> "Erreur de reconnaissance"
            "es" -> "Error de reconocimiento"
            "pt-PT" -> "Erro de reconhecimento"
            "pt-BR" -> "Erro de reconhecimento"
            "it" -> "Errore di riconoscimento"
            "nl" -> "Herkenningsfout"
            "de" -> "Erkennungsfehler"
            else -> "Recognition error"
        }
    }

    @Stable
    fun startingInAMoment() : String {
        return when (locale) {
            "ru" -> "Начнется через мгновение..."
            "fr-FR" -> "Commencera dans un instant..."
            "es" -> "Comenzará en un momento..."
            "pt-PT" -> "Começará em um momento..."
            "pt-BR" -> "Começará em um momento..."
            "it" -> "Inizierà tra un momento..."
            "nl" -> "Begint over een ogenblik..."
            "de" -> "Beginnt in einem Moment..."
            else -> "Starting in a moment..."
        }
    }

    @Stable
    fun loading() : String {
        return when (locale) {
            "ru" -> "Загрузка..."
            "fr-FR" -> "Chargement..."
            "es" -> "Cargando..."
            "pt-PT" -> "Carregando..."
            "pt-BR" -> "Carregando..."
            "it" -> "Caricamento..."
            "nl" -> "Laden..."
            "de" -> "Laden..."
            else -> "Loading..."
        }
    }

    @Stable
    fun initializing() : String {
        return when (locale) {
            "ru" -> "Инициализация..."
            "fr-FR" -> "Initialisation..."
            "es" -> "Inicializando..."
            "pt-PT" -> "Inicializando..."
            "pt-BR" -> "Inicializando..."
            "it" -> "Inizializzazione..."
            "nl" -> "Initialiseren..."
            "de" -> "Initialisierung..."
            else -> "Initializing..."
        }
    }

    @Stable
    fun noMicPermission() : String {
        return when (locale) {
            "ru" -> "Нет разрешения на микрофон"
            "fr-FR" -> "Pas de permission pour le microphone"
            "es" -> "Sin permiso para el micrófono"
            "pt-PT" -> "Sem permissão para o microfone"
            "pt-BR" -> "Sem permissão para o microfone"
            "it" -> "Nessun permesso per il microfono"
            "nl" -> "Geen microfoonrechten"
            "de" -> "Keine Mikrofonberechtigung"
            else -> "No microphone permission"
        }
    }

    @Stable
    fun authentication(): String {
        return when (locale) {
            "en" -> "You are not authenticated.\nSign in to your account to start using this keyboard."
            "ru" -> "Вы не аутентифицированы.\nВойдите в свою учетную запись, чтобы начать использовать эту клавиатуру."
            "fr-FR" -> "Vous n'êtes pas authentifié.\nConnectez-vous à votre compte pour commencer à utiliser ce clavier."
            "es" -> "No estás autenticado.\nInicia sesión en tu cuenta para comenzar a usar este teclado."
            "pt-PT" -> "Você não está autenticado.\nFaça login na sua conta para começar a usar este teclado."
            "pt-BR" -> "Você não está autenticado.\nFaça login na sua conta para começar a usar este teclado."
            "it" -> "Non sei autenticato.\nAccedi al tuo account per iniziare a utilizzare questa tastiera."
            "nl" -> "Je bent niet geauthenticeerd.\nMeld je aan bij je account om deze toetsenbord te gaan gebruiken."
            "de" -> "Sie sind nicht authentifiziert.\nMelden Sie sich bei Ihrem Konto an, um mit dieser Tastatur zu beginnen."
            else -> "You are not authenticated.\nSign in to your account to start using this keyboard."
        }
    }

    @Stable
    fun clear(): String {
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
    fun back(): String {
        return when (locale) {
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