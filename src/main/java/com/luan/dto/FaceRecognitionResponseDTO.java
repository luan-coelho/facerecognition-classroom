package com.luan.dto;

public record FaceRecognitionResponseDTO(boolean faceDetected, String person, String detail) {
}
