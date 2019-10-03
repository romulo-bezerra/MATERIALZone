package br.edu.ifpb.tccii.materialzone.config;

import br.edu.ifpb.tccii.materialzone.abstration.RoleService;
import br.edu.ifpb.tccii.materialzone.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private RoleService roleService;

    final private String ROLE_PROFESSOR = "ROLE_PROFESSOR";
    final private String ROLE_ALUNO = "ROLE_ALUNO";

    public ApplicationInit() { }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Role roleProfessor = new Role();
        roleProfessor.setRole(ROLE_PROFESSOR);
        Role roleAluno= new Role();
        roleAluno.setRole(ROLE_ALUNO);

        roleService.save(roleProfessor);
        roleService.save(roleAluno);
    }

}
