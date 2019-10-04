package br.edu.ifpb.tccii.materialzone.config;

import br.edu.ifpb.tccii.materialzone.abstration.AlunoService;
import br.edu.ifpb.tccii.materialzone.abstration.ProfessorService;
import br.edu.ifpb.tccii.materialzone.abstration.UsuarioService;
import br.edu.ifpb.tccii.materialzone.domain.Aluno;
import br.edu.ifpb.tccii.materialzone.domain.Professor;
import br.edu.ifpb.tccii.materialzone.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private UsuarioService usuarioService;
    @Autowired private ProfessorService professorService;
    @Autowired private AlunoService alunoService;

    public ApplicationInit() { }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

//        Professor professor = new Professor();
//        professor.setEmail("romulo");
//        professor.setSenha("romulo");
//
//        Aluno aluno = new Aluno();
//        aluno.setEmail("renan");
//        aluno.setSenha("renan");
//
//        Professor professorSaved = professorService.save(professor);
//        Aluno alunoSaved = alunoService.save(aluno);
//
//        Usuario usuarioProfessor = new Usuario();
//        usuarioProfessor.setUsername(professorSaved.getEmail());
//        usuarioProfessor.setPassword(professorSaved.getSenha());
//        usuarioProfessor.setProfessor(true);
//
//        Usuario usuarioAluno = new Usuario();
//        usuarioAluno.setUsername(alunoSaved.getEmail());
//        usuarioAluno.setPassword(alunoSaved.getSenha());
//        usuarioAluno.setProfessor(false);
//
//        usuarioService.save(usuarioProfessor);
//        usuarioService.save(usuarioAluno);

    }

}
