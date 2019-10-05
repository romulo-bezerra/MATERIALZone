package br.edu.ifpb.tccii.materialzone.service.auth;

import br.edu.ifpb.tccii.materialzone.domain.Aluno;
import br.edu.ifpb.tccii.materialzone.domain.Professor;
import br.edu.ifpb.tccii.materialzone.repository.AlunoRepository;
import br.edu.ifpb.tccii.materialzone.repository.ProfessorRepository;
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

    @Autowired ProfessorRepository professorRepository;
    @Autowired AlunoRepository alunoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      List<GrantedAuthority> authorityListProfessor = AuthorityUtils.createAuthorityList(ROLE_PROFESSOR);
      List<GrantedAuthority> authorityListAluno = AuthorityUtils.createAuthorityList(ROLE_ALUNO);

      Optional<Professor> optionalProfessor = professorRepository.findByEmail(email);
      Optional<Aluno> optionalAluno = alunoRepository.findByEmail(email);

      if (optionalProfessor.isPresent()) {
          Professor professor = optionalProfessor.get();
          return new User(professor.getEmail(), professor.getSenha(), authorityListProfessor);
      } else {
          Aluno aluno = optionalAluno.get();
          return new User(aluno.getEmail(), aluno.getSenha(), authorityListAluno);
      }

    }
}
