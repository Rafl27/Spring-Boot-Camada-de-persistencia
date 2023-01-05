package br.com.alura.forum.repository

import br.com.alura.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TopicoRepository: JpaRepository<Topico, Long> {
    //nesse caso, a JPA sabe navegar entre os join e objetos
    //para isso deve ser usado o padrao findBy
    fun findByCursoNome(nomeCurso: String, paginacao : Pageable): Page<Topico>
}