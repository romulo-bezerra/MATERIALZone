package br.edu.ifpb.tccii.materialzone.integration;

import br.edu.ifpb.tccii.materialzone.integration.dto.ResultClassifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "DataCleanerService", url = "http://localhost:5000/sortdata")
public interface ClassifierResultService {

    @GetMapping("/{content}")
    ResultClassifier getResultClassification(@PathVariable List<String> content);
}
