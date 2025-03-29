package com.joaoaugustopf.cacatesouro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.joaoaugustopf.cacatesouro.Views.Ask
import com.joaoaugustopf.cacatesouro.Views.FinalScreen
import com.joaoaugustopf.cacatesouro.Views.Home

data class Pergunta(
    val titulo: String,
    val opcoes: List<String>,
    val indiceCorreto: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val perguntas = listOf(
                Pergunta(
                    titulo = "Qual é a capital da França?",
                    opcoes = listOf("Berlim", "Paris", "Roma", "Madri"),
                    indiceCorreto = 1
                ),
                Pergunta(
                    titulo = "Qual linguagem usamos para Android nativo?",
                    opcoes = listOf("Kotlin", "JavaScript", "Swift", "C#"),
                    indiceCorreto = 0
                ),
                Pergunta(
                    titulo = "Quem pintou a Mona Lisa?",
                    opcoes = listOf("Michelangelo", "Leonardo da Vinci", "Van Gogh", "Picasso"),
                    indiceCorreto = 1
                )
            )

            var tela by remember { mutableStateOf("inicio") }
            var indicePerguntaAtual by remember { mutableStateOf(0) }
            var podeAvancar by remember { mutableStateOf(false) }

            when (tela) {
                "inicio" -> Home {
                    tela = "pergunta"
                    podeAvancar = false
                }

                "pergunta" -> {
                    val perguntaAtual = perguntas[indicePerguntaAtual]
                    Ask(
                        tituloPergunta = perguntaAtual.titulo,
                        opcoes = perguntaAtual.opcoes,
                        indiceCorreto = perguntaAtual.indiceCorreto,
                        onResposta = { isCorreta -> podeAvancar = isCorreta },
                        podeAvancar = podeAvancar,
                        isUltimaPergunta = indicePerguntaAtual == perguntas.lastIndex,
                        onProxima = {
                            if (indicePerguntaAtual < perguntas.lastIndex) {
                                indicePerguntaAtual++
                                podeAvancar = false
                            } else {
                                tela = "final"
                            }
                        }
                    )
                }

                "final" -> FinalScreen {
                    indicePerguntaAtual = 0
                    podeAvancar = false
                    tela = "inicio"
                }
            }
        }
    }
}
