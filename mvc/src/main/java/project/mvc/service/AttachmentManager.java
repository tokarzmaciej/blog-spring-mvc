package project.mvc.service;

import project.mvc.domain.Attachment;

import java.util.List;

public interface AttachmentManager {
    Attachment addAttachment(Attachment attachment);

    List<Attachment> getAllAttachments();

    void deleteAttachmentByIdPost(String idPost);


}

