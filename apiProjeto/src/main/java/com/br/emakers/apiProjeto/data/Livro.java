package com.br.emakers.apiProjeto.data;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor; // Importe LocalDate para data_lancamento

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivro;

    @NotBlank(message = "Nome do livro é obrigatório")
    @Size(max = 100, message = "O nome do livro deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "Autor é obrigatório")
    @Size(max = 100, message = "O nome do autor deve ter no máximo 100 caracteres")
    private String autor;

    @NotNull(message = "Data de lançamento é obrigatória")
    private LocalDate data_lancamento; // Usando LocalDate para representar a data

}