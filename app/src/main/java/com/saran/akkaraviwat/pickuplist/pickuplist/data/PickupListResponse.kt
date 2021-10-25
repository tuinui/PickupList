package com.saran.akkaraviwat.pickuplist.pickuplist.data

import com.google.gson.annotations.SerializedName

data class PickupListResponse(
    @SerializedName("pickup")
    val pickupList: List<Pickup>
) {
    data class Pickup(
        @SerializedName("id_country")
        val countryId: Long?,
        @SerializedName("alias")
        val alias: String?,
        @SerializedName("address1")
        val address1: String?,
        @SerializedName("city")
        val city: String?,
        @SerializedName("active")
        val active: Boolean?,
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?
    )
}
