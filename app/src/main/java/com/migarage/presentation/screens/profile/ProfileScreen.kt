package com.migarage.presentation.screens.profile

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.migarage.BuildConfig
import com.migarage.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onEditVehicle: () -> Unit
) {
    val context = LocalContext.current
    val vehicle by viewModel.vehicle.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil", color = TextPrimary) },
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Surface),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Primary, PrimaryDark)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = vehicle?.displayName ?: "Sin vehículo",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    vehicle?.let {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${it.year} · ${it.licensePlate}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        it.color?.let { color ->
                            Text(
                                text = color,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                        Text(
                            text = "${it.currentMileage} km",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onEditVehicle,
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (vehicle == null) "Agregar Vehículo" else "Editar Vehículo")
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ProfileSettingsItem(
                        icon = Icons.Default.Notifications,
                        title = "Notificaciones",
                        subtitle = "Configurar alertas y recordatorios",
                        onClick = { }
                    )
                    HorizontalDividerLine()
                    ProfileSettingsItem(
                        icon = Icons.Default.Cloud,
                        title = "Respaldo",
                        subtitle = "Sincronizar datos en la nube",
                        onClick = { }
                    )
                    HorizontalDividerLine()
                    ProfileSettingsItem(
                        icon = Icons.Default.Share,
                        title = "Compartir App",
                        subtitle = "Recomienda MiGarage a otros",
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "MiGarage - Tu garage digital")
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Descarga MiGarage: https://github.com/hbravoStridelabs/MiGarage\n\n" +
                                    "La app perfecta para mantener toda la documentación de tu vehículo al día."
                                )
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Compartir vía"))
                        }
                    )
                    HorizontalDividerLine()
                    ProfileSettingsItem(
                        icon = Icons.Default.Star,
                        title = "Calificar App",
                        subtitle = "Tu opinión nos ayuda a mejorar",
                        onClick = { }
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ProfileSettingsItem(
                        icon = Icons.Default.Info,
                        title = "Acerca de",
                        subtitle = "Versión ${BuildConfig.VERSION_NAME}",
                        onClick = { }
                    )
                    HorizontalDividerLine()
                    ProfileSettingsItem(
                        icon = Icons.Default.Help,
                        title = "Ayuda",
                        subtitle = "Preguntas frecuentes y soporte",
                        onClick = { }
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun HorizontalDividerLine() {
    Divider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = SurfaceLight
    )
}

@Composable
private fun ProfileSettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(SurfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextSecondary
        )
    }
}
