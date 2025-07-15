package org.booking_hotel.files; // Убедитесь, что пакет правильный

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.booking_hotel.daos.files.dto.FileDto;
import org.booking_hotel.files.FileService;
import org.booking_hotel.files.FileUploadForm;
import org.booking_hotel.files.model.FileModel;
import org.booking_hotel.files.model.FileSaveRequest;
import org.booking_hotel.files.model.FileSaveResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/core/files")
@Tag(name = "File", description = "Все операции, связанные с файлами")
public class FileResource {

    private static final String UPLOAD_DIR = "C:/booking-hotel-uploads/";

    @Inject
    FileService fileService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get file metadata by ID")
    public FileModel getFileById(@PathParam("id") Long id) {
        FileDto file = fileService.getFileById(id);
        return FileModel.of(file);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get metadata for all files")
    public List<FileModel> getAllFiles() {
        return fileService.getAllFiles().stream().map(FileModel::of).collect(Collectors.toList());
    }

    @Authenticated
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a file by ID")
    public void deleteFileById(@PathParam("id") Long id) {
        fileService.deleteFileById(id);
    }

    @Authenticated
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Upload a file")
    @RequestBody(
            description = "Multipart form for file upload",
            required = true,
            content = @Content(
                    mediaType = MediaType.MULTIPART_FORM_DATA,
                    schema = @Schema(implementation = FileUploadForm.class)
            )
    )
    public FileSaveResponse uploadFile(@MultipartForm FileUploadForm form) {
        File uploadsFolder = new File(UPLOAD_DIR);

        if (!uploadsFolder.exists()) {
            uploadsFolder.mkdirs();
        }

        String fileName = form.fileName;
        File targetFile = new File(uploadsFolder, fileName);

        try (var input = form.file; var output = new FileOutputStream(targetFile)) {
            input.transferTo(output);
        } catch (IOException e) {
            throw new WebApplicationException("File upload failed: " + e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }

        return fileService.saveFile(new FileSaveRequest(
                null,
                fileName,
                fileName, // В базу данных сохраняем только имя файла
                getExtension(fileName),
                targetFile.length()
        ));
    }

    @GET
    @Path("/download/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Operation(summary = "Download a file by its name")
    public Response downloadFile(@PathParam("filename") String filename) {
        File file = new File(UPLOAD_DIR, filename);

        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // ИЗМЕНЕНО: Заменяем '*' на точный URL вашего фронтенда
        return Response.ok(file)
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Credentials", "true") // Дополнительный заголовок для надежности
                .header("Content-Disposition", "inline; filename=\"" + file.getName() + "\"")
                .build();
    }

    private String getExtension(String fileName) {
        if (fileName == null) return "";
        int i = fileName.lastIndexOf('.');
        return (i > 0) ? fileName.substring(i + 1) : "";
    }
}
