package com.ender.cobblegymmod.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.arguments.StringArgumentType.word
import com.mojang.brigadier.context.CommandContext
import com.yourname.cobblegymmod.data.GymLeader
import com.yourname.cobblegymmod.data.GymLeaderStorage
import net.minecraft.command.CommandSource
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object RegisterGymLeaderCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
                CommandManager.literal("setgymleader")
                        .then(CommandManager.argument("city", word())
                                .executes { ctx -> setGymLeader(ctx, StringArgumentType.getString(ctx, "city")) })
        )
    }

    private fun setGymLeader(context: CommandContext<ServerCommandSource>, city: String): Int {
        val source = context.source
        val player: ServerPlayerEntity = source.playerOrThrow

        val gymLeader = GymLeader(
                playerName = player.entityName,
                city = city,
                badgeImage = Identifier("minecraft", "textures/item/diamond.png"), // placeholder
                rewardItems = listOf(player.mainHandStack.copy()), // example: whatever item they hold
                gymLocation = player.blockPos
        )

        GymLeaderStorage.addGymLeader(gymLeader)
        GymLeaderStorage.save()

        source.sendFeedback(
                { Text.of("✅ ${player.entityName} set as Gym Leader of $city!") },
                false
        )

        return 1
    }
}