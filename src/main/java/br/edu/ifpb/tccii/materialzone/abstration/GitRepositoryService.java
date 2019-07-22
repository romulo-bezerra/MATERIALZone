package br.edu.ifpb.tccii.materialzone.abstration;

import java.io.File;

public interface GitRepositoryService {
    File doClone(String linkHttpRepository);
}
