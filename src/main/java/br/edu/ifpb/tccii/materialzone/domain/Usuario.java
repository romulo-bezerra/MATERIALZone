package br.edu.ifpb.tccii.materialzone.domain;

import br.edu.ifpb.tccii.materialzone.domain.enumeration.Papel;
import br.edu.ifpb.tccii.materialzone.domain.enumeration.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

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
    private Sexo sexo;
    private Papel papel;

    public Usuario() {} //For Spring Data

}
