package project.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.mvc.domain.Attachment;

import java.util.List;


@Service
class AttachmentManagerInMemoryImpl implements AttachmentManager {


    @Autowired
    List<Attachment> db;

    @Override
    public Attachment addAttachment(Attachment attachment) {
        Attachment attachmentToAdd = new Attachment(attachment.getId_post(), attachment.getFilename());
        db.add(attachmentToAdd);
        return attachmentToAdd;
    }

    @Override
    public List<Attachment> getAllAttachments() {
        return db;
    }

}
