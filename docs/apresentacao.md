# Apresentação - Health Monitor

## Slide 1: Visão Geral
- **Título**: Health Monitor - Calculadora de IMC e Métricas de Saúde
- **Desenvolvido por**: Victor Hugo Monteiro da Silva e Murilo de Melo
- **Tecnologias**: Kotlin, Jetpack Compose, Room, MVVM

## Slide 2: Funcionalidades Principais
- Cálculo de IMC com classificação
- Taxa Metabólica Basal (TMB)
- Necessidade calórica diária
- Percentual de Gordura Corporal (US Navy)
- Peso Ideal
- Histórico com gráficos de evolução
- Tema claro/escuro

## Slide 3: Dificuldades Encontradas
- Implementação correta das fórmulas matemáticas (logaritmos, validações)
- Gerenciamento de estado com Compose + Flow
- Validação condicional de campos (quadril apenas para mulheres)
- Tratamento de erros para evitar NaN e valores negativos

## Slide 4: Uso de LLMs
- **Ferramentas**: Claude Opus 4.5, Gemini 3.0 PRO, ChatGPT-5
- **Destaques**:
    - Cálculo de Gordura Corporal (Gemini)
    - Função de IMC com classificações (Claude)
    - Temas e Design System (Gemini)
- **Avaliação**: Acelera desenvolvimento, bom para boilerplate, requer revisão