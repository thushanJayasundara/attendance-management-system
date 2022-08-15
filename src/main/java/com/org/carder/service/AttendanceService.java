package com.org.carder.service;

import com.org.carder.DTOs.AttendanceDTO;
import com.org.carder.DTOs.UserDTO;
import com.org.carder.constant.CommonMsg;
import com.org.carder.entity.Attendance;
import com.org.carder.entity.User;
import com.org.carder.repository.AttendanceRepository;
import com.org.carder.utill.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AttendanceService {

    private final Logger logger = LoggerFactory.getLogger(AttendanceService.class);

    private final UserService userService;
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceService(UserService userService, AttendanceRepository attendanceRepository) {
        this.userService = userService;
        this.attendanceRepository = attendanceRepository;
    }

    /**
     * mark employee attendance
     * @param attendanceDTO
     * @return
     */
    public CommonResponse markAttendance(AttendanceDTO attendanceDTO){
        CommonResponse commonResponse = new CommonResponse();
        Attendance attendance;
        try {
            Boolean isExistsUser = userService.existByEmpNumber(Long.parseLong(attendanceDTO.getEmpNumber()));
            if (isExistsUser){
                 attendance = attendanceRepository.save(getAttendanceEntity(attendanceDTO));
                commonResponse.setStatus(true);
                commonResponse.setPayload(Arrays.asList(attendance.getEmpNumber()));
            }else {
                commonResponse.setStatus(false);
                commonResponse.setErrorMessages(Arrays.asList(CommonMsg.NOT_FOUND_IN_SYSTEM));
            }
        }catch (Exception e){
            logger.warn("/******** Exception in AttendanceService -> markAttendance() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     * Convert attendanceDTO to AttendanceEntity
     * @param attendanceDTO
     * @return
     */
    public Attendance getAttendanceEntity(AttendanceDTO attendanceDTO){
        return new Attendance(
                attendanceDTO.getId() == null ? null : Long.parseLong(attendanceDTO.getId()),
                userService.getUser(Long.parseLong(attendanceDTO.getEmpNumber())),
                attendanceDTO.getAttendance()
        );
    }
}
