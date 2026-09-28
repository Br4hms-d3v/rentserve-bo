package be.brahms.TFE_RentServe.services;

import be.brahms.TFE_RentServe.enums.UploadFolder;
import org.springframework.web.multipart.MultipartFile;

/** Service to save files in storage. */
public interface FileStorageService {

  /**
   * Saves a picture in the correct folder.
   *
   * @param file the picture to save
   * @param folder the folder where the picture is saved
   * @return the file name saved in storage (for example, "marteau.png")
   */
  String store(MultipartFile file, UploadFolder folder);
}
