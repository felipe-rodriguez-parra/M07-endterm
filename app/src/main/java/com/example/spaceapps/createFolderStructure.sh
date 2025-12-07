#!/bin/bash

# Define la ruta base completa y específica, incluyendo el directorio de inicio del usuario (~).
# Usamos ${HOME} para asegurar la compatibilidad.
BASE_PATH="${HOME}/AndroidStudioProjects/SpaceApps/app/src/main/java/com/example/spaceapps"

echo "Creando la estructura de directorios para SpaceApps en $BASE_PATH..."

# 1. Crear directorios principales
mkdir -p "$BASE_PATH"

# 2. Crear directorios para la arquitectura (MVVM)
mkdir -p "$BASE_PATH/data/api"        # Retrofit interfaces
mkdir -p "$BASE_PATH/data/local"      # Room DAOs, Entities, Database
mkdir -p "$BASE_PATH/data/repository" # Repositorios

mkdir -p "$BASE_PATH/domain/model"    # Clases de dominio (Rocket, User)
mkdir -p "$BASE_PATH/domain/usecase"  # Casos de uso

mkdir -p "$BASE_PATH/ui/pages"        # Pantallas completas
mkdir -p "$BASE_PATH/ui/components"   # Componentes reutilizables
mkdir -p "$BASE_PATH/ui/viewmodel"    # ViewModels
mkdir -p "$BASE_PATH/ui/theme"        # Ficheros de tema de Compose

# --- Rutas de Tests ---
# Las rutas de tests son relativas al directorio 'app' dentro del proyecto raíz
TEST_BASE_PATH="${HOME}/AndroidStudioProjects/SpaceApps/app/src"
mkdir -p "$TEST_BASE_PATH/test/java/com/example/spaceapps/viewmodel"
mkdir -p "$TEST_BASE_PATH/androidTest/java/com/example/spaceapps/ui/tests"


echo "Directorios creados. Creando ficheros de placeholder de Kotlin..."

# 3. Crear ficheros de placeholder clave (Kotlin)
PACKAGE_NAME="com.example.spaceapps"
TOUCH_AND_PACKAGE() {
    FILE_PATH=$1
    # Calcula la sub-ruta relativa al BASE_PATH para definir el paquete correctamente
    RELATIVE_PATH="${FILE_PATH#$BASE_PATH/}"
    SUB_PACKAGE=$(echo "$RELATIVE_PATH" | sed 's/\.kt//g; s/\//\./g')
    echo "package $PACKAGE_NAME.$SUB_PACKAGE" > "$FILE_PATH"
}

# --- Data Layer ---
TOUCH_AND_PACKAGE "$BASE_PATH/data/api/SpaceXApiService.kt"
TOUCH_AND_PACKAGE "$BASE_PATH/data/local/RocketDao.kt"
TOUCH_AND_PACKAGE "$BASE_PATH/data/local/SpaceAppsDatabase.kt"
TOUCH_AND_PACKAGE "$BASE_PATH/data/repository/RocketRepository.kt"

# --- Domain Layer ---
TOUCH_AND_PACKAGE "$BASE_PATH/domain/model/Rocket.kt"
TOUCH_AND_PACKAGE "$BASE_PATH/domain/usecase/GetRocketsUseCase.kt"

# --- UI Layer ---
# Ficheros de Páginas
TOUCH_AND_PACKAGE "$BASE_PATH/ui/pages/Splash.kt"
TOUCH_AND_PACKAGE "$BASE_PATH/ui/pages/Login.kt"
TOUCH_AND_PACKAGE "$BASE_PATH/ui/pages/Home.kt"
TOUCH_AND_PACKAGE "$BASE_PATH/ui/pages/SpaceRocketDetail.kt"
# Ficheros de Componentes
TOUCH_AND_PACKAGE "$BASE_PATH/ui/components/RocketCard.kt"
TOUCH_AND_PACKAGE "$BASE_PATH/ui/components/CommonStates.kt"
# Ficheros de ViewModel
TOUCH_AND_PACKAGE "$BASE_PATH/ui/viewmodel/RocketListViewModel.kt"
TOUCH_AND_PACKAGE "$BASE_PATH/ui/viewmodel/LoginViewModel.kt"

# 4. Crear ficheros de Test Placeholder (sin la línea de paquete por simplicidad)
touch "$TEST_BASE_PATH/androidTest/java/com/example/spaceapps/ui/tests/LoginUiTest.kt"
touch "$TEST_BASE_PATH/androidTest/java/com/example/spaceapps/ui/tests/RocketListUiTest.kt"

echo "✅ Estructura del proyecto SpaceApps creada exitosamente en $BASE_PATH."
