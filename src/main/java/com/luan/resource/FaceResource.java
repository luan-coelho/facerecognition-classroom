package com.luan.resource;

import com.luan.commons.pagination.MultipartData;
import com.luan.dto.FaceRecognitionResponseDTO;
import com.luan.service.PersonService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestQuery;

@Produces(MediaType.APPLICATION_JSON)
@Path("/face")
public class FaceResource {

    @Inject
    PersonService personService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response toRecognize(@RestQuery("person") Long personId, MultipartData multipartBody) {
        FaceRecognitionResponseDTO responseDTO = personService.faceDetected(personId, multipartBody.inputStream);
        return Response.ok(responseDTO).build();
    }
}
