package com.luan.service;

import com.luan.model.Person;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class FaceRecognitionProcessing {

    private Path dirImageOutputPath;

    public boolean faceDetected(Person person, InputStream image) {
        String output;
        try {
            String imageOutputPath = saveTemporaryImageAndReturnPath(person.getName(), image);
            output = requestCall(person, imageOutputPath);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                Files.walk(dirImageOutputPath)
                        .sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException ignored) {
                            }
                        });
            } catch (IOException ignored) {
            }
        }

        return !output.contains("unknown_person");
    }

    private String requestCall(Person person, String imageOutputPath) throws IOException, InterruptedException {
        StringBuilder output;
        String command = String.format("face_recognition %s %s", imageOutputPath, person.getResourcePath());

        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        output = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        return output.toString();
    }

    public String saveTemporaryImageAndReturnPath(String person, InputStream image) throws IOException {
        String imageOutputPath = "/tmp/" + person.toLowerCase();
        this.dirImageOutputPath = Paths.get(imageOutputPath);
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
