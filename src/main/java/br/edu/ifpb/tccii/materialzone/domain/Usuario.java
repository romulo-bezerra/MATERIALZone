package br.edu.ifpb.tccii.materialzone.domain;

import br.edu.ifpb.tccii.materialzone.domain.enumeration.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@Document(indexName = "usuarios", type = "usuario")
public class Usuario {

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
    @Field(type = FieldType.Text)
    private Sexo sexo;

    public Usuario() {} //For Spring Data

}
