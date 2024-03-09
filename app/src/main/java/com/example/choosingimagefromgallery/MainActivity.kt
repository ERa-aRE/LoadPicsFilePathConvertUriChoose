package com.example.choosingimagefromgallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.choosingimagefromgallery.ui.theme.ChoosingImageFromGalleryTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChoosingImageFromGalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PhotoSelectorView(maxSelectionCount = 3)
                    DisplayImage()
                }
            }
        }
    }
}

@Composable
fun DisplayImage() {
    val stepImage = "1121.jpg"
    val imgFile = File("/storage/emulated/0/Download/Z.jpg")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Image from File Path in Android",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
        )

        Image(
            painter = rememberAsyncImagePainter(model = File("/storage/emulated/0/Download/DrillApp/$stepImage")),
            contentDescription = "Image",
            modifier = Modifier
                .clickable {}
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = rememberAsyncImagePainter(model = File("/storage/emulated/0/Download/DrillApp/1028.jpg")),
            contentDescription = "Image",
            modifier = Modifier
                .clickable {}
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 8.dp, topEnd = 8.dp)),
            contentScale = ContentScale.FillBounds
        )

        val path = "/storage/emulated/0/Download/DrillApp/1085.jpg"
        Image(
            painter = rememberAsyncImagePainter(model = File(path)),
            contentDescription = "Image",
            modifier = Modifier
                .clickable {}
                .fillMaxHeight()
        )
    }
}























@Composable
fun PhotoSelectorView(maxSelectionCount: Int = 1) {
    var selectedImages by remember {
        mutableStateOf<List<Uri?>>(emptyList())
    }

    val buttonText = if (maxSelectionCount > 1) {
        "Select up to $maxSelectionCount photos"
    } else {
        "Select a photo"
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImages = listOf(uri) }
    )

    // I will start this off by saying that I am still learning Android development:
    // We are tricking the multiple photos picker here which is probably not the best way,
    // if you know of a better way to implement this feature drop a comment and let me know
    // how to improve this design
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = if (maxSelectionCount > 1) {
            maxSelectionCount
        } else {
            2
        }),
        onResult = { uris -> selectedImages = uris }
    )

    fun launchPhotoPicker() {
        if (maxSelectionCount > 1) {
            multiplePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            launchPhotoPicker()
        }) {
            Text(buttonText)
        }

        ImageLayoutView(selectedImages = selectedImages)
    }
}

@Composable
fun ImageLayoutView(selectedImages: List<Uri?>) {
    LazyRow {
        this.items(selectedImages) { uri ->
            Log.d("uris","$uri")
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

