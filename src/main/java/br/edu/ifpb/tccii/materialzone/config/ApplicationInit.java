//package br.edu.ifpb.tccii.materialzone.config;
//
//import br.edu.ifpb.tccii.materialzone.abstration.MaterialService;
//import br.edu.ifpb.tccii.materialzone.domain.Categoria;
//import br.edu.ifpb.tccii.materialzone.repository.CategoriaRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent> {
//
//    @Autowired private CategoriaRepository categoriaRepository;
//
//    public ApplicationInit() { }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//
//        List<String> strings = new ArrayList<>();
//        strings.add("#-?_keySlice?_-#meu name #-?_keySlice?_-# is#-?_keySlice?_-# [#-?_keySlice?_-#romulo#-?_keySlice?_-#");
//
//        System.out.println(removeKeySlices(strings));
//
//
//    }
//
//    final private List<String> removeKeySlices(List<String> arquivosRepo) {
//        List<String> result = new ArrayList<>();
//        String keySlice = "#-?_keySlice?_-#";
//        for (String s : arquivosRepo) {
//            result.add(s.replace(keySlice, ""));
//        }
//        return result;
//    }
//
//}
