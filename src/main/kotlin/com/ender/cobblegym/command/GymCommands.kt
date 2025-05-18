package com.ender.cobblegym.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.LiteralText
import net.minecraft.command.CommandSource

object GymCommands {

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
                literal("gymleaders")
                        .executes { ctx -> listGymLeaders(ctx) }
        )

        dispatcher.register(
                literal("badges")
                        .executes { ctx -> listBadges(ctx) }
        )
    }

    private fun listGymLeaders(ctx: CommandContext<ServerCommandSource>): Int {
        val source = ctx.source

        // TODO: Replace with your actual gym leaders list retrieval
        val gymLeaders = listOf("Alice", "Bob", "Charlie")

        if (gymLeaders.isEmpty()) {
            source.sendFeedback(LiteralText("No gym leaders found."), false)
        } else {
            source.sendFeedback(LiteralText("Current Gym Leaders:"), false)
            gymLeaders.forEach { leader ->
                source.sendFeedback(LiteralText("- $leader"), false)
            }
        }
        return 1
    }

    private fun listBadges(ctx: CommandContext<ServerCommandSource>): Int {
        val source = ctx.source

        // TODO: Replace with your actual badges list retrieval
        val badges = listOf("Alice's Badge", "Bob's Badge", "Charlie's Badge")

        if (badges.isEmpty()) {
            source.sendFeedback(LiteralText("No badges found."), false)
        } else {
            source.sendFeedback(LiteralText("Available Badges:"), false)
            badges.forEach { badge ->
                source.sendFeedback(LiteralText("- $badge"), false)
            }
        }
        return 1
    }
}