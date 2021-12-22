package project.mvc.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init();

    void storeImage(MultipartFile file);

    void storeAttachment(MultipartFile file);

    Path loadImage(String filename);

    Path loadAttachment(String filename);

    Resource loadAsResourceImage(String filename);

    Resource loadAsResourceAttachment(String filename);

    Path loadExportFile(String filename);

    Resource loadAsResourceExport(String filename);

    void deleteAll();

}
