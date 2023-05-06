package com.example.harganow.domain.model

import com.google.firebase.firestore.DocumentId

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

enum class District {
    WANGSA_MAJU,
}

fun districtToString(district: District): String {
    return when (district) {
        District.WANGSA_MAJU -> "Wangsa Maju"
    }
}

enum class PremiseName{
    AEON_BIG_DANAU_KOTA,
    AEON_BIG_WANGSA_MAJU,
    AEON_WANGSA_MAJU,
    MYDIN_DANAU_SAUJANA,
    ECONSAVE_SETAPAK_CENTRAL
}

fun districtToString(premiseName: PremiseName): String {
    return when (premiseName){
        PremiseName.AEON_BIG_DANAU_KOTA -> "AEON BIG ( DANAU KOTA )"
        PremiseName.AEON_BIG_WANGSA_MAJU -> "AEON BIG ( WANGSA MAJU )"
        PremiseName.AEON_WANGSA_MAJU -> "AEON ( WANGSA MAJU )"
        PremiseName.MYDIN_DANAU_SAUJANA -> "MY MYDIN ( DANAU SAUJANA )"
        PremiseName.ECONSAVE_SETAPAK_CENTRAL -> "ECONSAVE CASH AND CARRY ( SETAPAK CENTRAL )"
    }
}

data class Premise(
    @DocumentId
    val id: String,
    val premise: String,
    val premise_type: String,
    val state: String,
    val district: String,
) {
    constructor() : this(
        "", "", "", "", ""
    )
}

