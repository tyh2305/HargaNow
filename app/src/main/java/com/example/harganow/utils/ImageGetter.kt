package com.example.harganow.utils

class ImageGetter {
    fun GetImage(id: String): String {
        return "https://myharga.kpdn.gov.my/myharga/mobile/apps/" + id + ".png"
    }

    companion object {
        fun GetImage(id: String): String {
            return "https://myharga.kpdn.gov.my/myharga/mobile/apps/" + id + ".png"
        }
    }
}