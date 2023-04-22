package com.example.harganow.domain.model

import com.example.harganow.data.auth.FireAuthRepository
import com.google.firebase.firestore.DocumentId

data class AppUser(
    @DocumentId
    var id: String?,
    var name: String? = "Anonymous",
    var email: String? = "no-reply@harganow.com",
) {
    constructor() : this(null, null, null)

    fun fromAuthContext(authRepository: FireAuthRepository): AppUser {
        var user = authRepository.getCurrentUser()
        var appUser = AppUser()
        if (user != null) {
            appUser.id = user.uid
            appUser.name = user.displayName
            appUser.email = user.email
        }
        return appUser
    }
}



