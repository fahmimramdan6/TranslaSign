package com.dicoding.translasign.data.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DictionaryData(
    val name: String?,
    val description: String?,
    val photo: String,
) : Parcelable {
    companion object {
        val alphabet = listOf(
            DictionaryData(
                "A",
                "This is the American Sign Language for A",
                "https://storage.googleapis.com/translasign/handsign_font/A.png"
            ),
            DictionaryData(
                "B",
                "This is the American Sign Language for B",
                "https://storage.googleapis.com/translasign/handsign_font/B.png"
            ),
            DictionaryData(
                "C",
                "This is the American Sign Language for C",
                "https://storage.googleapis.com/translasign/handsign_font/C.png"
            ),
            DictionaryData(
                "D",
                "This is the American Sign Language for D",
                "https://storage.googleapis.com/translasign/handsign_font/D.png"
            ),
            DictionaryData(
                "E",
                "This is the American Sign Language for E",
                "https://storage.googleapis.com/translasign/handsign_font/E.png"
            ),
            DictionaryData(
                "F",
                "This is the American Sign Language for F",
                "https://storage.googleapis.com/translasign/handsign_font/F.png"
            ),
            DictionaryData(
                "G",
                "This is the American Sign Language for G",
                "https://storage.googleapis.com/translasign/handsign_font/G.png"
            ),
            DictionaryData(
                "H",
                "This is the American Sign Language for H",
                "https://storage.googleapis.com/translasign/handsign_font/H.png"
            ),
            DictionaryData(
                "I",
                "This is the American Sign Language for I",
                "https://storage.googleapis.com/translasign/handsign_font/I.png"
            ),
            DictionaryData(
                "J",
                "This is the American Sign Language for J",
                "https://storage.googleapis.com/translasign/handsign_font/J.png"
            ),
            DictionaryData(
                "K",
                "This is the American Sign Language for K",
                "https://storage.googleapis.com/translasign/handsign_font/K.png"
            ),
            DictionaryData(
                "L",
                "This is the American Sign Language for L",
                "https://storage.googleapis.com/translasign/handsign_font/L.png"
            ),
            DictionaryData(
                "M",
                "This is the American Sign Language for M",
                "https://storage.googleapis.com/translasign/handsign_font/M.png"
            ),
            DictionaryData(
                "N",
                "This is the American Sign Language for N",
                "https://storage.googleapis.com/translasign/handsign_font/N.png"
            ),
            DictionaryData(
                "O",
                "This is the American Sign Language for O",
                "https://storage.googleapis.com/translasign/handsign_font/O.png"
            ),
            DictionaryData(
                "P",
                "This is the American Sign Language for P",
                "https://storage.googleapis.com/translasign/handsign_font/P.png"
            ),
            DictionaryData(
                "Q",
                "This is the American Sign Language for Q",
                "https://storage.googleapis.com/translasign/handsign_font/Q.png"
            ),
            DictionaryData(
                "R",
                "This is the American Sign Language for R",
                "https://storage.googleapis.com/translasign/handsign_font/R.png"
            ),
            DictionaryData(
                "S",
                "This is the American Sign Language for S",
                "https://storage.googleapis.com/translasign/handsign_font/S.png"
            ),
            DictionaryData(
                "T",
                "This is the American Sign Language for T",
                "https://storage.googleapis.com/translasign/handsign_font/T.png"
            ),
            DictionaryData(
                "U",
                "This is the American Sign Language for U",
                "https://storage.googleapis.com/translasign/handsign_font/U.png"
            ),
            DictionaryData(
                "V",
                "This is the American Sign Language for V",
                "https://storage.googleapis.com/translasign/handsign_font/V.png"
            ),
            DictionaryData(
                "W",
                "This is the American Sign Language for W",
                "https://storage.googleapis.com/translasign/handsign_font/W.png"
            ),
            DictionaryData(
                "X",
                "This is the American Sign Language for X",
                "https://storage.googleapis.com/translasign/handsign_font/X.png"
            ),
            DictionaryData(
                "Y",
                "This is the American Sign Language for Y",
                "https://storage.googleapis.com/translasign/handsign_font/Y.png"
            ),
            DictionaryData(
                "Z",
                "This is the American Sign Language for Z",
                "https://storage.googleapis.com/translasign/handsign_font/Z.png"
            )
        )
    }
}
