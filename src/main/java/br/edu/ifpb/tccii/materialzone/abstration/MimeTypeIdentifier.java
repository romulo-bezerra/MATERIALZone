package br.edu.ifpb.tccii.materialzone.abstration;

import java.io.File;

public interface MimeTypeIdentifier {
    String detectMimeType(final File file);
}
