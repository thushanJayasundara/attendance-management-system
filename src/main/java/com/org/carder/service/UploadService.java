package com.org.carder.service;

import com.org.carder.constant.CommonMsg;
import com.org.carder.utill.CommonResponse;
import com.org.carder.utill.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;


@Service
public class UploadService {

    private final Logger logger = LoggerFactory.getLogger(UploadService.class);

    @Value("${fileUploadPath}")
    private String UPLOADED_FOLDER;

    @Value("${fileName}")
    private String FILE_NAME;

    private final AttendanceService attendanceService;

    public UploadService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     *
     * @param file
     * @return
     */
    @Transactional
    public CommonResponse uploadFile(MultipartFile file){
        CommonResponse commonResponse = new CommonResponse();
        try {
            String documentName = DateTimeUtil.getLocalDate().trim()+FILE_NAME;
            String filePath = UPLOADED_FOLDER.concat(File.separator).concat(documentName);
            Path path = Paths.get(filePath);
            if(!Files.exists(path)){
                file.transferTo(new File(filePath));
                attendanceService.fetchAttendance(documentName);
                commonResponse.setStatus(true);
            }else {
            commonResponse.setErrorMessages(Collections.singletonList(CommonMsg.FILE_ALREADY_EXIST));
            }
        }catch (IOException e){logger.warn("/******** IOException in UploadService -> uploadFile() :" + e);}
        return commonResponse;
    }

}
