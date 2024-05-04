package de.themeparkcraft.proxy

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import de.themeparkcraft.proxy.commands.BroadcastCommand
import de.themeparkcraft.proxy.commands.HubCommand
import de.themeparkcraft.proxy.listener.PingListener
import org.slf4j.Logger

@Plugin(
    id = "tpc-proxy",
    name = "TPC Proxy",
    version = "1.0",
    description = "The TPC proxy plugin",
    authors = ["Liam Sage"],
)
class ProxyPlugin @Inject constructor(val server: ProxyServer, private val logger: Logger) {

    companion object {
        lateinit var instance: ProxyPlugin
        val PREFIX: String =
            "<gradient:#f6e58d:#ffda79>ThemeParkCraft</gradient> <color:#576574>•</color> <color:#c8d6e5>"
    }

    init {
        instance = this
    }

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        logger.info("Initializing proxy... $event")

        val commandManager = server.commandManager
        val eventManager = server.eventManager

        commandManager.register(
            commandManager
                .metaBuilder("hub")
                .aliases("lobby", "l", "h")
                .build(),
            HubCommand(server)
        )

        commandManager.register(
            commandManager
                .metaBuilder("broadcast")
                .aliases("bc")
                .build(),
            BroadcastCommand(server)
        )

        eventManager.register(this, PingListener())
    }

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent) {
        logger.info("Proxy shutting down! $event")
    }

    fun getPluginAnnotation(): Plugin {
        return this::class.java.getAnnotation(Plugin::class.java)
    }
}