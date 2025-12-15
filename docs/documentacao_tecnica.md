# Documentação Técnica - Health Monitor IMC

## Estrutura do Projeto

```
app/src/main/java/com/example/calculadoraimc/
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt          # Configuração Room (Singleton)
│   │   ├── dao/IMCHistoryDao.kt    # Queries do banco
│   │   ├── entity/IMCHistory.kt    # Entidade principal
│   │   └── converter/Converters.kt # Type converters (Date, Gender)
│   └── repository/
│       └── IMCHistoryRepository.kt # Abstração de dados
├── datasource/
│   └── Calculations.kt             # Fórmulas de saúde
├── feature/
│   ├── home/
│   │   ├── view/Home.kt
│   │   ├── viewmodel/HomeViewModel.kt
│   │   ├── components/             # MainCard, MetricCard, IMCGraphic...
│   │   └── model/                  # IMCData, Gender, ActivityLevel...
│   ├── history/
│   │   ├── view/HistoryScreen.kt
│   │   ├── viewmodel/HistoryViewModel.kt
│   │   └── components/IMCHistoryChart.kt
│   ├── detail/
│   │   ├── view/DetailScreen.kt
│   │   └── viewmodel/DetailViewModel.kt
│   └── help/
│       └── view/HelpScreen.kt
└── ui/theme/
    ├── Color.kt                    # Paleta de cores (Tailwind-based)
    ├── Theme.kt                    # Light/Dark themes
    └── Type.kt                     # Tipografia (IBM Plex Sans)
```

---

## Arquitetura MVVM

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│    View     │ ──▶ │  ViewModel  │ ──▶ │ Repository  │ ──▶ │     DAO     │
│  (Compose)  │ ◀── │   (State)   │ ◀── │   (Flow)    │ ◀── │   (Room)    │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
```

**Fluxo de dados:**
1. Usuário interage com a UI (Compose)
2. ViewModel processa a ação e atualiza o estado
3. Repository faz a mediação com o banco de dados
4. Room persiste/recupera os dados
5. Flow propaga as mudanças de volta para a UI

---

## Telas do Aplicativo

| Tela | Descrição | ViewModel |
|------|-----------|-----------|
| **Home** | Entrada de dados e exibição dos resultados | HomeViewModel |
| **History** | Lista de registros com gráfico de evolução | HistoryViewModel |
| **Detail** | Detalhes completos de um registro | DetailViewModel |
| **Help** | Explicação das fórmulas utilizadas | - |

---

## Modelo de Dados

```kotlin
@Entity(tableName = "imc_history")
data class IMCHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Date,
    val weight: Double,
    val height: Int,
    val imc: Double,
    val imcClassification: String,
    val age: Int,
    val gender: Gender?,
    val bmr: Int?,
    val idealWeight: String?,
    val dailyCaloricNeed: Int?,
    val bodyFat: Float?
)
```

### Enums

```kotlin
enum class Gender { MALE, FEMALE }

enum class ActivityLevel(val displayName: String, val factor: Double) {
    SEDENTARY("Sedentário", 1.2),
    LIGHTLY_ACTIVE("Levemente Ativo", 1.375),
    MODERATELY_ACTIVE("Moderadamente Ativo", 1.55),
    VERY_ACTIVE("Muito Ativo", 1.725),
    EXTRA_ACTIVE("Extremamente Ativo", 1.9)
}
```

---

## Navegação

```kotlin
NavHost(startDestination = "home") {
    composable("home") { Home(...) }
    composable("history") { HistoryScreen(...) }
    composable("detail/{historyId}") { DetailScreen(...) }
    composable("help") { HelpScreen(...) }
}
```

---

## Componentes UI Principais

| Componente | Função |
|------------|--------|
| `MainCard` | Card principal com IMC, TMB, calorias e gordura |
| `MetricCard` | Cards menores para métricas individuais |
| `IMCGraphic` | Gráfico circular animado do IMC |
| `IMCHistoryChart` | Gráfico de linha da evolução (Vico) |
| `IMCCalculatorContainer` | Formulário de entrada de dados |
| `IconTag` | Ícone circular reutilizável |

---

## Design System

### Paleta de Cores (Tailwind-based)

| Cor | Hex | Uso |
|-----|-----|-----|
| Primary Dark | `#312E81` | indigo-900 |
| Primary Medium | `#3730A3` | indigo-800 |
| Accent Color | `#7C3AED` | purple-600 (principal) |
| Green Level | `#10B981` | Status normal |
| Orange Level | `#F59E0B` | Status atenção |
| Red Level | `#EF4444` | Status crítico |

### Gradiente Principal
```kotlin
Brush.linearGradient(
    colors = listOf(
        Color(0xFF7C3AED), // purple-600
        Color(0xFF6366F1)  // indigo-500
    )
)
```

### Tipografia
- **Fonte:** IBM Plex Sans
- **Pesos:** ExtraLight, Thin, Light, Regular, Medium, SemiBold, Bold

---

## Fórmulas Implementadas

### 1. IMC
```
IMC = peso (kg) / altura² (m)
```

| Faixa | Classificação |
|-------|---------------|
| < 18.5 | Abaixo do peso |
| 18.5 - 24.9 | Peso normal |
| 25.0 - 29.9 | Sobrepeso |
| 30.0 - 34.9 | Obesidade Grau I |
| 35.0 - 39.9 | Obesidade Grau II |
| ≥ 40.0 | Obesidade Grau III |

### 2. TMB (Mifflin-St Jeor)
```
Homens:   (10 × peso) + (6.25 × altura) - (5 × idade) + 5
Mulheres: (10 × peso) + (6.25 × altura) - (5 × idade) - 161
```

### 3. Peso Ideal (Devine)
```
Homens:   50 kg + 2.3 kg por polegada acima de 5 pés
Mulheres: 45.5 kg + 2.3 kg por polegada acima de 5 pés
```

### 4. Necessidade Calórica
```
NCD = TMB × Fator de Atividade
```

### 5. Gordura Corporal (US Navy)
```
Homens:   495 / (1.0324 - 0.19077 × log10(cintura - pescoço) + 0.15456 × log10(altura)) - 450
Mulheres: 495 / (1.29579 - 0.35004 × log10(cintura + quadril - pescoço) + 0.22100 × log10(altura)) - 450
```

---

## Validação de Campos

O `HomeViewModel` gerencia o estado de validação:

```kotlin
data class ValidationState(
    val heightError: Boolean = false,
    val weightError: Boolean = false,
    val ageError: Boolean = false,
    val genderError: Boolean = false,
    val activityLevelError: Boolean = false
)
```

**Campos obrigatórios:** Altura, Peso, Idade, Gênero, Nível de Atividade, Cintura, Pescoço
**Campo condicional:** Quadril (obrigatório apenas para mulheres)

---

## Dependências Principais

```groovy
// Room
implementation "androidx.room:room-runtime:2.6.1"
implementation "androidx.room:room-ktx:2.6.1"
kapt "androidx.room:room-compiler:2.6.1"

// Navigation
implementation "androidx.navigation:navigation-compose:2.7.7"

// Gráficos
implementation "com.patrykandpatrick.vico:compose:2.0.0"

// UI
implementation "androidx.compose.material3:material3"
implementation "androidx.compose.material:material-icons-extended"
```

---

## Uso de LLMs no Desenvolvimento

| Ferramenta | Uso |
|------------|-----|
| Claude | Lógica de cálculos, arquitetura |
| Gemini | Componentes UI, temas, validações |
| ChatGPT | Documentação, boilerplate |