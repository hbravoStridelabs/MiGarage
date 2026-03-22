package com.migarage.presentation.screens.documents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.migarage.BuildConfig
import com.migarage.domain.model.Document
import com.migarage.domain.model.DocumentStatus
import com.migarage.presentation.theme.*
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentsScreen(
    viewModel: DocumentsViewModel = hiltViewModel(),
    onDocumentClick: (String) -> Unit,
    onAddClick: () -> Unit
) {
    val documents by viewModel.documents.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Todos", "Vigentes", "Por Vencer", "Vencidos")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Documentos", color = TextPrimary) },
                actions = {
                    Surface(
                        color = Primary.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "v${BuildConfig.VERSION_NAME}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Primary,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = TextPrimary)
            }
        },
        containerColor = Background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Background,
                contentColor = TextPrimary,
                edgePadding = 16.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                color = if (selectedTab == index) Primary else TextSecondary
                            )
                        }
                    )
                }
            }

            val filteredDocuments = when (selectedTab) {
                1 -> documents.filter { it.status == DocumentStatus.ACTIVE }
                2 -> documents.filter { it.status == DocumentStatus.EXPIRING_SOON }
                3 -> documents.filter { it.status == DocumentStatus.EXPIRED }
                else -> documents
            }

            if (filteredDocuments.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No hay documentos",
                            color = TextSecondary
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredDocuments) { document ->
                        DocumentCard(
                            document = document,
                            onClick = { onDocumentClick(document.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DocumentCard(
    document: Document,
    onClick: () -> Unit
) {
    val (statusColor, statusText) = when (document.status) {
        DocumentStatus.ACTIVE -> Success to "Vigente"
        DocumentStatus.EXPIRING_SOON -> Warning to "Por vencer"
        DocumentStatus.EXPIRED -> Error to "Vencido"
        DocumentStatus.ARCHIVED -> TextSecondary to "Archivado"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (document.type) {
                        com.migarage.domain.model.DocumentType.RT -> Icons.Default.CheckCircle
                        com.migarage.domain.model.DocumentType.SOAT -> Icons.Default.Security
                        com.migarage.domain.model.DocumentType.PC -> Icons.Default.DirectionsCar
                        com.migarage.domain.model.DocumentType.TP -> Icons.Default.Article
                        com.migarage.domain.model.DocumentType.CV -> Icons.Default.Handshake
                        com.migarage.domain.model.DocumentType.RG -> Icons.Default.Air
                        com.migarage.domain.model.DocumentType.SV -> Icons.Default.Shield
                        com.migarage.domain.model.DocumentType.OTHER -> Icons.Default.Folder
                    },
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = document.type.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = document.documentNumber,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                document.expiryDate?.let {
                    Text(
                        text = "Vence: ${it.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .padding(4.dp)
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
