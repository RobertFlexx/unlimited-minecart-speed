package dev.robert.unlimitedminecarts

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

private const val MOD_ID = "unlimited_minecart_speed"

const val STUPID_HIGH_MINECART_GAMERULE_MAX: Int = 1_000_000_000

class UnlimitedMinecartSpeed : ModInitializer {
    override fun onInitialize() {
        LOGGER.info(
            "Unlimited Minecart Speed loaded. /gamerule minecartMaxSpeed now accepts values up to {}.",
            STUPID_HIGH_MINECART_GAMERULE_MAX
        )
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MOD_ID)
    }
}
