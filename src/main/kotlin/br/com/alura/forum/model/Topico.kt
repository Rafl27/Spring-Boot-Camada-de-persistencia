package br.com.alura.forum.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class  Topico(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var titulo: String,
        var mensagem: String,
        val dataCriacao: LocalDateTime = LocalDateTime.now(),
        @ManyToOne
        val curso: Curso,
        @ManyToOne
        val autor: Usuario,
        //Utilizando type.string, será de fato salvo as string presentes no enum.
        @Enumerated(value = EnumType.STRING)
        val status: StatusTopico = StatusTopico.NAO_RESPONDIDO,
        @OneToMany(mappedBy="topico")
        val respostas: List<Resposta> = ArrayList()
)