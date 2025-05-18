package com.ender.cobblegym.storage

import com.example.cobblegym.GymLeader
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import java.io.File
import java.lang.reflect.Type

object GymLeaderStorage {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val file = File("config/cobblegym/gym_leaders.json")

    init {
        if (!file.parentFile.exists()) file.parentFile.mkdirs()
    }

    fun save(gymLeaders: List<GymLeader>) {
        val json = gson.toJson(gymLeaders.map { serializeGymLeader(it) })
        file.writeText(json)
    }

    fun load(): List<GymLeader> {
        if (!file.exists()) return emptyList()
        val type: Type = object : TypeToken<List<Map<String, Any>>>() {}.type
        val data: List<Map<String, Any>> = gson.fromJson(file.readText(), type)
        return data.mapNotNull { deserializeGymLeader(it) }
    }

    private fun serializeGymLeader(leader: GymLeader): Map<String, Any> = mapOf(
            "playerName" to leader.playerName,
            "city" to leader.city,
            "badgeName" to leader.badgeName,
            "badgeImage" to leader.badgeImage.toString(),
            "rewardItems" to leader.rewardItems.map { it.writeNbt(NbtCompound()).toString() },
            "gymLocation" to listOf(leader.gymLocation.x, leader.gymLocation.y, leader.gymLocation.z)
    )

    private fun deserializeGymLeader(data: Map<String, Any>): GymLeader? {
        try {
            val posList = data["gymLocation"] as List<Double>
            val pos = BlockPos(posList[0].toInt(), posList[1].toInt(), posList[2].toInt())
            val items = (data["rewardItems"] as List<String>).map {
                ItemStack.fromNbt(NbtCompound().apply {
                    net.minecraft.nbt.NbtHelper.fromString(it)?.let { compound ->
                        putAll(compound)
                    }
                })
            }
            return GymLeader(
                    playerName = data["playerName"] as String,
                    city = data["city"] as String,
                    badgeName = data["badgeName"] as String,
                    badgeImage = Identifier(data["badgeImage"] as String),
                    rewardItems = items,
                    gymLocation = pos
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}