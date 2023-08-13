package com.luan.resource;

import com.luan.commons.pagination.MultipartData;
import com.luan.dto.FaceRecognitionResponseDTO;
import com.luan.service.PersonService;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestQuery;

@Blocking
@Path("/face")
@Produces(MediaType.TEXT_HTML)
public class FaceRecognitionResource {

    @Inject
    PersonService personService;

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance index();
    }

    @GET
    @Path("/")
    public TemplateInstance index() {
        return Templates.index();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response toRecognize(@RestQuery("person") Long personId, MultipartData multipartBody) {
        FaceRecognitionResponseDTO responseDTO = personService.faceDetected(personId, multipartBody.inputStream);
        return Response.ok(responseDTO).build();
    }
}
