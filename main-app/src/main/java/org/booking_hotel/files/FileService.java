package org.booking_hotel.files;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.booking_hotel.daos.files.FileDao;
import org.booking_hotel.daos.files.dto.FileDto;
import org.booking_hotel.files.model.FileSaveRequest;
import org.booking_hotel.files.model.FileSaveResponse;
import org.booking_hotel.jooq.model.tables.records.FileRecord;

import java.util.List;
import java.util.function.Consumer;

@RequestScoped
@Transactional
public class FileService {
    @Inject
    FileDao fileDao;

    public List<FileDto> getAllFiles() {
        return fileDao.getAll();
    }

    public FileDto getFileById(Long id) {
        return fileDao.getById(id);
    }

    public FileSaveResponse saveFile(FileSaveRequest req) {
        Consumer<FileRecord> fn = record -> {
            record.setName(req.name());
            record.setUrlPath(req.urlPath());
            record.setType(req.type());
            record.setSize(req.size());
        };

        FileDto createdFile = req.id() == null ? fileDao.insert(fn) : fileDao.updateById(fn, req.id());

        return new FileSaveResponse(
                createdFile.id(),
                createdFile.name(),
                createdFile.urlPath(),
                createdFile.type(),
                createdFile.size()
        );
    }

    public void deleteFileById(Long id) {
        fileDao.removeById(id);
    }
}