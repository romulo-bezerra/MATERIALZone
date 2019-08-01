package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

@Service
public class FileManagerImpl implements FileManager {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Retorna uma array de arquivos de texto contidos no diretório
     * @param rootDirectory representa o diretório raiz do repositório
     * @return File[] filesEncontrados
     */
    @Override
    public File[] getAllFilesDirectory(File rootDirectory) {
        Vector enc = new Vector();

        File[] files = rootDirectory.listFiles();
        for (File f : files) {
            if (f.isDirectory() && !f.isHidden()) {
                //Adiciona no Vector os arquivos encontrados dentro de 'files[i]':
                File[] recFiles = getAllFilesDirectory(f);
                for (File rf : recFiles) enc.addElement(rf);
            } else {
                //Adiciona no Vector o arquivo encontrado dentro de 'dir':
                if(!f.isHidden()) enc.addElement(f);
            }
        }
        //Transforma um Vector em um File[]:
        File[] filesEncontrados = new File[enc.size()];

        for (int i = 0; i < enc.size(); i++) {
            filesEncontrados[i] = (File) enc.elementAt(i);
        }
        return filesEncontrados;
    }

    /**
     * Lê todo o conteúdo de texto do arquivo
     * @param file arquivo a ser analisado
     * @return string com o conteúdo do arquivo
     */
    @Override
    public String readContentFile(File file) {
        Path path = Paths.get(file.toURI());
        try {
            String longText = Files.readAllLines(path, StandardCharsets.ISO_8859_1).toString();
            return longText;
        } catch (IOException e) {
            log.warn("Falha ao lê o arquivo\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Exclui o Diretório dado com todos seus sub-diretórios e arquivos
     * @param dir representa o arquivo raiz do repositório
     */
    @Override
    public void deleteDirectories(File dir) {
        String[] arqs = dir.list();
        File arq;
        for(int i = 0; i < arqs.length; i++){
            arq = new File(dir.getPath(), arqs[i]);
            if(arq.isDirectory()) deleteDirectories(arq);
            else arq.delete();
        }
        dir.delete();
    }

}
