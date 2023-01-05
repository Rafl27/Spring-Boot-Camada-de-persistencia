package br.com.alura.forum.controller

import br.com.alura.forum.dto.AtualizacaoTopicoForm
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.dto.TopicoView
import br.com.alura.forum.service.TopicoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/topicos")
class TopicoController(private val service: TopicoService) {

    @GetMapping
    //Adicionando o parametro @RequestParam nos parametros da funcao, podemos adicionar ou nao o nome do curso na uri
    //Por meio de um pageable, podemos receber quantos itens deverao ser carregados em uma pagina, ao inves de simplesmente retornar todos
    //pois em um banco muito extenso, o tempo de resposta sera cada vez maior

    /*
    pq usar Page e nao uma List? pq a paginacao alem de ser uma lista traz informacoes importantes para o front end
    como, total de itens, pagina atual, quantas ainda faltam etc, basicamente toda a logica necessaria para se
    trabalhar com paginas.

    para determinar quantos itens deverao retornar em cada pagina, basta passar na uri, ?size=5, nesse caso seriam retornados
    5 itens.

    se tiver uma page com 6 itens, mas limitada a um size de 5, ela tera 2 paginas, para acessar a pagina 2, basta utilizar page
    dessa forma: ?size=5&page=1
    como de constume comeca do 0

    mas caso n tenham parametros na uri, podemos usar o @pageabledefault tambem

     */
    fun listar(@RequestParam(required = false,) nomeCurso: String?,
               @PageableDefault(size = 5) paginacao: Pageable): Page<TopicoView> {
        return service.listar(nomeCurso, paginacao)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        return service.buscarPorId(id)
    }

    @PostMapping
    @Transactional
    fun cadastrar(
            @RequestBody @Valid form: NovoTopicoForm,
            uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoView> {
        val topicoView = service.cadastrar(form)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping
    @Transactional
    fun atualizar(@RequestBody @Valid form: AtualizacaoTopicoForm): ResponseEntity<TopicoView> {
        val topicoView = service.atualizar(form)
        return ResponseEntity.ok(topicoView)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun deletar(@PathVariable id: Long) {
        service.deletar(id)
    }

}