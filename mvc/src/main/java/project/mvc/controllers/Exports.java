package project.mvc.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.mvc.service.*;
import project.mvc.storage.StorageService;


@Slf4j
@Controller
public class Exports {

    private final StorageService storageService;
    private final PostManager postManager;
    private final AuthorManager authorManager;
    private final CommentManager commentManager;
    private final AttachmentManager attachmentManager;
    private final PostAndAuthorManager postAndAuthorManager;

    public Exports(StorageService storageService, PostManager postManager, AuthorManager authorManager,
                   CommentManager commentManager, AttachmentManager attachmentManager,
                   PostAndAuthorManager postAndAuthorManager) {
        this.storageService = storageService;
        this.postManager = postManager;
        this.authorManager = authorManager;
        this.commentManager = commentManager;
        this.attachmentManager = attachmentManager;
        this.postAndAuthorManager = postAndAuthorManager;
    }


    @GetMapping("/exports")
    public String exports() {
        postManager.writeToCsv();
        authorManager.writeToCsv();
        commentManager.writeToCsv();
        attachmentManager.writeToCsv();
        postAndAuthorManager.writeToCsv();
        return "exports";
    }

    @GetMapping("/files/export/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFileExport(@PathVariable String filename) {

        Resource file = storageService.loadAsResourceExport(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


}
