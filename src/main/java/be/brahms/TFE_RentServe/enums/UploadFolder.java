package be.brahms.TFE_RentServe.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Folders used to store uploaded files. */
@Getter
@RequiredArgsConstructor
public enum UploadFolder {
  /** Folder for user materials. */
  USER_MATERIAL("userMaterials"),
  /** Folder for user favours. */
  USER_FAVOR("userFavour"),
  /** Folder for user photos. */
  USER_PHOTO("userPhotos");

  private final String folderName;
}
