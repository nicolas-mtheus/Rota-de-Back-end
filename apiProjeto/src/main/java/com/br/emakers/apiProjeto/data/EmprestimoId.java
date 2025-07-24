package com.br.emakers.apiProjeto.data;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable // Indica que esta classe pode ser embutida como parte de uma chave primária
@Data // Lombok para getters, setters, equals, hashCode, toString
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoId implements Serializable { // Deve implementar Serializable

    private Long idLivro; // Corresponde ao idLivro da entidade Livro
    private Long idPessoa; // Corresponde ao idPessoa da entidade Pessoa

    // É crucial sobrescrever equals() e hashCode() para chaves compostas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmprestimoId that = (EmprestimoId) o;
        return Objects.equals(idLivro, that.idLivro) &&
               Objects.equals(idPessoa, that.idPessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLivro, idPessoa);
    }
}