package io.sparkled.persistence.cache

import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Singleton

@Singleton
class CacheServiceImpl : CacheService {
    override var brightness = AtomicInteger(0)
}
