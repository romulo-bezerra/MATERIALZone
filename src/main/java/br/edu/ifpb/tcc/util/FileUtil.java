package br.edu.ifpb.tcc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    public List<String> readContentFileAsList(File file){
        List<String> retorno = new ArrayList<>();
        String nameFile = file.getName();

        if(!nameFile.endsWith("jar") && !nameFile.endsWith("png")
                && !nameFile.endsWith("jpeg") && !nameFile.endsWith("gz")
                && !nameFile.endsWith("gif") && !nameFile.endsWith("tiff")
                && !nameFile.endsWith("bmp") && !nameFile.endsWith("psd")
                && !nameFile.endsWith("exif") && !nameFile.endsWith("zip")
                && !nameFile.endsWith("rar") && !nameFile.endsWith("tar")
                && !nameFile.endsWith("z") && !nameFile.endsWith("taz")
                && !nameFile.endsWith("tgz") && !nameFile.endsWith("arj")){

            BufferedReader conteudoFile = null;
            String linha = "";

            try {
                conteudoFile = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
                while ((linha = conteudoFile.readLine()) != null) {
                    retorno.add(formmaterString(linha));
                }
                return retorno;

            } catch (FileNotFoundException e) {
                log.error("Arquivo não encontrado: \n" + e.getMessage());
                return retorno;
            } catch (IOException e) {
                log.error("IO erro: \n" + e.getMessage());
                return retorno;
            } finally {
                if (conteudoFile != null){
                    try {
                        conteudoFile.close();
                    } catch (IOException e) {
                        log.error("IO erro: \n" + e.getMessage());
                    }
                }
            }
        }
        return retorno;
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
