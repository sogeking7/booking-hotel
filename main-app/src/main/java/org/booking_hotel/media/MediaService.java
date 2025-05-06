package org.booking_hotel.media;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.booking_hotel.files.FileDao;
import org.booking_hotel.media.dto.MediaDto;
import org.booking_hotel.media.model.MediaSaveRequest;
import org.booking_hotel.media.model.MediaSaveResponse;
import org.booking_hotel.jooq.model.tables.records.MediaRecord;
import org.booking_hotel.utils.BusinessException;

import java.util.List;
import java.util.function.Consumer;

@RequestScoped
@Transactional
public class MediaService {
    @Inject
    MediaDao mediaDao;
    
    @Inject
    FileDao fileDao;

    public List<MediaDto> getAllMedia() {
        return mediaDao.getAll();
    }

    public MediaDto getMediaById(Long id) {
        return mediaDao.getById(id);
    }
    
    public List<MediaDto> getMediaByRefIdAndRefType(Long refId, String refType) {
        return mediaDao.getByRefIdAndRefType(refId, refType);
    }

    public MediaSaveResponse saveMedia(MediaSaveRequest req) throws BusinessException {
        // Verify that the file exists
        try {
            fileDao.getById(req.fileId());
        } catch (Exception e) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "media.save.fileIsNotExists",
                    "File with id " + req.fileId() + " does not exist"
            );
        }

        Consumer<MediaRecord> fn = record -> {
            record.setRefId(req.refId());
            record.setRefType(req.refType());
            record.setFileId(req.fileId());
        };

        MediaDto createdMedia = req.id() == null ? mediaDao.insert(fn) : mediaDao.updateById(fn, req.id());

        return new MediaSaveResponse(
                createdMedia.id(),
                createdMedia.refId(),
                createdMedia.refType(),
                createdMedia.fileId()
        );
    }

    public void deleteMediaById(Long id) {
        mediaDao.deleteById(id);
    }
}