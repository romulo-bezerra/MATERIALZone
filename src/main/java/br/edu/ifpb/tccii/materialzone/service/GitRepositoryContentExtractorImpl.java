package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.FileManager;
import br.edu.ifpb.tccii.materialzone.abstration.GitRepositoryContentExtractor;
import br.edu.ifpb.tccii.materialzone.abstration.GitRepositoryService;
import br.edu.ifpb.tccii.materialzone.abstration.MimeTypeIdentifier;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitRepositoryContentExtractorImpl implements GitRepositoryContentExtractor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired private GitRepositoryService gitRepositoryService;
    @Autowired private FileManager fileManager;
    @Autowired private MimeTypeIdentifier mimeTypeIdentifier;

    public GitRepositoryContentExtractorImpl() { }

    /**
     * Recupera todos os arquivos passivos de leitura (texto - não explicitamente) do repositório
     * @param linkHttpRepository link HTTP do repositório no Github
     * @return Uma lista de strings contendo os arquivos lidos do diretório. Cada String representa um arquivo
     */
    @Override
    public List<String> extractContentRepository(String linkHttpRepository) throws GitAPIException {
        log.debug("Clonando repositório");
        File clonedRepository = gitRepositoryService.doClone(linkHttpRepository);
        log.debug("Repositório clonado!");

        List<String> aggregateLines = new ArrayList<>();
        File[] arquivosParaAnalise = fileManager.getAllFilesDirectory(clonedRepository);

        log.debug("Escaneando arquivos...");
        for (File scannedFile : arquivosParaAnalise){
            if (isPassiveReadFile(scannedFile)){
                log.debug("### Arquivo lido: " + scannedFile.getName());
                String contentFile = fileManager.readContentFile(scannedFile);
                aggregateLines.add(contentFile.concat("#-?_keySlice?_-#"));
            }
        }
        log.debug("Arquivos escaneados: " + arquivosParaAnalise.length);
        log.debug("Escaneamento finalizado!");
        log.debug("Deletando diretório de repositório temporário...");
        //Deleta o arquivo clonado após o escaneamento
        fileManager.deleteDirectories(clonedRepository);
        log.debug("Repositório deletado!");

        return aggregateLines;
    }

    private boolean isPassiveReadFile(File file){
        return mimeTypeIdentifier.detectMimeType(file).startsWith("text");
    }

}
