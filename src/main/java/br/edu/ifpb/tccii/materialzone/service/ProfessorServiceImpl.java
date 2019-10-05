package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.ProfessorService;
import br.edu.ifpb.tccii.materialzone.domain.Professor;
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
public class ProfessorServiceImpl implements ProfessorService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired private ProfessorRepository professorRepository;
    @Autowired private AlunoRepository alunoRepository;

    public ProfessorServiceImpl() { }

    public Professor save(Professor professor) {
        log.debug("Request to save Professor : {}", professor);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = professor.getSenha();
        String passwordEncoded = passwordEncoder.encode(password);
        professor.setSenha(passwordEncoded);
        if (!professorRepository.findByEmail(professor.getEmail()).isPresent()
                && !alunoRepository.findByEmail(professor.getEmail()).isPresent()){
            return professorRepository.save(professor);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Page<Professor> findAll(Pageable pageable) {
        log.debug("Request to get all Professores");
        return professorRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Professor> findOne(String id) {
        log.debug("Request to get Professor : {}", id);
        return professorRepository.findById(id);
    }

    public void delete(String id) {
        log.debug("Request to delete Professor : {}", id);
        professorRepository.deleteById(id);
    }

}
