package br.edu.ifpb.tccii.materialzone.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "datacleanerService", url = "http://localhost:5000/dataclear")
public interface CepService {

    @GetMapping("/{text}")
    CepResponse getCep(@PathVariable List<String> text);
}
