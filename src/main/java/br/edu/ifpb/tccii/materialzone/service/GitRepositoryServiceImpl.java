package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.GitRepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GitRepositoryServiceImpl implements GitRepositoryService {

    private final String USER = "material-zone";
    private final String PASSWORD = "materialzone135";
    private final String PATH_CLONE_REPO = "src/main/resources/temp-repositories";

    /**
     * Realiza clone (do comando: git clone <link_repo>) de um repositório do Github
     * @return File file, referência para a raiz do diretório clonado
     */
    @Override
    public File doClone(String linkHttpRepository) throws GitAPIException {
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(USER, PASSWORD);
        File file = new File(PATH_CLONE_REPO); //storage path
        Git git = Git.cloneRepository()
                .setURI(linkHttpRepository)
                .setDirectory(file)
                .setCredentialsProvider(credentialsProvider)
                .call();
        return file;
    }

}
