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

    public GitUtil(String linkHttpRepo){
        this.linkHttpRepo = linkHttpRepo;
    }

    /**
     * Realiza clone (do comando: git clone <link_repo>) de um repositório do Github
     *
     * @return File file, referência para a raiz do diretório clonado
     */
    public File doClone(){
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(user, password);

        File file = new File("src/main/resources/temp-repositories");

        try {
            Git git = Git.cloneRepository()
                    .setURI(linkHttpRepo).setDirectory(file)
                    .setCredentialsProvider(credentialsProvider)
                    .call();

            return file;
        } catch (GitAPIException e) {
            log.debug("\nNão foi possível clonar o repositório: " + e.getMessage());
            return null;
        }
    }

    /**
     * Exclui o Diretório dado com todos seus sub-diretórios e arquivos:
     *
     * @param dir
     */
    public void deleteDir(File dir){
        String[] arqs;
        File arq;
        int i;

        arqs = dir.list();
        for(i=0; i<arqs.length; i++){
            arq = new File(dir.getPath(), arqs[i]);
            if(arq.isDirectory()){
                deleteDir(arq);
            } else {
                arq.delete();
            }
        }
        dir.delete();
    }
}
