package de.themeparkcraft.proxy.commands

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import de.themeparkcraft.proxy.extensions.text

class HubCommand(private val proxy: ProxyServer) : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation?) {
        if (invocation == null) return
        if (invocation.source() !is Player) return

        val player = invocation.source() as Player
        val server = proxy.getServer("hub")

        if (!server.isPresent) {
            player.sendMessage(text("<red>The hub is offline!"))
            return
        }

        if (player.currentServer.get() == server.get()) {
            player.sendMessage(text("<red>You are already on the hub!"))
            return
        }

        player.createConnectionRequest(server.get()).fireAndForget()
        player.sendMessage(text("You have been sent to the hub!"))
    }

    override fun suggest(invocation: SimpleCommand.Invocation?): MutableList<String> {
        return mutableListOf()
    }

}