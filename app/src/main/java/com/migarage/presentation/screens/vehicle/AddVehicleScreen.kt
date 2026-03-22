package com.migarage.presentation.screens.vehicle

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.migarage.data.CarBrandsData
import com.migarage.presentation.screens.profile.EditVehicleViewModel
import com.migarage.presentation.theme.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleScreen(
    viewModel: EditVehicleViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onVehicleSaved: () -> Unit
) {
    val context = LocalContext.current
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var licensePlate by remember { mutableStateOf("") }
    var vin by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }
    var saved by remember { mutableStateOf(false) }
    var licensePlateImageUri by remember { mutableStateOf<Uri?>(null) }
    var currentPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var showImageOptions by remember { mutableStateOf(false) }
    
    var showBrandDropdown by remember { mutableStateOf(false) }
    var showModelDropdown by remember { mutableStateOf(false) }
    var brandSearchQuery by remember { mutableStateOf("") }
    var modelSearchQuery by remember { mutableStateOf("") }
    val brands = CarBrandsData.brands
    val filteredBrands = remember(brandSearchQuery) {
        if (brandSearchQuery.isEmpty()) brands
        else brands.filter { it.contains(brandSearchQuery, ignoreCase = true) }
    }
    val modelsForBrand = remember(brand) { CarBrandsData.getModelsForBrand(brand) }
    val filteredModels = remember(modelSearchQuery, modelsForBrand) {
        if (modelSearchQuery.isEmpty()) modelsForBrand
        else modelsForBrand.filter { it.contains(modelSearchQuery, ignoreCase = true) }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && currentPhotoUri != null) {
            licensePlateImageUri = currentPhotoUri
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { licensePlateImageUri = it }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val photoFile = createImageFile(context)
            currentPhotoUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                photoFile
            )
            currentPhotoUri?.let { cameraLauncher.launch(it) }
        }
    }

    fun takePhoto() {
        val photoFile = createImageFile(context)
        currentPhotoUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            photoFile
        )
        currentPhotoUri?.let { cameraLauncher.launch(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Vehículo", color = TextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        },
        containerColor = Background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LicensePlateSection(
                licensePlate = licensePlate,
                onLicensePlateChange = { licensePlate = it.uppercase() },
                imageUri = licensePlateImageUri,
                onCameraClick = { showImageOptions = true },
                onClearImage = { licensePlateImageUri = null }
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Datos del Vehículo",
                        style = MaterialTheme.typography.titleMedium,
                        color = Primary,
                        fontWeight = FontWeight.SemiBold
                    )

                    ExposedDropdownMenuBox(
                        expanded = showBrandDropdown,
                        onExpandedChange = { showBrandDropdown = it }
                    ) {
                        OutlinedTextField(
                            value = brand,
                            onValueChange = {
                                brand = it
                                brandSearchQuery = it
                                model = ""
                                modelSearchQuery = ""
                                showModelDropdown = false
                                showBrandDropdown = true
                            },
                            label = { Text("Marca") },
                            placeholder = { Text("Escribe para buscar...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = textFieldColors(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = showBrandDropdown)
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Business,
                                    contentDescription = null,
                                    tint = TextSecondary
                                )
                            },
                            singleLine = true
                        )
                        ExposedDropdownMenu(
                            expanded = showBrandDropdown,
                            onDismissRequest = { showBrandDropdown = false }
                        ) {
                            filteredBrands.take(20).forEach { brandItem ->
                                DropdownMenuItem(
                                    text = { Text(brandItem) },
                                    onClick = {
                                        brand = brandItem
                                        brandSearchQuery = brandItem
                                        showBrandDropdown = false
                                    }
                                )
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = showModelDropdown,
                        onExpandedChange = { showModelDropdown = it }
                    ) {
                        OutlinedTextField(
                            value = model,
                            onValueChange = {
                                model = it
                                modelSearchQuery = it
                                showModelDropdown = true
                            },
                            label = { Text("Modelo") },
                            placeholder = { Text("Escribe para buscar...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = textFieldColors(),
                            enabled = brand.isNotEmpty(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = showModelDropdown)
                            },
                            leadingIcon = {
                                if (brand.isNotEmpty()) {
                                    Icon(
                                        imageVector = Icons.Default.DirectionsCar,
                                        contentDescription = null,
                                        tint = TextSecondary
                                    )
                                }
                            },
                            singleLine = true
                        )
                        ExposedDropdownMenu(
                            expanded = showModelDropdown,
                            onDismissRequest = { showModelDropdown = false }
                        ) {
                            filteredModels.take(20).forEach { modelItem ->
                                DropdownMenuItem(
                                    text = { Text(modelItem) },
                                    onClick = {
                                        model = modelItem
                                        modelSearchQuery = modelItem
                                        showModelDropdown = false
                                    }
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = year,
                            onValueChange = { year = it.filter { c -> c.isDigit() }.take(4) },
                            label = { Text("Año") },
                            placeholder = { Text("2020") },
                            modifier = Modifier.weight(1f),
                            colors = textFieldColors(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = null,
                                    tint = TextSecondary
                                )
                            },
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = color,
                            onValueChange = { color = it.replaceFirstChar { c -> c.uppercase() } },
                            label = { Text("Color") },
                            placeholder = { Text("Gris") },
                            modifier = Modifier.weight(1f),
                            colors = textFieldColors(),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
                                imeAction = ImeAction.Next
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Palette,
                                    contentDescription = null,
                                    tint = TextSecondary
                                )
                            },
                            singleLine = true
                        )
                    }

                    OutlinedTextField(
                        value = mileage,
                        onValueChange = { mileage = it.filter { c -> c.isDigit() } },
                        label = { Text("Kilometraje actual") },
                        placeholder = { Text("45000") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = null,
                                tint = TextSecondary
                            )
                        },
                        trailingIcon = {
                            Text(
                                text = "km",
                                color = TextSecondary,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = vin,
                        onValueChange = { vin = it.uppercase() },
                        label = { Text("VIN (opcional)") },
                        placeholder = { Text("17 dígitos") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Characters,
                            imeAction = ImeAction.Done
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.QrCode,
                                contentDescription = null,
                                tint = TextSecondary
                            )
                        },
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.saveVehicle(
                        brand = brand,
                        model = model,
                        year = year.toIntOrNull() ?: 0,
                        licensePlate = licensePlate,
                        vin = vin.takeIf { it.isNotBlank() },
                        color = color.takeIf { it.isNotBlank() },
                        mileage = mileage.toIntOrNull() ?: 0
                    )
                    saved = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(16.dp),
                enabled = brand.isNotBlank() && model.isNotBlank() && licensePlate.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Guardar Vehículo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (saved) {
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(300)
                    onVehicleSaved()
                }
            }
        }
    }

    if (showImageOptions) {
        ModalBottomSheet(
            onDismissRequest = { showImageOptions = false },
            containerColor = Surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Agregar foto de patente",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Fotografía la patente de tu vehículo o selcciona una imagen existente",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ImageOptionButton(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.CameraAlt,
                        label = "Cámara",
                        onClick = {
                            showImageOptions = false
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    )
                    ImageOptionButton(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.PhotoLibrary,
                        label = "Galería",
                        onClick = {
                            showImageOptions = false
                            galleryLauncher.launch("image/*")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun LicensePlateSection(
    licensePlate: String,
    onLicensePlateChange: (String) -> Unit,
    imageUri: Uri?,
    onCameraClick: () -> Unit,
    onClearImage: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Primary.copy(alpha = 0.3f),
                                PrimaryDark.copy(alpha = 0.3f)
                            )
                        )
                    )
                    .border(
                        width = 2.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(Primary, PrimaryDark)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Success,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Patente fotografiada",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Success,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedButton(
                            onClick = onClearImage,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = TextPrimary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Cambiar")
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Patente del vehículo",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = licensePlate,
                onValueChange = onLicensePlateChange,
                label = { Text("Patente") },
                placeholder = { Text("Ej: ABC-1234") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Done
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Badge,
                        contentDescription = null,
                        tint = Primary
                    )
                },
                trailingIcon = {
                    IconButton(onClick = onCameraClick) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Fotografiar patente",
                            tint = Primary
                        )
                    }
                },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Toca la cámara para fotografiar la patente",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
private fun ImageOptionButton(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceVariant)
            .clickable(onClick = onClick)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Primary,
    unfocusedBorderColor = SurfaceLight,
    focusedLabelColor = Primary,
    unfocusedLabelColor = TextSecondary,
    cursorColor = Primary,
    focusedTextColor = TextPrimary,
    unfocusedTextColor = TextPrimary
)

private fun createImageFile(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("PATENTE_${timeStamp}_", ".jpg", storageDir)
}
