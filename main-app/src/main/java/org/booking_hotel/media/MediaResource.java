package org.booking_hotel.media;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.daos.media.dto.MediaDto;
import org.booking_hotel.media.model.MediaModel;
import org.booking_hotel.media.model.MediaSaveRequest;
import org.booking_hotel.media.model.MediaSaveResponse;
import org.booking_hotel.utils.BusinessException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/core/media")
@Tag(name = "Media", description = "Operations related to Media items")
public class MediaResource {
    @Inject
    MediaService mediaService;

    @GET
    @Path("/{id}")
    public MediaModel getMediaById(@PathParam("id") Long id) {
        MediaDto media = mediaService.getMediaById(id);
        return MediaModel.of(media);
    }


    @POST
    public MediaSaveResponse saveMedia(@Valid MediaSaveRequest req) throws BusinessException {
        return mediaService.saveMedia(req);
    }

    @GET
    public List<MediaModel> getAllMedia() {
        return mediaService.getAllMedia().stream().map(MediaModel::of).toList();
    }

    @GET
    @Path("/ref/{refId}/{refType}")
    public List<MediaModel> getMediaByRefIdAndRefType(
            @PathParam("refId") Long refId,
            @PathParam("refType") String refType) {
        return mediaService.getMediaByRefIdAndRefType(refId, refType)
                .stream().map(MediaModel::of).toList();
    }


    @DELETE
    @Path("/{id}")
    public void deleteMediaById(@PathParam("id") Long id) {
        mediaService.deleteMediaById(id);
    }
}