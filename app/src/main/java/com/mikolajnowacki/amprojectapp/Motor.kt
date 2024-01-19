package com.mikolajnowacki.amprojectapp

data class Motor(
    var name: String = "Motor",
    var voltageRating: String = "0",
    var maxRPM: String = "0",
    var currentRPM: String = "0",
    var maxLoad: String = "N/A",
    var status: String = "offline"
)
