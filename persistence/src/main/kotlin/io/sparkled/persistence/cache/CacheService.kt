package io.sparkled.persistence.cache

import java.util.concurrent.atomic.AtomicInteger

interface CacheService {
    val brightness: AtomicInteger
}
