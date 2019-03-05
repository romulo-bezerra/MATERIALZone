package br.edu.ifpb.tcc.config;

import br.edu.ifpb.tcc.util.RestoreSubCategorias;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent> {

    private final RestoreSubCategorias restoreSubCategorias;

    public ApplicationInit(RestoreSubCategorias restoreSubCategorias) {
        this.restoreSubCategorias = restoreSubCategorias;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        restoreSubCategorias.restoreSubcategoriaAndroid();
    }

}
