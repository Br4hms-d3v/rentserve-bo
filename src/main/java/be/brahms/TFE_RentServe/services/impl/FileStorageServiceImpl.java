package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.enums.UploadFolder;
import be.brahms.TFE_RentServe.exceptions.picture.PictureException;
import be.brahms.TFE_RentServe.services.FileStorageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/** Service implementation to save pictures in storage. */
@Service
public class FileStorageServiceImpl implements FileStorageService {

  /** Main folder where files are stored. */
  private final Path rootDirectory;

  /**
   * Creates the file storage service.
   *
   * @param dir the main folder for uploaded files
   * @throws IllegalStateException if the folder does not exist
   */
  public FileStorageServiceImpl(@Value("${app.upload-dir}") String dir) {
    this.rootDirectory = Paths.get(dir).toAbsolutePath().normalize();

    if (!Files.isDirectory(this.rootDirectory)) {
      throw new IllegalStateException("Dossier introuvable");
    }
  }

  /**
   * Saves a picture in the correct folder.
   *
   * @param file the picture to save
   * @param folder the folder where the picture is saved
   * @return the file name saved in storage
   * @throws PictureException if the file is not an image or cannot be saved
   * @throws SecurityException if the file path is not safe
   */
  @Override
  public String store(MultipartFile file, UploadFolder folder) {
    String contentType = file.getContentType();
    if (contentType == null || !contentType.startsWith("image/")) {
      throw new PictureException("Seules les images sont acceptées");
    }

    String originalName = file.getOriginalFilename();
    if (originalName == null || originalName.isBlank()) {
      throw new PictureException("Nom de fichier invalide");
    }

    String safeName = StringUtils.cleanPath(originalName).replaceAll("[^a-zA-Z0-9._-]", "_");

    Path targetDirectory = rootDirectory.resolve(folder.getFolderName()).normalize();
    Path target = targetDirectory.resolve(safeName).normalize();

    if (!targetDirectory.startsWith(rootDirectory) || !target.startsWith(targetDirectory)) {
      throw new SecurityException("Le chemin n'existe pas");
    }

    try {
      Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new PictureException("Impossible d'enregistre la photo");
    }
    return safeName;
  }
}
