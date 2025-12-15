# 🏋️ Health Monitor - Calculadora de IMC & Saúde

Aplicativo Android para monitoramento de saúde com cálculo de IMC, Taxa Metabólica Basal, Necessidade Calórica e Percentual de Gordura Corporal.

**Desenvolvido por:** Victor Hugo Monteiro da Silva e Murilo de Melo

---

## 📱 Funcionalidades

- **IMC** - Índice de Massa Corporal com classificação
- **TMB** - Taxa Metabólica Basal (Mifflin-St Jeor)
- **Necessidade Calórica** - Baseada no nível de atividade física
- **Gordura Corporal** - Método da Marinha dos EUA
- **Peso Ideal** - Fórmula de Devine
- **Histórico** - Persistência local com gráfico de evolução
- **Tema Claro/Escuro**

---

## 🛠 Tecnologias

| Tecnologia | Uso |
|------------|-----|
| Kotlin | Linguagem principal |
| Jetpack Compose | Interface declarativa |
| Room | Persistência local (SQLite) |
| Navigation Compose | Navegação entre telas |
| Vico | Gráficos de evolução |
| Material 3 | Design system |

**Arquitetura:** MVVM com Repository Pattern

---

## 🚀 Como Executar

1. Clone o repositório
2. Abra no Android Studio
3. Sincronize o Gradle
4. Execute em dispositivo/emulador (API 24+)

---

## 📐 Fórmulas Utilizadas

### IMC
```
IMC = peso (kg) / altura² (m)
```

### TMB (Mifflin-St Jeor)
```
Homens:   (10 × peso) + (6.25 × altura) - (5 × idade) + 5
Mulheres: (10 × peso) + (6.25 × altura) - (5 × idade) - 161
```

### Gordura Corporal (US Navy)
```
Homens:   495 / (1.0324 - 0.19077 × log10(cintura - pescoço) + 0.15456 × log10(altura)) - 450
Mulheres: 495 / (1.29579 - 0.35004 × log10(cintura + quadril - pescoço) + 0.22100 × log10(altura)) - 450
```

## 📄 Documentação

Consulte a [documentação técnica](./docs/documentacao_tecnica.md) ou [Documentation_IMC_project.pdf](docs/Documentation_IMC_project.pdf) para mais detalhes sobre as implementação.

## apresentação a ser feita
Acesse o [guia da apresentação](./docs/apresentacao.md) ou [IMC_project_presentation.pdf](docs/IMC_project_presentation.pdf) para ver os slides e tópicos que serão abordados.