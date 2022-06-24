package androidx.essentials.playground

import androidx.essentials.network.ThreadPolicyBuilder
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executor

class ThreadPolicy(executor: Executor) : ThreadPolicyBuilder({
    penaltyListener(executor) {
        Firebase.crashlytics.recordException(it)
    }
    penaltyLog()
    detectAll()
})