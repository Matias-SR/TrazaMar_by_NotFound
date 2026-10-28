# TrazaMar Android — modo local SQLite

La app está configurada para funcionar sin API REST, Node.js ni `npm run dev`. Room usa SQLite y guarda los datos en el almacenamiento privado de la app (`trazamar.db`).

## Datos locales incluidos al iniciar

**Usuarios de demostración** (contraseña inicial para todos: `1234`):

- `ADMIN-01` — administrador
- `OP-01` y `OP-02` — operadores
- `SUP-01` — supervisor
- `AN-01` — analista

Las contraseñas se guardan como hash SHA-256, no como texto plano. Cambia las claves demo antes de usar datos reales.

**Catálogo de demostración:** Centro Demo → Tren 1 → Línea 1 y Línea 2. Se crea únicamente si todavía no existen centros en la base local.

## Qué queda guardado en SQLite

- Usuarios, roles, estado activo y hash de contraseña.
- Centros, trenes y líneas.
- Muestras, estado de revisión, conteos, mediciones, comentarios y ruta local de fotografía.
- Historial y filtros, consultados desde la base local.

## Abrir y ejecutar

1. Descomprime el ZIP y abre la carpeta `TrazaMar` en Android Studio.
2. Espera la sincronización de Gradle.
3. Ejecuta la configuración `app` en un emulador o dispositivo Android.
4. Inicia sesión con una de las cuentas de demostración anteriores.

La base de datos vive en el dispositivo/emulador, no en una carpeta del proyecto ni en VS Code. Cada instalación tiene su propia base local. No hay sincronización entre dispositivos ni respaldo en servidor en esta versión.

## Migración y datos existentes

Se agregó una migración Room de la versión 1 a la 2 para conservar las tablas locales existentes y crear la tabla de usuarios, además de agregar el indicador `activo` a catálogos. No se usa `fallbackToDestructiveMigration`, para evitar borrar automáticamente los datos si cambia el esquema.

## Limitaciones conocidas

- Las fotos se guardan como rutas locales; al borrar archivos de la app o cambiar de dispositivo, las rutas pueden dejar de ser válidas.
- SHA-256 simple es suficiente para esta demo local, pero una aplicación de producción debería usar una función de derivación de claves con sal (por ejemplo, PBKDF2) y controles adicionales.
- La fecha/hora usa el reloj configurado en el dispositivo, porque ya no se consulta un servidor de tiempo.
