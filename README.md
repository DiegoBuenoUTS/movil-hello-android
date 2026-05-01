# Tareas DBueno y JUrrea

Aplicacion Android desarrollada para los talleres de Programacion Movil. El proyecto mantiene el paquete `com.bueno.helloandroid` y evoluciona el ejercicio inicial hacia una app de lista de tareas con una sola `Activity`, multiples `Fragments`, persistencia local y recordatorios.

## Taller 3 - Lista de tareas

### Que se implemento

- `MainActivity` como host unico de navegacion.
- `TaskListFragment` para listar tareas guardadas.
- `TaskDetailFragment` para crear, editar y eliminar tareas.
- Navegacion con `Navigation Component` desde lista hacia detalle y regreso con `navigateUp()`.
- Persistencia local usando `SharedPreferences` y JSON con Gson.
- Modelo `Task` y repositorio `TaskRepository` separados en `data.task`.
- `BroadcastReceiver` llamado `TaskReminderReceiver` para recordatorios.
- Opcion de recordatorio elegida: notificacion local.
- Solicitud del permiso `POST_NOTIFICATIONS` en Android 13 o superior.
- Interfaz actualizada con contador, estado vacio, tarjetas de tareas y validacion de titulo obligatorio.

### Flujo de uso

1. Abrir la app.
2. Tocar `Agregar tarea`.
3. Escribir titulo, descripcion y fecha u hora aproximada.
4. Activar `Recordatorio` si se quiere una notificacion local.
5. Guardar la tarea y regresar a la lista.
6. Tocar una tarea existente para editarla o eliminarla.

### Recordatorios

La app programa una notificacion local 30 segundos despues de guardar una tarea con recordatorio activo. El recordatorio se dispara mediante `AlarmManager`, un `PendingIntent` y `TaskReminderReceiver`.

Si una tarea se edita y se desactiva el recordatorio, la alarma pendiente se cancela. Si una tarea se elimina, tambien se cancela su recordatorio.

## Cumplimiento del taller

| Requisito del PDF | Estado |
| --- | --- |
| Single Activity + Fragments | Cumplido |
| `TaskListFragment` con lista y boton para agregar | Cumplido |
| `TaskDetailFragment` con titulo, descripcion, fecha, recordatorio y guardar | Cumplido |
| Navegacion lista -> detalle -> lista | Cumplido |
| Tocar tarea existente para editar | Cumplido |
| Persistencia local de tareas | Cumplido |
| `BroadcastReceiver` para recordatorios | Cumplido |
| Recordatorio por notificacion o sonido | Cumplido con notificacion |
| Codigo organizado por paquetes | Cumplido |
| README con seccion del Taller 3 | Cumplido |

## Estructura relevante

```text
app/src/main/java/com/bueno/helloandroid/
├── MainActivity.kt
├── data/task/
│   ├── Task.kt
│   └── TaskRepository.kt
├── receiver/
│   └── TaskReminderReceiver.kt
└── ui/task/
    ├── TaskAdapter.kt
    ├── TaskDetailFragment.kt
    └── TaskListFragment.kt
```

```text
app/src/main/res/
├── layout/
│   ├── activity_main.xml
│   ├── fragment_task_list.xml
│   ├── fragment_task_detail.xml
│   └── item_task.xml
└── navigation/
    └── nav_graph.xml
```

## Validacion local

Comandos usados para verificar el proyecto:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat testDebugUnitTest
```

Ambos comandos deben terminar con `BUILD SUCCESSFUL`.

## Evidencias

Para la entrega final del taller, la carpeta `Docs/` debe contener capturas reales del emulador o dispositivo mostrando:

- Lista de tareas.
- Pantalla de creacion o edicion.
- Notificacion de recordatorio visible.

## Datos del proyecto

- Nombre de la app: `Tareas Bueno`
- Paquete Android: `com.bueno.helloandroid`
- Lenguaje: Kotlin
- Persistencia: `SharedPreferences` + Gson
- Recordatorio: Notificacion local con `BroadcastReceiver`
