package br.edu.ifpb.tccii.materialzone.config;

import br.edu.ifpb.tccii.materialzone.abstration.CategoriaService;
import br.edu.ifpb.tccii.materialzone.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private CategoriaService categoriaService;

    public ApplicationInit() { }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        insertBancoDadosCategory();
        insertProgramacaoOrientadaObjetoCategory();
        insertLinguagemMarcacaoCategory();
        insertTesteSoftwareCategory();
        insertLinguagemScriptCategory();
    }

    private void insertBancoDadosCategory(){
        final String nameCategory = "Banco de Dados";
        Categoria bdCategoria = new Categoria();
        bdCategoria.setNome(nameCategory);
        if (!categoriaService.findByName(nameCategory).isPresent()){
            categoriaService.save(bdCategoria);
        }
    }

    private void insertProgramacaoOrientadaObjetoCategory(){
        final String nameCategory = "Programação Orientada a Objeto";
        Categoria pooCategoria = new Categoria();
        pooCategoria.setNome(nameCategory);
        if (!categoriaService.findByName(nameCategory).isPresent()){
            categoriaService.save(pooCategoria);
        }
    }

    private void insertLinguagemMarcacaoCategory(){
        final String nameCategory = "Linguagem de Marcação";
        Categoria lmCategoria = new Categoria();
        lmCategoria.setNome(nameCategory);
        if (!categoriaService.findByName(nameCategory).isPresent()){
            categoriaService.save(lmCategoria);
        }
    }

    private void insertTesteSoftwareCategory(){
        final String nameCategory = "Teste de Software";
        Categoria tsCategoria = new Categoria();
        tsCategoria.setNome(nameCategory);
        if (!categoriaService.findByName(nameCategory).isPresent()){
            categoriaService.save(tsCategoria);
        }
    }

    private void insertLinguagemScriptCategory(){
        final String nameCategory = "Linguagem de Script";
        Categoria lsCategoria = new Categoria();
        lsCategoria.setNome(nameCategory);
        if (!categoriaService.findByName(nameCategory).isPresent()){
            categoriaService.save(lsCategoria);
        }
    }

}
