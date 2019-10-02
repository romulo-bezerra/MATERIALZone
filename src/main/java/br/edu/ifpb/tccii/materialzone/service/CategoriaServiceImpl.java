package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.CategoriaService;
import br.edu.ifpb.tccii.materialzone.domain.Categoria;
import br.edu.ifpb.tccii.materialzone.repository.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired private CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl() { }

    @Override
    public Categoria save(Categoria categoria) {
        log.debug("Request to save Categoria : {}", categoria);
        return categoriaRepository.save(categoria);
    }
}
