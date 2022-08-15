package com.org.carder.service;

import com.org.carder.DTOs.AttendanceDTO;
import com.org.carder.constant.AttendanceEnum;
import com.org.carder.constant.CommonStatus;
import com.org.carder.entity.Attendance;
import com.org.carder.entity.Holiday;
import com.org.carder.entity.Leave;
import com.org.carder.entity.User;
import com.org.carder.repository.AttendanceRepository;
import com.org.carder.utill.CommonResponse;
import com.org.carder.utill.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final Logger logger = LoggerFactory.getLogger(AttendanceService.class);

    @Value("${fileUploadPath}")
    private String UPLOADED_FOLDER;



    private final UserService userService;
    private final AttendanceRepository attendanceRepository;
    private final LeaveService leaveService;
    private final HolidayService holidayService;

    @Autowired
    public AttendanceService(UserService userService, AttendanceRepository attendanceRepository, LeaveService leaveService, HolidayService holidayService) {
        this.userService = userService;
        this.attendanceRepository = attendanceRepository;
        this.leaveService = leaveService;
        this.holidayService = holidayService;
    }

    /**
     * mark employee attendance
     * @param fileName
     * @return
     */
    @Transactional
    public CommonResponse fetchAttendance(String fileName){
        CommonResponse commonResponse = new CommonResponse();
        String lineText = null;
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(UPLOADED_FOLDER+fileName));
            lineReader.readLine(); // skip header line
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String empNumber = data[0];
                saveAttendance(empNumber);
            }
            lineReader.close();
            updateAbsentEmployee();
        commonResponse.setStatus(true);
        } catch (IOException ex) {logger.warn("/********Exception in AttendanceService -> fetchAttendance() :" + ex);}
        return commonResponse;
    }

    /**
     * mark absent attendance
     */
    @Transactional
    public void updateAbsentEmployee(){
            try {
                List<User> userList = userService.getAllUserList();
                userList.forEach(user -> {
                    Attendance attendance = attendanceRepository.getAttendanceByDateAndAndEmployee(DateTimeUtil.getSriLankaDate(),user);
                    Leave leave = leaveService.getLeave(user.getEmpNumber(),DateTimeUtil.getSriLankaDate());
                    if (attendance == null && leave == null){
                        Attendance absentAttendance = getAbsentAttendanceEntity(user.getEmpNumber().toString());
                        attendanceRepository.save(absentAttendance);
                    }
                    if (leave != null){
                        Attendance leaveAttendance = getLeaveAttendanceEntity(user.getEmpNumber().toString());
                        attendanceRepository.save(leaveAttendance);
                    }
                });
            }catch (Exception e){logger.warn("/********Exception in AttendanceService -> updateAbsentEmployee() :" + e);}
     }

    /**
     * get all attendance
     * @return
     */
    @Transactional
    public CommonResponse getAttendance(){
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<AttendanceDTO> attendanceDTOS = getAll().
                                                 stream().
                                                 map(this::getAttendanceDTO).
                                                 collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(attendanceDTOS));
        }catch (Exception e){logger.warn("/********Exception in AttendanceService -> getAttendance() :" + e);}
        return commonResponse;
    }

    /**
     * get absent attendance list
     * @return
     */
    public Set<Attendance> getFourDaysAgoAbsentList(){
        Set<Attendance> attendanceList = null;
        try {
                Predicate<Attendance> filterOnAbsent = attendance -> attendance.getAttendance() == AttendanceEnum.ABSENT;
                LocalDate fourDaysAgo = LocalDate.now().minusDays(4);
                attendanceList = attendanceRepository.findAllByDate(fourDaysAgo)
                                .stream()
                                .filter(filterOnAbsent)
                                .collect(Collectors.toSet());
        }catch (Exception e){logger.warn("/********Exception in AttendanceService -> getAllAbsentList() :" + e);}
        return attendanceList;
    }

    public int getTodayAbsentCount(){
        int count = 0;
        List<Attendance> attendanceList = null;
        try {
            Predicate<Attendance> filterOnAbsent = attendance -> attendance.getAttendance() == AttendanceEnum.ABSENT;
            attendanceList = attendanceRepository.getAllByDate(DateTimeUtil.getSriLankaDate())
                    .stream()
                    .filter(filterOnAbsent)
                    .collect(Collectors.toList());
            count = attendanceList.size();
        }catch (Exception e){logger.warn("/********Exception in AttendanceService -> getTodayAbsentCount() :" + e);}
        return count;
    }
    /**
     * get All attendance
     * @return
     */
    @Transactional
    public List<Attendance> getAll(){
        return attendanceRepository.findAll();
    }

    /**
     *save attendance using txt file
     * @param empNumber
     */
    @Transactional
    public void saveAttendance(String empNumber){
        try {
              Attendance presentAttendance = getPresentAttendanceEntity(empNumber);
              attendanceRepository.save(presentAttendance);
        }catch (Exception e){logger.warn("/********Exception in AttendanceService -> saveAttendance() :" + e); }
    }

    /**
     *get present attendance entity
     * @param empNumber
     * @return
     */
    @Transactional
    public Attendance getPresentAttendanceEntity(String empNumber){
        AttendanceEnum attendanceEnum;
      String isTodayHoliday = DateTimeUtil.getLocalDate();
      Holiday  holiday = holidayService.getHolidayByDate(isTodayHoliday);
      if (holiday == null)
       attendanceEnum= AttendanceEnum.PRESENT;
     else
       attendanceEnum = AttendanceEnum.PRESENT_IN_HOLIDAY;
        return new Attendance(
                null,
                userService.getUserByEmpNum(Long.parseLong(empNumber)),
                DateTimeUtil.getSriLankaDate(),
                attendanceEnum,
                CommonStatus.ACTIVE
        );
    }

    /**
     *get absent attendance entity
     * @param empNumber
     * @return
     */
    @Transactional
    public Attendance getAbsentAttendanceEntity(String empNumber){
        return new Attendance(
                null,
                userService.getUserByEmpNum(Long.parseLong(empNumber)),
                DateTimeUtil.getSriLankaDate(),
                AttendanceEnum.ABSENT,
                CommonStatus.ACTIVE
        );
    }

    /**
     *get absent attendance entity
     * @param empNumber
     * @return
     */
    @Transactional
    public Attendance getLeaveAttendanceEntity(String empNumber){
        return new Attendance(
                null,
                userService.getUserByEmpNum(Long.parseLong(empNumber)),
                DateTimeUtil.getSriLankaDate(),
                AttendanceEnum.LEAVE,
                CommonStatus.ACTIVE
        );
    }

    /**
     *get attendance dto
     * @param attendance
     * @return
     */
    @Transactional
    public AttendanceDTO getAttendanceDTO(Attendance attendance){
        return new AttendanceDTO(
                attendance.getId().toString(),
                userService.getUserDTOByEmpNum(attendance.getEmployee().getEmpNumber()),
                attendance.getDate().toString(),
                attendance.getAttendance(),
                attendance.getCommonStatus()


        );
    }

}
