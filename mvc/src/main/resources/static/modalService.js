function openModal() {
    const modal = document.querySelector('.modal');
    const html = document.querySelector('html');
    modal.classList.add("is-active");
    html.classList.add('is-clipped');
}

function closeModal() {
    const modal = document.querySelector('.modal');
    const html = document.querySelector('html');
    modal.classList.remove("is-active");
    html.classList.remove('is-clipped');
}