package project.mvc.service;

import lombok.Setter;
import org.springframework.stereotype.Service;
import project.mvc.domain.Attachment;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
class AttachmentManagerInMemoryImpl implements AttachmentManager {

    @Setter
    private List<Attachment> db;

    AttachmentManagerInMemoryImpl(List<Attachment> db) {
        this.db = db;
    }

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

    public void deleteAttachmentByIdPost(String idPost) {
        setDb(db.stream().filter(attachment -> !Objects.equals(attachment.getId_post(), idPost))
                .collect(Collectors.toList()));
    }


}
