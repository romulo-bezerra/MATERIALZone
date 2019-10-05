package br.edu.ifpb.tccii.materialzone.abstration;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.util.List;

public interface GitRepositoryContentExtractor {

    List<String> extractContentRepository(String linkHttpRepository) throws GitAPIException;

}
