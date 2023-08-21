package com.luan.service;

import com.luan.repository.PersonRepository;
import com.luan.dto.FaceRecognitionResponseDTO;
import com.luan.model.Person;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.InputStream;

@ApplicationScoped
public class PersonService extends BaseService<Person, PersonRepository> {

    public FaceRecognitionResponseDTO faceDetected(Long personId, InputStream image) {
        Person person = findById(personId);
        FaceRecognitionProcessing processing = new FaceRecognitionProcessing();
        boolean faceDetected = processing.faceDetected(person, image);
        String detail = faceDetected ? "Face detectada" : "Face não detectada";
        return new FaceRecognitionResponseDTO(faceDetected, person.getName(), detail);
    }
}
