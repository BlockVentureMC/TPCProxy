package de.themeparkcraft.proxy.commands

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import de.themeparkcraft.proxy.ProxyPlugin
import de.themeparkcraft.proxy.extensions.text

/**
 * Represents a command that broadcasts a message to all players on the server.
 *
 * @param proxy The proxy server instance.
 */
class BroadcastCommand(private val proxy: ProxyServer) : SimpleCommand {

    /**
     * Executes the broadcast command by sending a message to all players on the server.
     *
     * @param invocation The command invocation.
     */
    override fun execute(invocation: SimpleCommand.Invocation?) {
        if (invocation == null) return

        val message = invocation.arguments().joinToString(" ")
        val textComp = text("${ProxyPlugin.PREFIX}$message")

        proxy.allPlayers.forEach { it.sendMessage(textComp) }
    }

    /**
     * Returns a list of suggestions for the given command invocation.
     *
     * @param invocation The command invocation.
     * @return The list of suggestions.
     */
    override fun suggest(invocation: SimpleCommand.Invocation?): MutableList<String> {
        return mutableListOf()
    }

    /**
     * Checks if the invocation source has the permission "ventureproxy.broadcast".
     *
     * @param invocation The invocation representing the command execution.
     * @return true if the source has the permission, false otherwise.
     */
    override fun hasPermission(invocation: SimpleCommand.Invocation?): Boolean {
        if (invocation == null) return true
        if (invocation.source() !is Player) return true

        val player = invocation.source() as Player
        return player.hasPermission("ventureproxy.broadcast")
    }
}