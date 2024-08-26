package com.trackmyterm.util.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 * Launch activity extension with optional bundles and finishes current activity.
 * @receiver Context
 * @param options Bundle?
 * @param init Intent.() -> Unit
 */
inline fun <reified T : Any> Activity.launchActivityAndFinish(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent, options)
    finish()
}