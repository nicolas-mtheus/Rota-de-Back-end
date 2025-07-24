package com.br.emakers.apiProjeto.controller.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data // Lombok para getters e setters
public class EmprestimoRequest {

    @NotNull(message = "ID do Livro é obrigatório")
    private Long idLivro;

    @NotNull(message = "ID da Pessoa é obrigatório")
    private Long idPessoa;

    @NotNull(message = "Data de Devolução Prevista é obrigatória")
    private LocalDate dataDevolucaoPrevista;
}