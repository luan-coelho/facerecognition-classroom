const video = document.getElementById('video');
const canvas = document.getElementById('canvas');
const snap = document.getElementById('snap');
const button = document.getElementById('button-enviar');
const fileInput = document.getElementById('file');
const context = canvas.getContext('2d');

navigator.mediaDevices.getUserMedia({video: true})
    .then(stream => video.srcObject = stream)
    .catch(err => console.error("Erro ao acessar a webcam:", err));

snap.addEventListener('click', function () {
    context.drawImage(video, 0, 0, 640, 480);
    canvas.toBlob(blob => {
        const file = new File([blob], "image.jpg", {type: 'image/jpeg', lastModified: new Date()});
        const formData = new FormData();
        formData.append('file', file);

        // Criar um elemento de imagem e definir o src como a URL do blob
        const img = new Image();
        img.src = URL.createObjectURL(blob);
        img.width = 640;
        img.height = 480;

        // Substituir o elemento de vídeo pelo elemento de imagem
        video.parentNode.replaceChild(img, video);
    });
})


button.addEventListener('click', function (e) {
    e.preventDefault()
    context.drawImage(video, 0, 0, 640, 480);
    canvas.toBlob(blob => {
        const file = new File([blob], "image.jpg", {type: 'image/jpeg', lastModified: new Date()});
        const formData = new FormData();
        formData.append('file', file);

        // Faça uma chamada fetch com os dados do formulário
        fetch('/api/face?person=1', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => console.log(data))
            .catch(error => console.error('Erro ao enviar a foto:', error));
    });
});