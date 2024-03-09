package com.example.choosingimagefromgallery


/*
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.firedrillb.R
import com.example.firedrillb.domain.models.Arzyab
import com.example.firedrillb.presentation.components.CustomTextField
import com.example.firedrillb.presentation.components.Profile_setup
import com.example.firedrillb.presentation.components.uriToFilePath
import com.example.firedrillb.presentation.modiriate_arzyabha.tarif_arzyab.TarifArzyabEvent
import com.example.firedrillb.presentation.modiriate_arzyabha.tarif_arzyab.tarif_arzyab_viewmodel
import com.example.firedrillb.presentation.navigation.Screen
import com.example.firedrillb.util.checkText
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
fun tarif_arzyab(
    navController : NavController,
    imageUriState: MutableState<Uri?>,
    selectImageLauncher: ActivityResultLauncher<String>,
    viewModel:tarif_arzyab_viewmodel= hiltViewModel(),
    arzyabid:Int?,
    which_page:Int?
) {

    val shape = RoundedCornerShape(15.dp)
    val focusManager = LocalFocusManager.current
    val maxCharMeli = 10
    val maxCharPhoneNumber = 11
    val state=viewModel._stateId.value
    var arzyabName by remember { mutableStateOf("") }
    var codeMeli_Arzyab by remember { mutableStateOf("") }
    var phoneNumber_Arzyab by remember { mutableStateOf("") }
    var userName_Arzyab by remember { mutableStateOf("") }
    var password_Arzyab by remember { mutableStateOf("") }
    var idFake by remember{ mutableStateOf(0) }
    val mutableUriState: MutableState<Uri?> = remember { mutableStateOf(null) }
    var openDialogErrorTarifArzyab by remember { mutableStateOf(false) }
    var openDialogErrorPhoneNumber by remember { mutableStateOf(false) }
    var openDialogErrorCodeMeli by remember { mutableStateOf(false) }
    var openDialogErrorCheckText by remember { mutableStateOf(false) }
    val checkUserNameArzyab = checkText(userName_Arzyab)
    val checkPasswordArzyab = checkText(password_Arzyab)
    val currentDate by remember { mutableStateOf(LocalDate.now()) }
    val formattedDate = remember(currentDate) { currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}
    val context = LocalContext.current
    var strForPics by remember {
        mutableStateOf("")
    }
    BackHandler {
        navController.popBackStack()
        navController.navigate(Screen.ModiriateArzyabhaScreen.route)
    }

    if (arzyabid != 0 && arzyabid != null){
        viewModel.onEvent(TarifArzyabEvent.findArzyabById,arzyabid.toInt())
        arzyabName=state.Name_Family
        codeMeli_Arzyab=state.Code_Mali
        phoneNumber_Arzyab=state.Tell.toString()
        userName_Arzyab=state.Username
        password_Arzyab=state.Pass
        mutableUriState.value = Uri.parse(state.ImageNameArzyab)
        strForPics = state.ImageNameArzyab
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFF3F5F7))) {

        //Top Bar
        TopAppBar(backgroundColor = Color.White) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.ModiriateArzyabhaScreen.route)
                }) { Icon(imageVector = Icons.Sharp.ArrowForward, contentDescription = "") }
                Text(
                    text = "تعریف ارزیاب",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable {
                            navController.popBackStack()
                            navController.navigate(Screen.ModiriateArzyabhaScreen.route)
                        }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .clip(shape)
                .background(color = Color.White)
                .verticalScroll(rememberScrollState())
        ) {

            //first main column
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.8f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Column(Modifier.fillMaxWidth(0.8f)) {
                    Row(modifier = Modifier.fillMaxWidth()) {

                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_account),
                            contentDescription = "",
                            tint = colorResource(id = R.color.color_icons)
                        )

                        Spacer(modifier = Modifier.padding(start = 3.dp, end = 3.dp))

                        Text(
                            text = "نام و نام خانوادگی ارزیاب", fontSize = 15.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    CustomTextField(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colors.surface,
                                RoundedCornerShape(percent = 50)
                            )
                            .height(42.dp)
                            .fillMaxWidth(),
                        fontSize = 12.sp,
                        placeholderText = arzyabName,//state.ImageNameArzyab,
                        onChangeString= {newName-> arzyabName=newName}
                    )
                }

                //second side column
                Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Row(modifier = Modifier.fillMaxWidth()) {

                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_contact_mail),
                            contentDescription = "",
                            tint = colorResource(id = R.color.color_icons)
                        )

                        Spacer(modifier = Modifier.padding(start = 3.dp, end = 3.dp))

                        Text(
                            text = "کد ملی", fontSize = 15.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )

                    }
                    CustomTextField(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colors.surface,
                                RoundedCornerShape(percent = 50)
                            )
                            .height(42.dp)
                            .fillMaxWidth(),
                        fontSize = 12.sp,
                        keyboardType = KeyboardType.Number,
                        placeholderText = codeMeli_Arzyab,
                        onChangeString= {newName-> codeMeli_Arzyab=newName}
                    )

                }

                //third side column
                Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Row(modifier = Modifier.fillMaxWidth()) {

                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_phone),
                            contentDescription = "",
                            tint = colorResource(id = R.color.color_icons)
                        )

                        Spacer(modifier = Modifier.padding(start = 3.dp, end = 3.dp))

                        Text(
                            text = "شماره موبایل", fontSize = 15.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    CustomTextField(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colors.surface,
                                RoundedCornerShape(percent = 50)
                            )
                            .height(42.dp)
                            .fillMaxWidth(),
                        fontSize = 12.sp,
                        keyboardType = KeyboardType.Number,
                        placeholderText = phoneNumber_Arzyab,
                        onChangeString= {newName-> phoneNumber_Arzyab=newName}
                    )
                }

                //forth side column
                Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Row(modifier = Modifier.fillMaxWidth()) {

                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_person),
                            contentDescription = "",
                            tint = colorResource(id = R.color.color_icons)
                        )

                        Spacer(modifier = Modifier.padding(start = 3.dp, end = 3.dp))

                        Text(
                            text = "نام کاربری", fontSize = 15.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    CustomTextField(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colors.surface,
                                RoundedCornerShape(percent = 50)
                            )
                            .height(42.dp)
                            .fillMaxWidth(),
                        fontSize = 12.sp,
                        placeholderText = userName_Arzyab,
                        onChangeString= {newName-> userName_Arzyab=newName}
                    )
                }
            }

            //second main column
            Column(
                modifier = Modifier
                    //.background(color = Color(0xFFF3F5F7))
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Column(
                    modifier=Modifier.fillMaxHeight(0.55f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    //***************************************
                    if(state.ImageNameArzyab.contains("storage")){
                        Box(modifier = Modifier.background(color = Color.White)){
                            Image(
                                painter = rememberAsyncImagePainter(model = File(state.ImageNameArzyab)),
                                contentDescription = "arzyabProfile",
                                contentScale = ContentScale.Crop,//for auto cropping the image to square
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(200.dp)
                                    .border(2.dp, Color(0xFF095A6F), CircleShape)
                            )

                        }


                    }else{
                        Profile_setup(
                            imageUriState =
                            if(arzyabid == 0){ imageUriState }else { mutableUriState},
                            selectImageLauncher = selectImageLauncher,
                            uriString=if(arzyabid == 0)"" else{state.ImageNameArzyab}
                        )

                    }




                }

                Spacer(Modifier.fillMaxHeight(0.148f))
                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Column(modifier=Modifier.fillMaxWidth()) {

                    Row(modifier = Modifier.fillMaxWidth()) {

                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_lock),
                            contentDescription = "",
                            tint = colorResource(id = R.color.color_icons)
                        )

                        Spacer(modifier = Modifier.padding(start = 3.dp, end = 3.dp))

                        Text(
                            text = "کلمه عبور", fontSize = 15.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically){
                        CustomTextField(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colors.surface,
                                    RoundedCornerShape(percent = 50)
                                )
                                .height(42.dp)
                                .fillMaxWidth(0.9f),
                            fontSize = 12.sp,
                            placeholderText = password_Arzyab,
                            onChangeString= {newName-> password_Arzyab=newName},
                            imeAction = ImeAction.Done,
                            keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()})
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(bottom = 5.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            shape = RoundedCornerShape(7.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF2699fb)),
                            onClick = {
                                if(imageUriState.value!=null){
                                    imageUriState.value?.let {
                                        val x = it
                                        Log.d("pictureCheck","${viewModel.getFilePathFromUri(contentResolver = context,it)}")

                                        strForPics = viewModel.getFilePathFromUri(contentResolver = context,it) ?:""

                                    }
                                }
                                if (arzyabName.isEmpty() || codeMeli_Arzyab.isEmpty() || phoneNumber_Arzyab.isEmpty() || userName_Arzyab.isEmpty() || password_Arzyab.isEmpty()){
                                    openDialogErrorTarifArzyab = true
                                }else{
                                    if (phoneNumber_Arzyab.length != maxCharPhoneNumber){
                                        openDialogErrorPhoneNumber = true
                                    }else{
                                        if (codeMeli_Arzyab.length != maxCharMeli){
                                            openDialogErrorCodeMeli = true
                                        }else{
                                            if (checkUserNameArzyab == false || checkPasswordArzyab == false){
                                                openDialogErrorCheckText = true
                                            }else{
                                                navController.popBackStack()
                                                viewModel.insertArzyab(Arzyab(
                                                    ID_Arzyab = if(arzyabid ==0 || arzyabid==null){
                                                        null
                                                    } else{state.ID_Arzyab},
                                                    Name_Family = arzyabName,
                                                    Code_Mali = codeMeli_Arzyab,
                                                    Tell = phoneNumber_Arzyab,
                                                    Username = userName_Arzyab,
                                                    Pass = password_Arzyab,
                                                    Activeval = 1,
                                                    Date_Create = formattedDate,
                                                    ImageNameArzyab = if(imageUriState!=null)strForPics else state.ImageNameArzyab
                                                ))
                                                viewModel.getLastArzyabId()

                                                if(which_page==0)navController.navigate("entekhabe_class_drill_ghabele_arzyabi/${viewModel.arzyabId.value}")
                                                if(which_page==1)navController.navigate(Screen.ModiriateArzyabhaScreen.route)

                                                imageUriState.value = null
                                            }
                                        }
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = "    ادامه    ",
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.White,
                                style = MaterialTheme.typography.h5
                            )
                        }
                    }
                }
            }
        }
    }

    if (openDialogErrorTarifArzyab) {
        AlertDialog(
            onDismissRequest = {openDialogErrorTarifArzyab = false},
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_warning),
                        contentDescription = "",
                        modifier = Modifier.size(60.dp)
                    )
                }
            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("اخطار: مشخصات ارزیاب کامل نیست.",
                            style = MaterialTheme.typography.h5,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                        Text("لطفا همه مشخصات ارزیاب را تکمیل کنید.",
                            modifier = Modifier.padding(vertical = 5.dp),
                            style = MaterialTheme.typography.h5,
                            maxLines = 3,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            confirmButton = {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    TextButton(
                        modifier = Modifier.padding(bottom = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2699fb)),
                        onClick = {openDialogErrorTarifArzyab = false}
                    ) {
                        Text("باشه", color = Color.White)
                    }
                }

            }
        )
    }

    if (openDialogErrorPhoneNumber) {
        AlertDialog(
            onDismissRequest = { openDialogErrorPhoneNumber = false },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_warning),
                        contentDescription = "",
                        modifier = Modifier.size(60.dp)
                    )
                }
            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("اخطار: شماره موبایل ارزیاب صحیح نیست.",
                            style = MaterialTheme.typography.h5,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                        Text("شماره موبایل ارزیاب باید یازده رقمی باشد.",
                            modifier = Modifier.padding(vertical = 5.dp),
                            style = MaterialTheme.typography.h5,
                            maxLines = 3,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            confirmButton = {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    TextButton(
                        modifier = Modifier.padding(bottom = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2699fb)),
                        onClick = {openDialogErrorPhoneNumber = false}
                    ) {
                        Text("باشه", color = Color.White)
                    }
                }

            }
        )
    }

    if (openDialogErrorCodeMeli) {
        AlertDialog(
            onDismissRequest = { openDialogErrorCodeMeli = false },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_warning),
                        contentDescription = "",
                        modifier = Modifier.size(60.dp)
                    )
                }
            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("اخطار: کد ملی ارزیاب صحیح نیست."
                            ,style = MaterialTheme.typography.h5,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                        Text("کد ملی ارزیاب باید ده رقمی باشد.",
                            modifier = Modifier.padding(vertical = 5.dp),
                            style = MaterialTheme.typography.h5,
                            maxLines = 3,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            confirmButton = {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    TextButton(
                        modifier = Modifier.padding(bottom = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2699fb)),
                        onClick = {openDialogErrorCodeMeli = false}
                    ) {
                        Text("باشه", color = Color.White)
                    }
                }

            }
        )
    }

    if (openDialogErrorCheckText) {
        AlertDialog(
            onDismissRequest = { openDialogErrorCheckText = false },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_warning),
                        contentDescription = "",
                        modifier = Modifier.size(60.dp)
                    )
                }
            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("اخطار: نام کاربردی یا کلمه عبور نامعتبر است.",
                            style = MaterialTheme.typography.h5,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                        Text("باید از بین a-z یا A-Z یا 9-0 (اعداد و حروف انگلیسی) انتخاب شود.",
                            modifier = Modifier.padding(vertical = 5.dp),
                            style = MaterialTheme.typography.h5,
                            maxLines = 3,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            confirmButton = {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    TextButton(
                        modifier = Modifier.padding(bottom = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2699fb)),
                        onClick = {openDialogErrorCheckText = false}
                    ) {
                        Text("باشه", color = Color.White)
                    }
                }

            }
        )
    }

}*/