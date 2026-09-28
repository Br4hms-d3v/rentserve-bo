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

@Service
public class FileStorageServiceImpl implements FileStorageService {

  private final Path rootDirectory;

  public FileStorageServiceImpl(@Value("${app.upload-dir}") String dir) {
    this.rootDirectory = Paths.get(dir).toAbsolutePath().normalize();

    if (!Files.isDirectory(this.rootDirectory)) {
      throw new IllegalStateException("Dossier introuvable");
    }
  }

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
