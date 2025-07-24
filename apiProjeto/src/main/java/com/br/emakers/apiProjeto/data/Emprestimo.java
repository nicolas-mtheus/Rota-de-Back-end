package com.br.emakers.apiProjeto.data;

import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emprestimo") // Define o nome da tabela no banco de dados
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emprestimo {

    @EmbeddedId // Indica que a chave primária é composta e embutida
    private EmprestimoId id; // Usará a classe EmprestimoId como a chave

    @ManyToOne // Define a relação de muitos para um com Livro
    @MapsId("idLivro") // Mapeia o campo idLivro da EmprestimoId para a chave estrangeira de Livro
    @JoinColumn(name = "id_livro", insertable = false, updatable = false) // Coluna no DB e não permite inserção/atualização direta via essa relação
    private Livro livro;

    @ManyToOne // Define a relação de muitos para um com Pessoa
    @MapsId("idPessoa") // Mapeia o campo idPessoa da EmprestimoId para a chave estrangeira de Pessoa
    @JoinColumn(name = "id_pessoa", insertable = false, updatable = false) // Coluna no DB e não permite inserção/atualização direta via essa relação
    private Pessoa pessoa;

    private LocalDate dataEmprestimo; // Data em que o livro foi emprestado
    private LocalDate dataDevolucaoPrevista; // Data prevista para devolução
    private LocalDate dataDevolucaoReal; // Data real de devolução (será nula até ser devolvido)

    // Método auxiliar para criar uma instância de Emprestimo
    public Emprestimo(Livro livro, Pessoa pessoa, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
        this.id = new EmprestimoId(livro.getIdLivro(), pessoa.getIdPessoa());
        this.livro = livro;
        this.pessoa = pessoa;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }
}