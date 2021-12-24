const imageInput = document.querySelector('#imageInput input[type=file]');
imageInput.onchange = () => {
    if (imageInput.files.length > 0) {
        const fileName = document.querySelector('#imageInput .file-name');
        fileName.textContent = imageInput.files[0].name;
    }
}

const attachmentInput = document.querySelector('#attachmentInput input[type=file]');
attachmentInput.onchange = () => {
    if (attachmentInput.files.length > 0) {
        const fileName = document.querySelector('#attachmentInput .file-name');
        fileName.textContent = attachmentInput.files[0].name;
    }
}