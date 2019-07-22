package br.edu.ifpb.tccii.materialzone.abstration;

import java.util.List;

public interface GitRepositoryContentExtractor {
    List<String> extractContentRepository(String linkHttpRepository);
}
