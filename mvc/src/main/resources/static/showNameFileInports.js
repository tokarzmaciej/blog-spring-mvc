const posts = document.querySelector('#postsInput input[type=file]');
posts.onchange = () => {
    if (posts.files.length > 0) {
        const fileName = document.querySelector('#postsInput .file-name');
        fileName.textContent = posts.files[0].name;
    }
}
const authors = document.querySelector('#authorsInput input[type=file]');
authors.onchange = () => {
    if (authors.files.length > 0) {
        const fileName = document.querySelector('#authorsInput .file-name');
        fileName.textContent = authors.files[0].name;
    }
}
const comments = document.querySelector('#commentsInput input[type=file]');
comments.onchange = () => {
    if (comments.files.length > 0) {
        const fileName = document.querySelector('#commentsInput .file-name');
        fileName.textContent = comments.files[0].name;
    }
}
const attachments = document.querySelector('#attachmentsInput input[type=file]');
attachments.onchange = () => {
    if (attachments.files.length > 0) {
        const fileName = document.querySelector('#attachmentsInput .file-name');
        fileName.textContent = attachments.files[0].name;
    }
}
const postAndAuthor = document.querySelector('#postAndAuthorInput input[type=file]');
postAndAuthor.onchange = () => {
    if (postAndAuthor.files.length > 0) {
        const fileName = document.querySelector('#postAndAuthorInput .file-name');
        fileName.textContent = postAndAuthor.files[0].name;
    }
}
