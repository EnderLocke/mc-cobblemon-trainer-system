package com.example.cobblegym

object GymLeaderManager {
    private val gymLeaders = mutableListOf<GymLeader>()

    fun registerLeader(leader: GymLeader) {
        gymLeaders.removeIf { it.city == leader.city }
        gymLeaders.add(leader)
    }

    fun getLeaderByCity(city: String): GymLeader? {
        return gymLeaders.find { it.city == city }
    }

    fun getAllLeaders(): List<GymLeader> = gymLeaders
}