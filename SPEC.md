# MiGarage — App de Documentación Automotriz

> Especificación técnica del proyecto
> Version: 1.0.0
> Fecha: 2026-03-20

---

## 1. Concepto y Vision

**MiGarage** es el管家 digital de tu vehículo. Una app que centraliza todos los documentos, mantiene al día el mantenimiento y te avisa antes de que algo venza o falle.

**Personalidad**: Profesional pero amigable. Como un mecánico de confianza que te recuerda todo sin molestar. Interfaz limpia que prioriza la información importante.

**Diferenciador**: No es solo un storage de documentos — es inteligente. OCR extrae datos automáticamente, las alertas son proactivas, y el historial de mantenimiento cuenta la historia completa de tu auto.

---

## 2. Design Language

### Estetica
Inspiración: **Automotriz premium + Minimalismo funcional**. Negro profundo con acentos de energía (naranja/ámbar). Como el panel de un auto deportivo.

### Paleta de colores

| Rol | Color | Hex |
|-----|-------|-----|
| **Primary** | Naranja energia | `#FF6B35` |
| **Primary Dark** | Naranja profundo | `#E55A2B` |
| **Secondary** | Gris titanium | `#1A1A2E` |
| **Background** | Negro suave | `#0D0D0D` |
| **Surface** | Gris oscuro | `#1A1A1A` |
| **Surface Light** | Gris medio | `#2D2D2D` |
| **Text Primary** | Blanco | `#FFFFFF` |
| **Text Secondary** | Gris claro | `#B0B0B0` |
| **Success** | Verde aprobado | `#4CAF50` |
| **Warning** | Amarillo alerta | `#FFC107` |
| **Error** | Rojo critico | `#F44336` |

### Tipografia

| Uso | Font | Weight |
|-----|------|--------|
| Headlines | Inter | Bold (700) |
| Subtitles | Inter | SemiBold (600) |
| Body | Inter | Regular (400) |
| Caption | Inter | Regular (400) |
| Numbers | Roboto Mono | Medium (500) |

### Espaciado (8pt grid)
- XS: 4dp
- SM: 8dp
- MD: 16dp
- LG: 24dp
- XL: 32dp
- XXL: 48dp

### Motion
- Transiciones: 300ms ease-in-out
- Cards: scale 0.98 → 1.0 on press
- FAB: bounce on appear
- Lista items: stagger 50ms entre items

---

## 3. Layout & Structure

### Navegacion (Bottom Navigation)

```
[Home] [Documentos] [Mantenimiento] [Alertas] [Perfil]
```

### Pantallas

1. **Home (Dashboard)**
   - Card principal: Proximo a vencer (一眼可见)
   - Stats rapidos: Documents, Mantenimiento, Km
   - Ultimo documento escaneado
   - Quick actions FAB

2. **Documentos**
   - Lista de documentos por categoria
   - Filtros: Todos, Vencidos, Por vencer, Archivados
   - Search
   - FAB: Agregar documento (camara)

3. **Documento Detalle**
   - Foto del documento (zoomable)
   - Datos extraidos en cards
   - Historial de cambios
   - Compartir / Exportar

4. **Mantenimiento**
   - Timeline de servicios
   - Kilometraje actual
   - Proximo servicio
   - Estadisticas de gasto

5. **Agregar Mantenimiento**
   - Tipo de servicio (dropdown)
   - Fecha
   - Kilometraje
   - Taller / Notas
   - Costo

6. **Alertas**
   - Lista de alertas activas
   - Por fecha
   - Por kilometraje
   - Marcar como resuelta

7. **Perfil / Settings**
   - Datos del vehiculo
   - Notificaciones
   - Backup / Restore
   - Acerca de

---

## 4. Features & Interactions

### 4.1 Documentos

#### Tipos de documento
- **Revision Tecnica** — RT
- **Seguro Obligatorio** — SOAT
- **Permiso de Circulacion** — PC
- **Titulo de Propiedad** — TP
- **Compraventa** — CV
- **Revision de Gases** — RG
- **Seguro Vehicular** — SV
- **Otro** — O

#### Captura de documento
1. User toca FAB → abre camara
2. Captura foto (puede recortarla)
3. OCR procesa la imagen (1-2 segundos)
4. Muestra datos extraidos para confirmacion
5. User puede editar antes de guardar
6. Guardar → documento en lista

#### OCR — Datos extraidos
| Documento | Campos |
|-----------|--------|
| RT | Numero certificado, Fecha emision, Fecha vencimiento, Resultado (aprobado/reprobado) |
| SOAT | Numero poliza, Aseguradora, Fecha inicio, Fecha vencimiento, Coverage |
| PC | Numero permiso, Region, Ano vigencia, Patente |
| SV | Numero poliza, Aseguradora, Fecha inicio, Fecha vencimiento, Coverage |
| RG | Numero certificado, Fecha emision, Fecha vencimiento, Resultado |

