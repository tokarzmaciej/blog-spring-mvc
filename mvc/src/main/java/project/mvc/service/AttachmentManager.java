package project.mvc.service;

import project.mvc.domain.Attachment;

import java.util.List;

public interface AttachmentManager {
    void addAttachment(Attachment attachment);

    List<Attachment> getAllAttachments();

    void deleteAttachmentByIdPost(String idPost);

    void setDb(List<Attachment> db);

    List<Attachment> getAllAttachmentsForPost(String idPost);

    List<Attachment> getAllImagesForPost(String idPost);

    void writeToCsv();

}

