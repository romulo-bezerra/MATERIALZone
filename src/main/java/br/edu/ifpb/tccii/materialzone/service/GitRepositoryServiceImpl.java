package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.GitRepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GitRepositoryServiceImpl implements GitRepositoryService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String USER = "material-zone";
    private final String PASSWORD = "materialzone135";
    private final String PATH_CLONE_REPO = "src/main/resources/temp-repositories";

    /**
     * Realiza clone (do comando: git clone <link_repo>) de um repositório do Github
     * @return File file, referência para a raiz do diretório clonado
     */
    @Override
    public File doClone(String linkHttpRepository) {
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(USER, PASSWORD);
        File file = new File(PATH_CLONE_REPO); //storage path
        try {
            Git git = Git.cloneRepository()
                    .setURI(linkHttpRepository)
                    .setDirectory(file)
                    .setCredentialsProvider(credentialsProvider)
                    .call();
            return file;
        } catch (GitAPIException e) {
            LOG.warn("Erro ao clonar o repositório. Link do repositório inconsistente!\n" + e.getMessage());
            return null;
        }
    }

}
