package br.edu.ifpb.tccii.materialzone.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@ApiIgnore
@Document(indexName = "materiais", type = "material")
public class Material {

    @Id
    @ApiModelProperty( hidden = true)
    private String id;
    @NotNull
    private String linkRepositorio;
    @NotNull
    private String titulo;
    @NotNull
    private String descricao;

    @ApiModelProperty( hidden = true)
    private ZonedDateTime timestampCriacao;
    @ApiModelProperty( hidden = true)
    private String emailProfessor;
    @ApiModelProperty( hidden = true)
    private List<Categoria> categorias;
    @ApiModelProperty( hidden = true)
    private List<String> arquivosRepositorio;

    public Material(){ //For Spring Data
        this.categorias = new ArrayList<>();
        this.arquivosRepositorio = new ArrayList<>();
    }

    public boolean addCategoria(Categoria categoria){
        return this.categorias.add(categoria);
    }

}
