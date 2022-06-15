package androidx.essentials.view

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import kotlin.reflect.KClass

fun <I, O> getActivityResultContract(
    klass: KClass<*>,
    createIntent: (Intent.(I) -> Unit)? = null,
    parseResult: ((Int, Intent?) -> O)? = null,
) = object : ActivityResultContract<I, O?>() {
    override fun createIntent(context: Context, input: I): Intent {
        return Intent(context, klass.java).also {
            createIntent?.invoke(it, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): O? {
        return parseResult?.invoke(resultCode, intent)
    }
}

fun getActivityResultContract(
    klass: KClass<*>
) = getActivityResultContract<Any?, Any?>(klass)