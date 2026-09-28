package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.enums.UploadFolder;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    /**
     * Save a picture inside the wright folder
     *
     * @return the name of file storage (ex: "marteau.png"), has stocked in DB
     */
    String store(MultipartFile file, UploadFolder folder);
}
