package com.migarage.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.migarage.domain.model.Alert
import com.migarage.domain.model.Document
import com.migarage.presentation.theme.*
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDocuments: () -> Unit,
    onNavigateToMaintenance: () -> Unit,
    onNavigateToAddVehicle: () -> Unit = {}
) {
    val vehicle by viewModel.vehicle.collectAsState()
    val expiringDocuments by viewModel.expiringDocuments.collectAsState()
    val recentMaintenance by viewModel.recentMaintenance.collectAsState()
    val activeAlerts by viewModel.activeAlerts.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            GreetingSection(
                vehicleName = vehicle?.displayName,
                licensePlate = vehicle?.licensePlate,
                mileage = vehicle?.currentMileage,
                onAddVehicleClick = onNavigateToAddVehicle
            )
        }

        if (vehicle == null) {
            item {
                NoVehicleCard(onClick = onNavigateToAddVehicle)
            }
        } else {
            if (expiringDocuments.isNotEmpty()) {
                item {
                    ExpiringCard(
                        document = expiringDocuments.first(),
                        count = expiringDocuments.size,
                        onClick = onNavigateToDocuments
                    )
                }
            }

            item {
                QuickStatsRow(
                    documentCount = viewModel.documentCount,
                    maintenanceCount = viewModel.maintenanceCount,
                    mileage = vehicle?.currentMileage ?: 0,
                    onDocumentsClick = onNavigateToDocuments,
                    onMaintenanceClick = onNavigateToMaintenance
                )
            }

            if (activeAlerts.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Alertas",
                        icon = Icons.Default.Warning,
                        iconColor = Warning
                    )
                }
                items(activeAlerts.take(3)) { alert ->
                    AlertItem(alert = alert)
                }
            }

            if (recentMaintenance.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Servicios Recientes",
                        icon = Icons.Default.Build,
                        iconColor = Success
                    )
                }
                items(recentMaintenance.take(3)) { record ->
                    MaintenanceItem(record = record)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun GreetingSection(
    vehicleName: String?,
    licensePlate: String?,
    mileage: Int?,
    onAddVehicleClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
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
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Hola!",
                style = MaterialTheme.typography.titleLarge,
                color = TextSecondary
            )
            if (vehicleName != null) {
                Text(
                    text = vehicleName,
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    licensePlate?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Primary,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = " · ",
                            color = TextSecondary
                        )
                    }
                    mileage?.takeIf { it > 0 }?.let {
                        Text(
                            text = formatMileage(it) + " km",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                Text(
                    text = "Bienvenido a MiGarage",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Agrega tu vehículo para comenzar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }

        if (vehicleName == null) {
            FilledIconButton(
                onClick = onAddVehicleClick,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar vehículo",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun NoVehicleCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Primary.copy(alpha = 0.15f),
                            Surface
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Sin vehículo registrado",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Agrega tu auto para mantener toda su documentación al día",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Toca para agregar →",
                        style = MaterialTheme.typography.labelMedium,
                        color = Primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpiringCard(
    document: Document,
    count: Int,
    onClick: () -> Unit
) {
    val daysUntil = java.time.temporal.ChronoUnit.DAYS.between(
        java.time.LocalDate.now(),
        document.expiryDate
    ).toInt()

    val (backgroundColor, iconColor, statusText) = when {
        daysUntil < 0 -> Triple(Error.copy(alpha = 0.15f), Error, "VENCIDO")
        daysUntil <= 7 -> Triple(Error.copy(alpha = 0.15f), Error, "Vence en $daysUntil días")
        daysUntil <= 30 -> Triple(Warning.copy(alpha = 0.15f), Warning, "Vence en $daysUntil días")
        else -> Triple(Success.copy(alpha = 0.15f), Success, "Vigente")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (count > 1) "$count documentos por vencer" else document.type.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.bodySmall,
                        color = iconColor
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = TextSecondary
                )
            }
        }
    }
}

@Composable
private fun QuickStatsRow(
    documentCount: Int,
    maintenanceCount: Int,
    mileage: Int,
    onDocumentsClick: () -> Unit,
    onMaintenanceClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Description,
            value = "$documentCount",
            label = "Documentos",
            color = Primary,
            onClick = onDocumentsClick
        )
        StatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Build,
            value = "$maintenanceCount",
            label = "Servicios",
            color = Success,
            onClick = onMaintenanceClick
        )
        StatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Speed,
            value = formatMileage(mileage),
            label = "Kilómetros",
            color = BlueAccent,
            onClick = {}
        )
    }
}

@Composable
private fun SectionHeader(
    title: String,
    icon: ImageVector,
    iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    value: String,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}

@Composable
private fun AlertItem(alert: Alert) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(if (alert.isResolved) Success else Warning)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = alert.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = alert.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
private fun MaintenanceItem(record: com.migarage.domain.model.MaintenanceRecord) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Success.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = null,
                    tint = Success,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.serviceType.displayName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${record.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))} · ${formatMileage(record.mileage)} km",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            record.cost?.let {
                Text(
                    text = "$${String.format("%.0f", it)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

private fun formatMileage(km: Int): String {
    return when {
        km >= 1000000 -> "${km / 1000000}.${(km % 1000000) / 100000}M"
        km >= 1000 -> "${km / 1000}.${(km % 1000) / 100}k"
        else -> km.toString()
    }
}
