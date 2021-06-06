package com.example.mywallet.model

import android.R
import android.graphics.Color
import android.os.Parcelable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.widget.TextView
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.facebook.stetho.json.annotation.JsonProperty
import com.facebook.stetho.json.annotation.JsonValue
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Currencies",primaryKeys = ["base","counter"])
@Parcelize
data class Currency(
    var base: String = "",
    var counter: String = "",
    var buy_price: String? = "",
    var sell_price: String? = "",
    var icon: String? = "",
    var name: String? = "",
    var is_favorite: Boolean = false
) : Parcelable

//fun Currency.getPairCurrency(): SpannableString {
//    val source = "$base /$counter"
//    val res = SpannableString(source)
//    res.setSpan(RelativeSizeSpan(1.5f), 0, source.indexOfFirst { it == '/' }, 0) // set size
//
//    res.setSpan(ForegroundColorSpan(Color.WHITE), 0, source.indexOfFirst { it == '/' }, 0)
//     // set color
//
//    return res
//}