#### Estados de documento
- **Activo** (verde) — vigente
- **Por vencer** (amarillo) — dentro de 30 dias
- **Vencido** (rojo) — caducado
- **Archivado** (gris) — dado de baja manualmente

### 4.2 Mantenimiento

#### Tipos de servicio
- Cambio de aceite
- Filtro de aire
- Filtro de aceite
- Filtro de combustibe
- Neumaticos (rotacion/balance/cambio)
- Frenos
- Bateria
- Refrigerante
- Transmision
- Suspension
- Alineacion
- Revision general
- Otro

#### Registro
- Tipo de servicio (requerido)
- Fecha del servicio (default: hoy)
- Kilometraje en ese momento
- Taller / Mecanico (opcional)
- Costo (opcional)
- Notas (opcional)
- Foto del comprobante (opcional)

#### Calculos automaticos
- Proximo cambio de aceite: basado en ultimo cambio + 5.000 km o 6 meses
- Proxima revision tecnica: fecha vencimiento RT + 1 ano
- Alerta desgaste neumaticos: profile < 3mm

### 4.3 Kilometraje

- Input manual en dashboard
- Historial de actualizaciones
- Grafico de evolucion
- Proyeccion de proximo servicio

### 4.4 Alertas

#### Tipos de alerta
- **Fecha** — documento proximo a vencer (30, 15, 7, 1 dia antes)
- **Kilometraje** — servicio proximo por km
- **Personalizada** — recordatorio manual

#### Notificaciones
- Push notification nativa
- Badge en el icono de la app
- Alertas in-app en dashboard

### 4.5 Perfil del vehiculo

Campos:
- Marca
- Modelo
- Ano
- Patente
- Numero de chassis (VIN)
- Color
- Kilometraje actual

---

## 5. Component Inventory

### Card Documento
```
┌─────────────────────────────────────┐
│ [Icono tipo]                        │
│ Revision Tecnica          [Badge]  │
│ RT-2024-XXXXX                      │
│ Vence: 15 Dic 2024                  │
│ Estado: ● Vigente                  │
└─────────────────────────────────────┘
```
Estados: default, selected, expired

### Card Mantenimiento
```
┌─────────────────────────────────────┐
│ 🔧 Cambio de aceite               │
│ 15 Mar 2024 · 45.230 km           │
│ Taller Mecanos · $45.000           │
└─────────────────────────────────────┘
```

### Card Stats
```
┌─────────────────┐
│      12.450     │
│      km        │
│ Prox.service   │
└─────────────────┘
```

