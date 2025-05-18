package com.ender.cobblegym.data

import net.minecraft.util.Identifier
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

data class GymLeader(
        val playerName: String,
        val city: String,
        val badgeName: String = "${playerName}'s Badge",
        val badgeImage: Identifier,
        val rewardItems: List<ItemStack>,
        val gymLocation: BlockPos
)