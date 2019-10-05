package br.edu.ifpb.tccii.materialzone.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@Document(indexName = "categorias", type = "categoria")
public class Categoria {

    @Id
    private String id;
    private String materialId;
    private String nome;
    private double pontuacaoFinalClassificacao;

    public Categoria() { } //For Spring Data

}
