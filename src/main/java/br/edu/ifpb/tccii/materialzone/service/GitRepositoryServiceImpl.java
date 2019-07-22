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

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String user = "material-zone";
    private final String password = "materialzone135";

    /**
     * Realiza clone (do comando: git clone <link_repo>) de um repositório do Github
     * @return File file, referência para a raiz do diretório clonado
     */
    @Override
    public File doClone(String linkHttpRepository) {
        //authenticate
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(user, password);
        //storage path
        File file = new File("src/main/resources/temp-repositories");

        try {
            Git git = Git.cloneRepository().setURI(linkHttpRepository)
                    .setDirectory(file).setCredentialsProvider(credentialsProvider).call();
            return file;
        } catch (GitAPIException e) {
            log.warn("Erro ao clonar o repositório. Link do repositório inconsistente!\n" + e.getMessage());
            return null;
        }
    }

}
