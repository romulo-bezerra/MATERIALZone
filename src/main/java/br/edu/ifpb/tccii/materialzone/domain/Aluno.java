package br.edu.ifpb.tccii.materialzone.domain;

import br.edu.ifpb.tccii.materialzone.domain.enumeration.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@Document(indexName = "usuarios", type = "aluno")
public class Aluno implements UserDetails {

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
    private List<Role> roles;

    public Aluno() {
        this.roles = new ArrayList<>();
    } //For Spring Data

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