### FAB
- Posicion: bottom-right, 16dp del borde
- Color: Primary (#FF6B35)
- Icono: + (add)
- Elevacion: 6dp
- Click: scale 0.9 → 1.0, 100ms

### Bottom Navigation
- 5 items, iconos + labels
- Item activo: Primary color
- Item inactivo: Text Secondary

### Status Badge
- Vigente: Success (#4CAF50) + texto verde
- Por vencer: Warning (#FFC107) + texto amarillo
- Vencido: Error (#F44336) + texto rojo

### Empty State
- Ilustracion SVG (auto stylized)
- Titulo descriptivo
- CTA button

---

## 6. Technical Approach

### Stack

| Capa | Tecnologia |
|------|-----------|
| UI | Jetpack Compose + Material 3 |
| Navigation | Compose Navigation |
| DI | Hilt |
| Local DB | Room |
| OCR | Google ML Kit Text Recognition |
| Image Loading | Coil |
| Camera | CameraX |
| Async | Kotlin Coroutines + Flow |
| Notifications | WorkManager + NotificationManager |
| Backup | Firebase (Firestore + Storage) |

### Arquitectura: Clean Architecture + MVVM

```
app/
├── data/
│   ├── local/
│   │   ├── db/
│   │   │   ├── MiGarageDatabase
│   │   │   ├── dao/
│   │   │   └── entity/
│   │   └── datastore/
│   └── repository/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── presentation/
│   ├── navigation/
│   ├── theme/
│   ├── components/
│   └── screens/
│       ├── home/
│       ├── documents/
│       ├── maintenance/
│       ├── alerts/
│       └── profile/
├── di/
├── util/
└── worker/
```

### Data Models

```kotlin
// Documento
@Entity(tableName = "documents")
data class Document(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val type: DocumentType,
    val documentNumber: String,
    val issueDate: LocalDate,
    val expiryDate: LocalDate?,
    val status: DocumentStatus,
    val imagePath: String,
    val extractedData: String?, // JSON
    val notes: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

// MaintenanceRecord
@Entity(tableName = "maintenance_records")
data class MaintenanceRecord(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val serviceType: ServiceType,
    val date: LocalDate,
    val mileage: Int,
    val workshop: String?,
    val cost: Double?,
    val notes: String?,
    val imagePath: String?,
    val createdAt: Long = System.currentTimeMillis()
)

// Vehicle
@Entity(tableName = "vehicle")
data class Vehicle(
    @PrimaryKey val id: String = "default",
    val brand: String,
    val model: String,
    val year: Int,
    val licensePlate: String,
    val vin: String?,
    val color: String?,
    val currentMileage: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

// Alert
@Entity(tableName = "alerts")
data class Alert(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val type: AlertType,
    val title: String,
    val message: String,
    val dueDate: LocalDate?,
    val dueMileage: Int?,
    val relatedDocumentId: String?,
    val relatedMaintenanceId: String?,
    val isResolved: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
```

### Enums

```kotlin
enum class DocumentType { RT, SOAT, PC, TP, CV, RG, SV, OTHER }
enum class DocumentStatus { ACTIVE, EXPIRING_SOON, EXPIRED, ARCHIVED }
enum class ServiceType { OIL_CHANGE, AIR_FILTER, OIL_FILTER, FUEL_FILTER, TIRES, BRAKES, BATTERY, COOLANT, TRANSMISSION, SUSPENSION, ALIGNMENT, GENERAL_CHECK, OTHER }
enum class AlertType { DATE, MILEAGE, CUSTOM }
```

---

## 7. Pantallas - Detalle

### Home Dashboard
- Header con saludo + vehiculo
- Card "Proximo a vencer" (si hay)
- 3 Cards stats: Documents, Mantenimientos, Km
- Quick actions FAB
- Lista de alertas activas (max 3)

### Documentos Lista
- Tabs: Todos | Documentos | Mantenimiento
- Search bar con filtro
- Lista de cards
- FAB → Camara

### Documento Detalle
- Imagen full width (zoomable)
- Chip de estado
- Info cards con datos extraidos
- Acciones: Editar, Compartir, Archivar, Eliminar

### Mantenimiento Lista
- Timeline vertical
- Card por cada servicio
- Header stats: Total gastado, Ultimo servicio, Promedio km

### Agregar Mantenimiento
- Formulario con validacion
- Date picker
- Numeric keyboard para km y costo
- Camera option para comprobante

### Alertas
- Lista de alertas pendientes
- Swipe to resolve
- Filter por tipo
- Empty state si no hay

### Perfil
- Datos del vehiculo (editable)
- Seccion notificaciones
- Backup/Restore
- Version de la app

---

## 8. Flujo de Usuario - Happy Path

### Agregar documento nuevo
1. User abre app → Home
2. Toca FAB (+) → Menu → "Documento"
3. Abre camara → captura foto
4. OCR procesa → muestra datos extraidos
5. User confirma/ corrige datos
6. Toca "Guardar"
7. Documento aparece en lista
8. Si vence pronto → alerta creada automaticamente

### Registrar mantenimiento
1. User → Tab Mantenimiento
2. Toca FAB (+)
3. Selecciona tipo de servicio
4. Ingresa fecha, km, costo (opcionales)
5. Toca "Guardar"
6. Registro aparece en timeline
7. Proximo servicio calculado automaticamente

---

## 9. Non-Functional Requirements

- **Performance**: App fria < 2s, Caliente < 500ms
- **Offline**: 100% funcional sin internet
- **Storage**: Max 100MB en local (imagenes comprimidas)
- **Backup**: Encryptado con clave del usuario
- **Privacy**: No se comparten datos con terceros
- **Accessibility**: Soporte TalkBack, contraste suficiente

---

## 10. Roadmap

### v1.0 — MVP (2-3 semanas)
- [ ] Proyecto base + arquitectura
- [ ] Navegacion + theme
- [ ] CRUD Documentos basico
- [ ] Camara + OCR
- [ ] Dashboard
- [ ] Lista documentos con filtros
- [ ] Detalle documento
- [ ] Notificaciones basicas

### v1.1 — Mantenimiento (2 semanas)
- [ ] CRUD Mantenimiento
- [ ] Timeline
- [ ] Estadisticas
- [ ] Proyeccion proximos servicios

### v1.2 — polish (1 semana)
- [ ] Empty states
- [ ] Animaciones
- [ ] Errores y edge cases
- [ ] Performance

### v2.0 — Cloud (pendiente)
- [ ] Firebase auth
- [ ] Backup en la nube
- [ ] Sincronizacion multi-dispositivo

---

_Especificacion creada por Jarvis — 2026-03-20_
