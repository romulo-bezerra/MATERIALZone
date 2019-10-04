package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.domain.Usuario;
import br.edu.ifpb.tccii.materialzone.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    final private String ROLE_PROFESSOR = "ROLE_PROFESSOR";
    final private String ROLE_ALUNO = "ROLE_ALUNO";

    @Autowired UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);
      List<GrantedAuthority> authorityListProfessor = AuthorityUtils.createAuthorityList(ROLE_ALUNO, ROLE_PROFESSOR);
      List<GrantedAuthority> authorityListAluno = AuthorityUtils.createAuthorityList(ROLE_ALUNO);
      Usuario usuario = optionalUsuario.get();
      return new User(usuario.getUsername(), usuario.getPassword(),
              usuario.isProfessor() ? authorityListProfessor : authorityListAluno);

    }

}
