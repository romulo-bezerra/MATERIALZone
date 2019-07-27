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
    private List<String> categoriasIds;

    public Material(){ //For Spring Data
        this.categoriasIds = new ArrayList<>();
        this.arquivosRepositorio = new ArrayList<>();
    }

    public boolean addCategoria(String categoriaId){
        return this.categoriasIds.add(categoriaId);
    }

}
