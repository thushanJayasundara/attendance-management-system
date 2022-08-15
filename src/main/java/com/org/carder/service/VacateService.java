package com.org.carder.service;



import com.org.carder.DTOs.VacateListDTO;
import com.org.carder.constant.CommonMsg;
import com.org.carder.constant.CommonStatus;
import com.org.carder.entity.Attendance;
import com.org.carder.entity.VacateList;
import com.org.carder.repository.VacateListRepository;
import com.org.carder.utill.CommonResponse;
import com.org.carder.utill.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VacateService {

    private final Logger logger = LoggerFactory.getLogger(LeaveService.class);

    @Value("${fileUploadPath}")
    private String UPLOADED_FOLDER;

    @Value("${fileName}")
    private String FILE_NAME;

    private final AttendanceService attendanceService;
    private final UserService userService;
    private final VacateListRepository vacateListRepository;

    public VacateService(AttendanceService attendanceService, UserService userService, VacateListRepository vacateListRepository) {
        this.attendanceService = attendanceService;
        this.userService = userService;
        this.vacateListRepository = vacateListRepository;
    }

    /**
     * add employee too vacate list
     * @return
     */
    @Transactional
    public CommonResponse generateVacateList(){
        CommonResponse commonResponse = new CommonResponse();
        try {
            String documentName = DateTimeUtil.getLocalDate().trim()+FILE_NAME;
            String filePath = UPLOADED_FOLDER.concat(File.separator).concat(documentName);
            Path path = Paths.get(filePath);
            if(Files.exists(path)){
                Set<Attendance> attendanceList = attendanceService.getFourDaysAgoAbsentList();
                if (attendanceList.size() == 4) {
                    attendanceList.forEach(attendance -> {
                        if (!vacateListRepository.existsByVacateUser(attendance.getEmployee())) {
                            VacateList vacateList = getVacateEntity(attendance.getEmployee().getEmpNumber());
                            vacateListRepository.save(vacateList);
                        }
                    });
                }
                commonResponse.setStatus(true);
            }else {
                commonResponse.setErrorMessages(Collections.singletonList(CommonMsg.UPLOAD_TODAY_ATTENDANCE_FILE));
            }
        }catch (Exception e){logger.warn("/******** Exception in VacateService -> generateVacateList() :" + e); }
        return commonResponse;
    }

    /**
     * get generate vacate list
     * @return
     */
    public CommonResponse getVacateList(){
        CommonResponse commonResponse = new CommonResponse();
         try {
                List<VacateListDTO> vacateListDTOS = vacateListRepository.findAll()
                                                                            .stream()
                                                                                .map(this::getVacateListDTO)
                                                                                    .collect(Collectors.toList());
                commonResponse.setPayload(Collections.singletonList(vacateListDTOS));
                commonResponse.setStatus(true);
         }catch (Exception e){logger.warn("/******** Exception in VacateService -> getVacateList() :" + e); }
         return commonResponse;
    }

    /**
     * get count vacate emp
     * @return
     */
    public long getVacateEmp(){return vacateListRepository.count();}

    /**
     * get vacate list DTO
     * @param vacateList
     * @return
     */
    public VacateListDTO getVacateListDTO(VacateList vacateList){
        return new VacateListDTO(
                vacateList.getId().toString(),
                userService.getUserDTO(vacateList.getVacateUser()),
                vacateList.getCommonStatus()
        );
    }

    /**
     * using employee number and vacate entity
     * @param empNumber
     * @return
     */
    @Transactional
    public VacateList getVacateEntity(Long empNumber){
        return new VacateList(
                null,
                userService.getUserByEmpNum(empNumber),
                CommonStatus.INACTIVE
        );
    }
}
