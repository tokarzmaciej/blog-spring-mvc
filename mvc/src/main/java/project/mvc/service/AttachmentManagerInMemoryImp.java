package project.mvc.service;

import com.opencsv.CSVWriter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Attachment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
class AttachmentManagerInMemoryImpl implements AttachmentManager {

    @Setter
    private List<Attachment> db;
    String urlForImage = "https://blog-with-spring-boot.herokuapp.com/files/image/";

    AttachmentManagerInMemoryImpl(@Autowired List<Attachment> db) {
        this.db = db;
    }

    @Override
    public void addAttachment(Attachment attachment) {
        Attachment attachmentToAdd = new Attachment(attachment.getId_post(), attachment.getFilename());
        db.add(attachmentToAdd);
    }

    @Override
    public List<Attachment> getAllAttachments() {
        return db;
    }

    public void deleteAttachmentByIdPost(String idPost) {
        setDb(db.stream().filter(attachment -> !Objects.equals(attachment.getId_post(), idPost))
                .collect(Collectors.toList()));
    }

    @Override
    public List<Attachment> getAllAttachmentsForPost(String idPost) {
        return db.stream()
                .filter(attachment -> Objects.equals(attachment.getId_post(), idPost)
                        && !attachment.getFilename().startsWith(urlForImage))
                .collect(Collectors.toList());
    }

    @Override
    public List<Attachment> getAllImagesForPost(String idPost) {
        return db.stream()
                .filter(attachment -> Objects.equals(attachment.getId_post(), idPost)
                        && attachment.getFilename().startsWith(urlForImage))
                .collect(Collectors.toList());
    }

    @Override
    public void writeToCsv() {
        File folder = new File("./upload-dir/export");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = "./upload-dir/export/attachments.csv";
        File file = new File(filePath);
        try {
            FileWriter outputFile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputFile);

            String[] header = {"id_post", "filename"};
            writer.writeNext(header);

            db.forEach(attachment -> {
                String[] data = {attachment.getId_post(), attachment.getFilename()};
                writer.writeNext(data);
            });

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void csvToBeans(String content) {
        List<Attachment> importedData = new ArrayList<>();

        Arrays.stream(content.split("\n")).skip(1)
                .forEach(row -> {
                    List<String> line = Arrays.stream(row.split(",")).collect(Collectors.toList());
                    Attachment attachment = new Attachment(line.get(0).substring(1, line.get(0).length() - 1),
                            line.get(1).substring(1, line.get(1).length() - 1));
                    importedData.add(attachment);
                });
        setDb(importedData);
    }

}
