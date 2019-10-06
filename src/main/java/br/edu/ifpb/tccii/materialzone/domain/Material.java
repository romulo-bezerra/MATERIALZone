package br.edu.ifpb.tccii.materialzone.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.NotNull;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Document(indexName = "materiais", type = "material")
public class Material {

    @Id
    private String id;
    @NotNull
    private String linkRepositorio;
    @NotNull
    private String titulo;
    @NotNull
    private String descricao;
    private ZonedDateTime timestampCriacao;
    private String emailProfessor;
    private List<Categoria> categorias;
    private List<String> arquivosRepositorio;

    public Material(){ //For Spring Data
        this.categorias = new ArrayList<>();
        this.arquivosRepositorio = new ArrayList<>();
    }

    public boolean addCategoria(Categoria categoria){
        return this.categorias.add(categoria);
    }

}
