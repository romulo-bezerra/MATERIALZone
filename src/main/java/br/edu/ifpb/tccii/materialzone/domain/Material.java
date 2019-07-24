package br.edu.ifpb.tccii.materialzone.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Document(indexName = "materiais", type = "material")
public class Material {

    @Id
    private String id;
    private String linkRepositorio;
    private String titulo;
    private String descricao;
    private ZonedDateTime timestampCriacao;
    private List<String> arquivosRepositorio;
    private Categoria categoria;

    public Material(){ //For Spring Data
        this.arquivosRepositorio = new ArrayList<>();
        this.timestampCriacao = ZonedDateTime.now(ZoneId.systemDefault()); //setting atual time
    }

}
