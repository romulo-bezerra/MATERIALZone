package br.edu.ifpb.tccii.materialzone.domain;

import br.edu.ifpb.tccii.materialzone.domain.enumeration.Sexo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Document(indexName = "professores", type = "professor")
public final class Professor {

    @Id
    private String id;
    @NotNull
    private String nome;
    private byte[] foto;
    @Email
    private String email;
    @NotNull
    private String senha;
    @NotNull
    private String telefone;
    private String urlPerfilGithub;
    @NotNull
    private String campus;
    @NotNull
    private String instituto;
    private Sexo sexo;
    private String urlLattes;
    @NotNull
    private String areaEnsino;

    public Professor() {} //For Spring Data

}
