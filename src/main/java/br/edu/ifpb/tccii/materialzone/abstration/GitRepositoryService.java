package br.edu.ifpb.tccii.materialzone.abstration;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public interface GitRepositoryService {

    File doClone(String linkHttpRepository) throws GitAPIException;

}
