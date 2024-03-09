package com.example.choosingimagefromgallery

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest

@Composable
fun Profile_setup(
    imageUriState: MutableState<Uri?>,
    selectImageLauncher: ActivityResultLauncher<String>,uriString: String
) {

    val context = LocalContext.current
    val imageLoader = ImageLoader(context)
    val imageUri = Uri.parse(uriString)
    val request = ImageRequest.Builder(context).data(imageUri).build()
    val painter = rememberImagePainter(
        request = request,
        imageLoader = imageLoader
    )

    Box(
        modifier = Modifier.background(color = Color.White)
    ) {

        if (imageUriState.value != null) {

            val uriImage = rememberAsyncImagePainter(model = if(uriString != ""){imageUri} else imageUriState.value!!)

            Image(
                painter = uriImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,//for auto cropping the image to square
                modifier = Modifier
                    .clip(CircleShape)
                    .size(200.dp)
                    .border(2.dp, Color(0xFF095A6F), CircleShape)
                    .clickable { selectImageLauncher.launch("image/*") }

            )
        } else Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
            contentDescription = null, contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxHeight()
                .clip(CircleShape)
                .size(200.dp)
                .clickable {selectImageLauncher.launch("image/*") }
        )
        /*Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )*/
    }

}
