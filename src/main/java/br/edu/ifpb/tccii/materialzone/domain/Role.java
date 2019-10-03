package br.edu.ifpb.tccii.materialzone.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@Document(indexName = "roles", type = "role")
public class Role implements GrantedAuthority {

    @Id
    private String role;

    @Override
    public String getAuthority() {
        return this.role;
    }

    public Role() { } //For Spring Data

}
