package br.edu.ifpb.tccii.materialzone.abstration;

import java.io.File;

public interface FileManager {

    File[] getAllFilesDirectory(File rootDirectory);
    String readContentFile(File file);
    void deleteDirectories(File rootDirectory);

}
