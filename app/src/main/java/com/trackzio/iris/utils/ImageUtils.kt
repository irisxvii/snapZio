package com.trackzio.iris.utils

import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream

data class CompressionResult(
    val compressedFile: File,
    val originalSizeKb: Int,
    val compressedSizeKb: Int
)

fun compressImage(
    imagePath: String
): CompressionResult {

    val originalFile = File(imagePath)

    val bitmap =
        BitmapFactory.decodeFile(imagePath)

    val compressedFile =
        File(
            originalFile.parent,
            "compressed_${originalFile.name}"
        )

    FileOutputStream(compressedFile).use {
        bitmap.compress(
            android.graphics.Bitmap.CompressFormat.JPEG,
            40,
            it
        )
    }

    val originalSizeKb =
        (originalFile.length() / 1024).toInt()

    val compressedSizeKb =
        (compressedFile.length() / 1024).toInt()

    return CompressionResult(
        compressedFile,
        originalSizeKb,
        compressedSizeKb
    )
}