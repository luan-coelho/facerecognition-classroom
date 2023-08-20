const video = document.getElementById('video');
const canvas = document.getElementById('canvas');
const snap = document.getElementById('snap');
const button = document.getElementById('button-enviar');
const context = canvas.getContext('2d');

navigator.mediaDevices.getUserMedia({video: true})
    .then(stream => video.srcObject = stream)
    .catch(() => {
        const message = document.querySelector("#message")
        message.innerHTML = "Não foi possível acessar a Webcam"
        message.parentElement.classList.remove("d-none")
    });

video.addEventListener('play', function () {
    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;
});

snap.addEventListener('click', function () {
    context.drawImage(video, 0, 0, canvas.width, canvas.height);
    canvas.toBlob(blob => {
        const file = new File([blob], "image.jpg", {type: 'image/jpeg', lastModified: new Date()});
        const formData = new FormData();
        formData.append('file', file);

        const img = new Image();
        img.src = URL.createObjectURL(blob);
        img.width = canvas.width;
        img.height = canvas.height;

        video.parentNode.replaceChild(img, video);
    });
});

button.addEventListener('click', function (e) {
    e.preventDefault();
    context.drawImage(video, 0, 0, canvas.width, canvas.height);
    canvas.toBlob(blob => {
        const file = new File([blob], "image.jpg", {type: 'image/jpeg', lastModified: new Date()});
        const formData = new FormData();
        formData.append('file', file);

        fetch('/face?person=1', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => console.log(data))
            .catch(error => console.error('Erro ao enviar a foto:', error));
    });
});
