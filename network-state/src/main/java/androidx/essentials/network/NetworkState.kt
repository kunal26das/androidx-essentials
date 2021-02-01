/*
 * Copyright (c) 2020.
 */

package androidx.essentials.network

enum class NetworkState {
    LOST,
    LOSING,
    AVAILABLE,
    UNAVAILABLE,
    CAPABILITIES_CHANGED,
    BLOCKED_STATUS_CHANGED,
    LINK_PROPERTIES_CHANGED,
}