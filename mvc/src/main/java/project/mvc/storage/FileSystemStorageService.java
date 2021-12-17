package project.mvc.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocationImage;
    private final Path rootLocationAttachment;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocationImage = Paths.get(properties.getLocation() + "/image");
        this.rootLocationAttachment = Paths.get(properties.getLocation() + "/attachment");

    }


    public void storeImage(MultipartFile file) {
        try {

            Path destinationFile = this.rootLocationImage.resolve(
                            Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocationImage.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }


    public void storeAttachment(MultipartFile file) {
        try {

            Path destinationFile = this.rootLocationAttachment.resolve(
                            Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocationAttachment.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }


    @Override
    public Path loadImage(String filename) {
        return rootLocationImage.resolve(filename);
    }

    @Override
    public Path loadAttachment(String filename) {
        return rootLocationAttachment.resolve(filename);
    }

    @Override
    public Resource loadAsResourceImage(String filename) {
        try {
            Path file = loadImage(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public Resource loadAsResourceAttachment(String filename) {
        try {
            Path file = loadAttachment(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {

        FileSystemUtils.deleteRecursively(rootLocationImage.toFile());
        FileSystemUtils.deleteRecursively(rootLocationAttachment.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocationImage);
            Files.createDirectories(rootLocationAttachment);

        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
