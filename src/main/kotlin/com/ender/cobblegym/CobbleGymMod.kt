package com.ender.cobblegym

import com.example.cobblegym.command.registerGymLeaderCommand
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.text.LiteralText
import org.slf4j.LoggerFactory

object CobbleGymMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("CobbleGym")

    override fun onInitialize() {
        logger.info("CobbleGym is initializing...")

        CommandRegistrationCallback.EVENT.register { dispatcher: CommandDispatcher<ServerCommandSource>, _, _ ->

            // Register existing /gymleader command
            dispatcher.register(registerGymLeaderCommand())

            // Register /gymleaders command
            dispatcher.register(
                    literal("gymleaders").executes { ctx ->
                        listGymLeaders(ctx.source)
                        1
                    }
            )

            // Register /badges command
            dispatcher.register(
                    literal("badges").executes { ctx ->
                        listBadges(ctx.source)
                        1
                    }
            )
        }

        GymLeaderStorage.load()

        logger.info("CobbleGym initialized successfully!")
    }

    private fun listGymLeaders(source: ServerCommandSource) {
        val gymLeaders = GymLeaderStorage.getAllGymLeaders() // You need to implement this method

        if (gymLeaders.isEmpty()) {
            source.sendFeedback(LiteralText("No gym leaders found."), false)
        } else {
            source.sendFeedback(LiteralText("Current Gym Leaders:"), false)
            gymLeaders.forEach { leader ->
                source.sendFeedback(LiteralText("- ${leader.name}"), false)
            }
        }
    }

    private fun listBadges(source: ServerCommandSource) {
        val badges = GymLeaderStorage.getAllBadges() // Implement this method to get badge names or info

        if (badges.isEmpty()) {
            source.sendFeedback(LiteralText("No badges found."), false)
        } else {
            source.sendFeedback(LiteralText("Available Badges:"), false)
            badges.forEach { badge ->
                source.sendFeedback(LiteralText("- $badge"), false)
            }
        }
    }
}