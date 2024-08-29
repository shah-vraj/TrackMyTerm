package com.trackmyterm.util.components

import android.content.Context
import androidx.core.content.edit
import com.trackmyterm.R
import com.trackmyterm.util.Constants.AUTH_TOKEN_KEY
import com.trackmyterm.util.Constants.ONBOARDING_COMPLETED_KEY
import com.trackmyterm.util.Constants.REMEMBERED_EMAIL_KEY
import com.trackmyterm.util.Constants.USER_LOGGED_IN_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface AppSharedPreferences {

    fun isOnboardingCompleted(): Boolean

    fun setOnboardingCompleted(isCompleted: Boolean = true)

    fun isUserLoggedIn(): Boolean

    fun setUserLoggedIn(isLoggedIn: Boolean)

    fun getAuthToken(): String?

    fun setAuthToken(token: String)

    fun getRememberedEmail(): String?

    fun setRememberedEmail(email: String)
}

@Singleton
class AppSharedPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context
): AppSharedPreferences {
    private val prefs =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    override fun isOnboardingCompleted(): Boolean =
        prefs.getBoolean(ONBOARDING_COMPLETED_KEY, false)

    override fun setOnboardingCompleted(isCompleted: Boolean) {
        prefs.edit { putBoolean(ONBOARDING_COMPLETED_KEY, isCompleted) }
    }

    override fun isUserLoggedIn(): Boolean =
        prefs.getBoolean(USER_LOGGED_IN_KEY, false)

    override fun setUserLoggedIn(isLoggedIn: Boolean) {
        prefs.edit { putBoolean(USER_LOGGED_IN_KEY, isLoggedIn) }
    }

    override fun getAuthToken(): String? =
        prefs.getString(AUTH_TOKEN_KEY, null)

    override fun setAuthToken(token: String) {
        prefs.edit { putString(AUTH_TOKEN_KEY, token) }
    }

    override fun getRememberedEmail(): String? =
        prefs.getString(REMEMBERED_EMAIL_KEY, null)

    override fun setRememberedEmail(email: String) {
        prefs.edit { putString(REMEMBERED_EMAIL_KEY, email) }
    }
}