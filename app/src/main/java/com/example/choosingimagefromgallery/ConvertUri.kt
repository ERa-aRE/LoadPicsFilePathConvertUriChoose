package com.example.choosingimagefromgallery

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore

fun getFilePathFromUri(contentResolver: Context, uri: Uri): String? {
    var filePath: String? = null

    val isKitkat = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT

    if (isKitkat && DocumentsContract.isDocumentUri(contentResolver, uri)) {
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            val type = split[0]

            if ("primary".equals(type, ignoreCase = true)) {
                filePath = "${contentResolver.getExternalFilesDir(null)?.absolutePath}/${split[1]}"
            }
        } else if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), id.toLong()
            )
            filePath = getDataColumn(contentResolver.contentResolver, contentUri, null, null)
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            val type = split[0]

            var contentUri: Uri? = null
            when (type) {
                "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])

            filePath = getDataColumn(contentResolver.contentResolver, contentUri!!, selection, selectionArgs)
        }
    } else if ("content".equals(uri.scheme, ignoreCase = true)) {
        filePath = getDataColumn(contentResolver.contentResolver, uri, null, null)
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        filePath = uri.path
    }

    return filePath
}

fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

fun getDataColumn(contentResolver: ContentResolver, uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)

    try {
        cursor = contentResolver.query(uri, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(columnIndex)
        }
    } finally {
        cursor?.close()
    }
    return null
}