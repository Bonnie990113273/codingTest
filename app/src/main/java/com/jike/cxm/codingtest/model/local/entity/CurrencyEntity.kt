package com.jike.cxm.codingtest.model.local.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_entity")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true)
    val nId: Int = 0,

    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "name")
    val name: String, // name 1

    @ColumnInfo(name = "symbol")
    val symbol: String// symbol 1
)