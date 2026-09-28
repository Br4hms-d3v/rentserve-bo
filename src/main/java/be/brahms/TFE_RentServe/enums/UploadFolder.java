package be.brahms.TFE_RentServe.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UploadFolder {
  USER_MATERIAL("userMaterials"),
  USER_FAVOR("userFavors"),
  USER_PHOTO("userPhotos");

  private final String folderName;
}
