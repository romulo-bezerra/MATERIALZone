package br.edu.ifpb.tcc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "avaliacoes", type = "avaliacao")
public class Avaliacao {

    @Id
    private String id;
    private int classificacao;

}
