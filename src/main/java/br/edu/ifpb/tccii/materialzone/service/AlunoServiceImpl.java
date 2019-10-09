package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.AlunoService;
import br.edu.ifpb.tccii.materialzone.domain.Aluno;
import br.edu.ifpb.tccii.materialzone.domain.enumeration.Papel;
import br.edu.ifpb.tccii.materialzone.repository.AlunoRepository;
import br.edu.ifpb.tccii.materialzone.repository.ProfessorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired private AlunoRepository alunoRepository;
    @Autowired private ProfessorRepository professorRepository;

    public AlunoServiceImpl() { }

    public Aluno save(Aluno aluno) {
        log.debug("Request to save Aluno : {}", aluno);
        aluno.setPapel(Papel.ALUNO);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = aluno.getSenha();
        String passwordEncoded = passwordEncoder.encode(password);
        aluno.setSenha(passwordEncoded);
        if (!professorRepository.findByEmail(aluno.getEmail()).isPresent()
                && !alunoRepository.findByEmail(aluno.getEmail()).isPresent()){
            return alunoRepository.save(aluno);
        }
        return null;
    }

    @Override
    public Aluno update(Aluno aluno) {
        log.debug("Request to update Aluno : {}", aluno);
        Optional<Aluno> alunoOptional = alunoRepository.findById(aluno.getId());
        if (alunoOptional.isPresent()){
            Aluno alunoBanco = alunoOptional.get();
            aluno.setEmail(alunoBanco.getEmail());
            if (!aluno.getSenha().equalsIgnoreCase(alunoBanco.getSenha())){
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String password = aluno.getSenha();
                String passwordEncoded = passwordEncoder.encode(password);
                aluno.setSenha(passwordEncoded);
            }
            return alunoRepository.save(aluno);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Page<Aluno> findAll(Pageable pageable) {
        log.debug("Request to get all Alunos");
        return alunoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Aluno> findOne(String id) {
        log.debug("Request to get Aluno : {}", id);
        return alunoRepository.findById(id);
    }

    public void delete(String id) {
        log.debug("Request to delete Aluno : {}", id);
        alunoRepository.deleteById(id);
    }

}
