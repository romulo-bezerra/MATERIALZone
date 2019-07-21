package br.edu.ifpb.tcc.domain;

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
    private String email;
    private String senha;
    private String telefone;
    private String urlPerfilGithub;
    private String campus;
    private String instituto;
    private String sexo;

    public Usuario() {}

}
