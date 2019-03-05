package br.edu.ifpb.tcc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "comentarios", type = "comentario")
public class Comentario {

    @Id
    private String id;
    private String texto;

}
