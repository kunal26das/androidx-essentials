package androidx.essentials.playground.network

import androidx.essentials.network.BuildConfig
import androidx.essentials.network.VmPolicyBuilder
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executor

class VmPolicy(executor: Executor) : VmPolicyBuilder({
    penaltyListener(executor) {
        Firebase.crashlytics.recordException(it)
    }
    if (BuildConfig.DEBUG) penaltyLog()
    detectAll()
})