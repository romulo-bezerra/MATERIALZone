package br.edu.ifpb.tccii.materialzone.domain;

import br.edu.ifpb.tccii.materialzone.domain.enumeration.Sexo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "alunos", type = "aluno")
public class Aluno {

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
    private String curso;

    public Aluno() { } //For Spring Data

}
