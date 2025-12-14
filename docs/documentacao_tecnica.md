# Documentação Técnica

## Fórmulas Utilizadas

### IMC
```
IMC = peso (kg) / (altura (m) * altura (m))
```

### Taxa Metabólica Basal (TMB) - Fórmula de Harris-Benedict
```
Homens: TMB = 88.362 + (13.397 × peso em kg) + (4.799 × altura em cm) - (5.677 × idade em anos)
Mulheres: TMB = 447.593 + (9.247 × peso em kg) + (3.098 × altura em cm) - (4.330 × idade em anos)
```

### Necessidade Calórica Diária
```
NCD = TMB × Fator de Atividade
- Sedentário: 1.2
- Levemente ativo: 1.375
- Moderadamente ativo: 1.55
- Muito ativo: 1.725
- Extremamente ativo: 1.9
```

## Modelo de Dados

```kotlin
@Entity(tableName = "imc_history")
data class IMCHistory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDateTime,
    val weight: Double,
    val height: Double,
    val imc: Double,
    val classification: String,
    val tmb: Double,
    val dailyCalories: Double,
    val idealWeight: Double
)
```

## Persistência

A persistência foi implementada utilizando:
- **Room** como camada de abstração sobre SQLite
- **DataStore** para preferências do usuário
- **TypeConverters** para tipos complexos
- **Repository pattern** para abstrair a fonte de dados

## Melhorias Futuras

1. **Integração com APIs** de saúde (Google Fit, Apple Health)
2. **Autenticação de usuário** para sincronização entre dispositivos
3. **Backup na nuvem** dos dados do usuário
4. **Exportação de relatórios** em PDF
5. **Recomendações personalizadas** baseadas nos dados coletados
6. **Acompanhamento de metas** de peso e saúde
