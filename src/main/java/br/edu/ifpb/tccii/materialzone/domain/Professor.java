package br.edu.ifpb.tccii.materialzone.domain;

import br.edu.ifpb.tccii.materialzone.domain.enumeration.Sexo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "professores", type = "professor")
public final class Professor {

    @Id
    private String id;
    private String nome;
    private byte[] foto;
    private String email;
    private String senha;
    private String telefone;
    private String urlPerfilGithub;
    private String campus;
    private String instituto;
    private Sexo sexo;
    private String urlLattes;
    private String areaEnsino;

    public Professor() {} //For Spring Data

}
