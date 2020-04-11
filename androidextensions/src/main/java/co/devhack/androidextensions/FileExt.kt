@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package co.devhack.androidextensions

import android.content.Context
import android.graphics.Bitmap
import id.zelory.compressor.Compressor
import java.io.File

fun File.compressImage(context: Context, pathUriImages: String): File? =
    File("${this.parent}/$pathUriImages").let {
        it.mkdirs()

        val imageCompressed: File? = Compressor(context)
            .setMaxWidth(640)
            .setMaxHeight(480)
            .setQuality(60)
            .setCompressFormat(Bitmap.CompressFormat.JPEG)
            .setDestinationDirectoryPath(this.parent + "/imgsComprimidas")
            .compressToFile(this)

        this.delete()

        imageCompressed
    }