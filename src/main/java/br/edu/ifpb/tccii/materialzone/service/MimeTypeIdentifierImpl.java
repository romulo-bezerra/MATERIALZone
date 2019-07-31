package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.MimeTypeIdentifier;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class MimeTypeIdentifierImpl implements MimeTypeIdentifier {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public String detectMimeType(final File file){
        TikaConfig tika;
        String mimetype = null;
        try {
            tika = new TikaConfig();
            mimetype = String.valueOf(tika.getDetector()
                    .detect(TikaInputStream.get(file.toURI()), new Metadata()));
        } catch (TikaException e) {
            LOG.error(e.getMessage());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return mimetype;
    }

}
