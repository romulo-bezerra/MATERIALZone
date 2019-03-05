package br.edu.ifpb.tcc.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GitUtil {

    private final Logger log = LoggerFactory.getLogger(GitUtil.class);
    private final String user = "romulo-soares";
    private final String password = "legacata135";
    private String linkHttpRepo;

    //Configurar diretório dinâmico (da aplicação)
    final String dir = "";

    public GitUtil(String linkHttpRepo){
        this.linkHttpRepo = linkHttpRepo;
    }

    /**
     *
     * @return File, referência para a raiz do diretório clonado
     */
    public File doClone(){

        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(user, password);

        //Configurar diretório dinâmico (da aplicação)
        final String path = "/home/romulo/Documentos/teste04";

        try {
            Git git = Git.cloneRepository()
                    .setURI(linkHttpRepo).setDirectory(new File(path))
                    .setCredentialsProvider(credentialsProvider).call();

            log.debug("\nPath retornado: " + path + "\n");
            return new File(path);
        } catch (GitAPIException e) {
            log.debug("\nNão foi possível clonar o repositório\n");
            log.error(e.getMessage());

            return null;
        }
    }
}
