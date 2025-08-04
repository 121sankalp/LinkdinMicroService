package com.example.uploader_service.controller;

import com.example.uploader_service.service.UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class UploaderController {

    private  final UploaderService uploaderService ;

//    @PostMapping
//    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file)
//    {
//        String url = uploaderService.upload(file) ;
//
//        return  ResponseEntity.ok(url) ;
//    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
        String url = uploaderService.upload(file);
        return ResponseEntity.ok(url);
    }

}
