package br.edu.ifpb.tccii.materialzone.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "usuarios", type = "professor")
public final class Professor {

    private String urlLattes;
    private String areaEnsino;

    public Professor() {} //For Spring Data

}
