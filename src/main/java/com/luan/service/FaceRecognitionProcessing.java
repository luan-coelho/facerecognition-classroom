package com.luan.service;

import com.luan.dto.FaceRecognitionResponseDTO;
import com.luan.model.Person;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class FaceRecognitionService {

    Person person;
    Path tempFile;
    StringBuilder output;

    public FaceRecognitionResponseDTO faceDetected(Long personId, InputStream image) {
        Person person = findById(personId);

        Path tempFile = null;
        StringBuilder output;

        try {
            String imageOutputPath = saveTemporaryImageAndReturnPath(person.getName(), image);
            String command = String.format("face_recognition %s %s", imageOutputPath, person.getResourcePath());

            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                System.out.println(line);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                Files.walk(tempFile)
                        .sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        boolean faceDetected = !output.toString().contains("unknown_person");
        String details = "Face detectada com sucesso";
        return new FaceRecognitionResponseDTO(faceDetected, details);
    }

    public String saveTemporaryImageAndReturnPath(String person, InputStream image) throws IOException {
        String imageOutputPath = "/tmp/" + person.toLowerCase();
        Path dirImageOutputPath = Paths.get(imageOutputPath);
        if (!Files.exists(dirImageOutputPath)) {
            Files.createDirectories(dirImageOutputPath);
        }

        Path tempFile = Files.createTempFile(dirImageOutputPath, "image_upload", ".jpg");

        try (OutputStream outStream = Files.newOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = image.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
        return imageOutputPath;
    }
}
