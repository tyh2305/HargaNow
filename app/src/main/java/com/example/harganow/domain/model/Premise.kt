package com.example.harganow.domain.model

data class Premise(
    val id: String,
    val name: String,
    val type: Place,
    val state: State,
    val district: String,
) {
    companion object {
        enum class Place {
            PASAR_BASAH,
            PASAR_RAYA,
            KEDAI_RUNCIT,
            PASAR_MINI,
            RESTORAN_INDIA_MUSLIM,
            RESTORAN_CINA,
            HYPERMARKET,
            RESTORAN_MELAYU,
            MEDAN_SELERA,
            FOODCOURT
        }

        fun placeToString(place: Place): String {
            return when (place) {
                Place.PASAR_BASAH -> "Pasar Basah"
                Place.PASAR_RAYA -> "Pasar Raya / Supermarket"
                Place.KEDAI_RUNCIT -> "Kedai Runcit"
                Place.PASAR_MINI -> "Pasar Mini"
                Place.RESTORAN_INDIA_MUSLIM -> "Restoran India Muslim"
                Place.RESTORAN_CINA -> "Restoran Cina"
                Place.HYPERMARKET -> "Hypermarket"
                Place.RESTORAN_MELAYU -> "Restoran Melayu"
                Place.MEDAN_SELERA -> "Medan Selera"
                Place.FOODCOURT -> "Foodcourt"
            }
        }

        enum class State {
            PERAK,
            MELAKA,
            PUTRAJAYA,
            PERLIS,
            NEGERI_SEMBILAN,
            KUALA_LUMPUR,
            SELANGOR,
            SABAH,
            LABUAN,
            KELANTAN,
            KEDAH,
            SARAWAK,
            JOHOR
        }

        fun stateToString(state: State): String {
            return when (state) {
                State.PERAK -> "Perak"
                State.MELAKA -> "Melaka"
                State.PUTRAJAYA -> "W.P. Putrajaya"
                State.PERLIS -> "Perlis"
                State.NEGERI_SEMBILAN -> "Negeri Sembilan"
                State.KUALA_LUMPUR -> "W.P. Kuala Lumpur"
                State.SELANGOR -> "Selangor"
                State.SABAH -> "Sabah"
                State.LABUAN -> "W.P. Labuan"
                State.KELANTAN -> "Kelantan"
                State.KEDAH -> "Kedah"
                State.SARAWAK -> "Sarawak"
                State.JOHOR -> "Johor"
            }
        }
    }
}

