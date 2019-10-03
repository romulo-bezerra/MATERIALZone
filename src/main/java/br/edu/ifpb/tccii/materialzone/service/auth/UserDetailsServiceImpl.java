package br.edu.ifpb.tccii.materialzone.service.auth;

import br.edu.ifpb.tccii.materialzone.domain.Aluno;
import br.edu.ifpb.tccii.materialzone.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Aluno> optionalAluno = Optional.of(usuarioRepository.findByEmail(email));
        Aluno aluno = new Aluno();
        if (optionalAluno.isPresent()) {
            aluno = optionalAluno.get();
        }
        return new User(aluno.getUsername(), aluno.getPassword(),
                true, true, true, true,
                aluno.getAuthorities());
    }

}
