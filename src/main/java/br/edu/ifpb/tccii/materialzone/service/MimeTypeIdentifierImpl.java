package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.MimeTypeIdentifier;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class MimeTypeIdentifierImpl implements MimeTypeIdentifier {

    private final Logger log;
    private final Detector DETECTOR;

    public MimeTypeIdentifierImpl() {
        DETECTOR = new DefaultDetector(MimeTypes.getDefaultMimeTypes());
        log = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public String detectMimeType(final File file) {
        TikaInputStream tikaIS = null;
        String retorno = "";
        try {
            tikaIS = TikaInputStream.get(file.toPath());
            final Metadata metadata = new Metadata();
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
            retorno = DETECTOR.detect(tikaIS, metadata).toString();
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (tikaIS != null) {
                try {
                    tikaIS.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return retorno;
    }
}
