package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.FileManager;
import br.edu.ifpb.tccii.materialzone.abstration.GitRepositoryContentExtractor;
import br.edu.ifpb.tccii.materialzone.abstration.GitRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitRepositoryContentExtractorImpl implements GitRepositoryContentExtractor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final GitRepositoryService gitRepositoryService;
    private final FileManager fileManager;

    public GitRepositoryContentExtractorImpl(GitRepositoryService gitRepositoryService, FileManager fileManager) {
        this.gitRepositoryService = gitRepositoryService;
        this.fileManager = fileManager;
    }

    /**
     * Recupera todos os arquivos passivos de leitura (texto - não explicitamente) do repositório
     * @param linkHttpRepository link HTTP do repositório no Github
     * @return Uma lista de strings contendo os arquivos lidos do diretório. Cada String representa um arquivo
     */
    @Override
    public List<String> extractContentRepository(String linkHttpRepository) {
        log.debug("Clonando repositório");
        File clonedRepository = gitRepositoryService.doClone(linkHttpRepository);
        log.debug("Repositório clonado!");

        List<String> aggregateLines = new ArrayList<>();
        File[] arquivosParaAnalise = fileManager.getAllFilesDirectory(clonedRepository);

        log.debug("Escaneando arquivos...");
        for (File scannedFile : arquivosParaAnalise){
            if (isFileRead(scannedFile)){
                String contentFile = fileManager.readContentFile(scannedFile);
                aggregateLines.add(contentFile);
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

    /**
     * Verifica pela extensão se o arquivo é passivo de leitura pela aplicação
     * @param file arquivo para análise
     * @return true se o arquivo for passivo de leitura e false caso contrário
     */
    private Boolean isFileRead(File file) {
        String nameFile = file.getName();
        return (!nameFile.endsWith("jar") && !nameFile.endsWith("png")
                && !nameFile.endsWith("jpeg") && !nameFile.endsWith("gz") && !nameFile.endsWith("gif")
                && !nameFile.endsWith("tiff") && !nameFile.endsWith("bmp") && !nameFile.endsWith("psd")
                && !nameFile.endsWith("exif") && !nameFile.endsWith("zip") && !nameFile.endsWith("rar")
                && !nameFile.endsWith("tar") && !nameFile.endsWith("z") && !nameFile.endsWith("taz")
                && !nameFile.endsWith("tgz") && !nameFile.endsWith("arj") && !nameFile.endsWith("woff2")
                && !nameFile.endsWith("woff") && !nameFile.endsWith("md") && !nameFile.endsWith("jpg")
                && !nameFile.endsWith("svg") && !nameFile.endsWith("otf") && !nameFile.endsWith("tff")
                && !nameFile.endsWith("eot") && !nameFile.endsWith("exe") && !nameFile.endsWith("pdf")
                && !nameFile.endsWith("doc") && !nameFile.endsWith("dll") && !nameFile.endsWith("docx")
                && !nameFile.endsWith("ppt") && !nameFile.endsWith("tif") && !nameFile.endsWith("pps")
                && !nameFile.endsWith("bat") && !nameFile.endsWith("xls") && !nameFile.endsWith("bin")
                && !nameFile.endsWith("xlsx") && !nameFile.endsWith("cab") && !nameFile.endsWith("com")
                && !nameFile.endsWith("ico") && !nameFile.endsWith("ttf") && !nameFile.endsWith("tmp")
                && !nameFile.endsWith("epgz") && !nameFile.endsWith("properties") && !nameFile.endsWith("mf")
                && !nameFile.endsWith("rs") && !nameFile.endsWith("class") && !nameFile.endsWith("mp4")
                && !nameFile.endsWith("mp3") && !nameFile.endsWith("iml") && !nameFile.endsWith("xml"));
    }

}
