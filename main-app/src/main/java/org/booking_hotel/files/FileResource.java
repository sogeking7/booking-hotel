package org.booking_hotel.files;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.daos.files.dto.FileDto;
import org.booking_hotel.files.model.FileModel;
import org.booking_hotel.files.model.FileSaveRequest;
import org.booking_hotel.files.model.FileSaveResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/core/files")
@Tag(name = "File", description = "Operations related to File items")
public class FileResource {
    @Inject
    FileService fileService;

    @GET
    @Path("/{id}")
    public FileModel getFileById(@PathParam("id") Long id) {
        FileDto file = fileService.getFileById(id);
        return FileModel.of(file);
    }

    @POST
    public FileSaveResponse saveFile(@Valid FileSaveRequest req) {
        return fileService.saveFile(req);
    }

    @GET
    public List<FileModel> getAllFiles() {
        return fileService.getAllFiles().stream().map(FileModel::of).toList();
    }

    @DELETE
    @Path("/{id}")
    public void deleteFileById(@PathParam("id") Long id) {
        fileService.deleteFileById(id);
    }
}