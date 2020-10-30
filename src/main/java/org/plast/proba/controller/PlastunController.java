package org.plast.proba.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.plast.proba.domain.model.PhotoType;
import org.plast.proba.domain.model.UploadFileResponse;
import org.plast.proba.domain.pojo.Picture;
import org.plast.proba.domain.pojo.User;
import org.plast.proba.service.PictureStorageService;
import org.plast.proba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/plastun")
public class PlastunController {

    @Autowired
    private PictureStorageService pictureStorageService;

    @Autowired
    private UserService userService;

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Access Token",
                    required = true,
                    allowEmptyValue = false,
                    paramType = "header",
                    dataTypeClass = String.class,
                    example = "Bearer access_token"))
    @PostMapping("/upload-photo")
    public UploadFileResponse uploadPhoto(
            @RequestParam
            @ApiParam(value = "photoType", example = "AVATAR or BACKGROUND")
                    PhotoType photoType,
            @RequestParam("file")
                    MultipartFile file) {
        Picture dbFile = pictureStorageService.storeFile(file);

        User user = userService.getUser();
        switch (photoType) {
            case AVATAR:
                user.setMainPhoto(dbFile);
                break;
            case BACKGROUND:
                user.setBackgroundPhoto(dbFile);
                break;
        }
        userService.save(user);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId().toString())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }
}
