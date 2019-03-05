package br.edu.ifpb.tcc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FileUtil {

    private final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public File[] getFiles(File dir) {
        Vector enc = new Vector();
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isDirectory() && !f.isHidden()) {
                //Adiciona no Vector os arquivos encontrados dentro de 'files[i]':
                File[] recFiles = getFiles(f);
                for (File rf : recFiles) {
                    enc.addElement(rf);
                }
            } else {
                //Adiciona no Vector o arquivo encontrado dentro de 'dir':
                if(!f.isHidden()){
                    enc.addElement(f);
                }
            }
        }
        //Transforma um Vector em um File[]:
        File[] encontrados = new File[enc.size()];
        for (int i = 0; i < enc.size(); i++) {
            encontrados[i] = (File)enc.elementAt(i);
        }
        return encontrados;
    }

    public String readContentFile(File file) {
        Path path = Paths.get(file.toURI());
        try {
            String longText = formmaterString(Files.readAllLines(path).toString());
            return longText;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public List<String> readContentFileAsList(File file) {
        Path path = Paths.get(file.toURI());
        try {
            List<String> readFiles = new ArrayList<>();

            for (String fr : Files.readAllLines(path)){
                readFiles.add(formmaterString(fr));
            }
            return readFiles;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private String formmaterString(String s){
        return s.replace(",", "")
                .replace("[", "")
                .replace("]", "")
                .replace("\"", "")
                .replace("  ", " ")
                .replace("   ", " ")
                .replace("    ", " ")
                .replace("//", "")
                .replace("{", "")
                .replace("}", "")
                .replace("#", "");
    }

}
