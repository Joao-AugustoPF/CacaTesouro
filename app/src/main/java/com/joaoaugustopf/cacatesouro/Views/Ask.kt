package com.joaoaugustopf.cacatesouro.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


@Composable
fun Ask(
    tituloPergunta: String,
    opcoes: List<String>,
    indiceCorreto: Int,
    onResposta: (Boolean) -> Unit,
    podeAvancar: Boolean,
    onProxima: () -> Unit,
    isUltimaPergunta: Boolean
) {
    var indiceSelecionado by remember { mutableStateOf<Int?>(null) }
    var ultimaRespostaCorreta by remember { mutableStateOf<Boolean?>(null) }

    // Resetar ao mudar de pergunta
    LaunchedEffect (tituloPergunta) {
        indiceSelecionado = null
        ultimaRespostaCorreta = null
        onResposta(false) // também reseta o estado externo no MainActivity
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = tituloPergunta,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        opcoes.forEachIndexed { index, opcao ->
            val selecionado = indiceSelecionado == index
            Button(
                onClick = {
                    indiceSelecionado = index
                    val isCorreta = index == indiceCorreto
                    ultimaRespostaCorreta = isCorreta
                    onResposta(isCorreta)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selecionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(opcao)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (ultimaRespostaCorreta) {
            true -> Text("✅ Resposta correta!", color = Color.Green)
            false -> Text("❌ Resposta incorreta. Tente novamente.", color = Color.Red)
            null -> {} // não mostra nada
        }

        Spacer(modifier = Modifier.weight(1f))

        if (podeAvancar) {
            Button(
                onClick = onProxima,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isUltimaPergunta) "Finalizar" else "Próxima")
            }
        }
    }
}
