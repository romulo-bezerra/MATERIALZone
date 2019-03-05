package br.edu.ifpb.tcc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Document(indexName = "categorias", type = "categoria")
public class Categoria {

    @Id
    private String id;
    private String nome;
    private String subcategoria;
    private List<String> palavrasRelacionadas;

    public Categoria(){
        this.palavrasRelacionadas = new ArrayList<>();
    }

}
