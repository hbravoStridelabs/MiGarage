package com.migarage.presentation.screens.documents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.migarage.domain.model.Document
import com.migarage.domain.model.DocumentStatus
import com.migarage.domain.model.DocumentType
import com.migarage.presentation.theme.*
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDocumentScreen(
    onBack: () -> Unit,
    onDocumentSaved: () -> Unit
) {
    var selectedType by remember { mutableStateOf(DocumentType.RT) }
    var documentNumber by remember { mutableStateOf("") }
    var issueDate by remember { mutableStateOf(LocalDate.now()) }
    var expiryDate by remember { mutableStateOf<LocalDate?>(LocalDate.now().plusYears(1)) }

    var showTypeDropdown by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Documento", color = TextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atras", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = showTypeDropdown,
                onExpandedChange = { showTypeDropdown = it }
            ) {
                OutlinedTextField(
                    value = selectedType.displayName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de documento") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showTypeDropdown) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = SurfaceLight
                    )
                )
                ExposedDropdownMenu(
                    expanded = showTypeDropdown,
                    onDismissRequest = { showTypeDropdown = false }
                ) {
                    DocumentType.entries.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.displayName) },
                            onClick = {
                                selectedType = type
                                showTypeDropdown = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = documentNumber,
                onValueChange = { documentNumber = it },
                label = { Text("Numero de documento") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = SurfaceLight
                )
            )

            OutlinedTextField(
                value = issueDate.toString(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha de emision") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { /* Date picker */ }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Fecha")
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = SurfaceLight
                )
            )

            OutlinedTextField(
                value = expiryDate?.toString() ?: "Sin fecha",
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha de vencimiento") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { /* Date picker */ }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Fecha")
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = SurfaceLight
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val status = when {
                        expiryDate == null -> DocumentStatus.ACTIVE
                        expiryDate!!.isBefore(LocalDate.now()) -> DocumentStatus.EXPIRED
                        expiryDate!!.isBefore(LocalDate.now().plusDays(30)) -> DocumentStatus.EXPIRING_SOON
                        else -> DocumentStatus.ACTIVE
                    }
                    val doc = Document(
                        type = selectedType,
                        documentNumber = documentNumber,
                        issueDate = issueDate,
                        expiryDate = expiryDate,
                        status = status,
                        imagePath = ""
                    )
                    // TODO: Save via ViewModel
                    onDocumentSaved()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
