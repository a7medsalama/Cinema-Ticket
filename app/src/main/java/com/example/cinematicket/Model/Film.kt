package com.example.cinematicket.Model

import android.os.Parcel
import android.os.Parcelable

data class Film(
    var Title: String? = null,
    var Trailer: String? = null,
    var Description: String? = null,
    var Imdb: Double = 0.0,
    var Poster: String? = null,
    var Time: String? = null,
    var Year: Int = 0,
    var Price: Int = 0,
    var Genre: ArrayList<String> = arrayListOf(),  // Default empty list
    var Casts: ArrayList<Cast> = arrayListOf()     // Default empty list
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.createTypedArrayList(Cast.CREATOR) ?: arrayListOf()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Title)
        parcel.writeString(Trailer)
        parcel.writeString(Description)
        parcel.writeDouble(Imdb)
        parcel.writeString(Poster)
        parcel.writeString(Time)
        parcel.writeInt(Year)
        parcel.writeInt(Price)
        parcel.writeStringList(Genre)
        parcel.writeTypedList(Casts)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Film> {
        override fun createFromParcel(parcel: Parcel): Film {
            return Film(parcel)
        }

        override fun newArray(size: Int): Array<Film?> {
            return arrayOfNulls(size)
        }
    }
}
