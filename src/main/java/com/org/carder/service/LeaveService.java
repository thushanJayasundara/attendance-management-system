package com.org.carder.service;


import com.org.carder.DTOs.LeaveDTO;
import com.org.carder.DTOs.insertDTO.LeaveInsertDTO;
import com.org.carder.constant.CommonMsg;
import com.org.carder.constant.CommonStatus;
import com.org.carder.entity.Leave;
import com.org.carder.repository.LeaveRepository;
import com.org.carder.utill.CommonResponse;
import com.org.carder.utill.CommonValidation;
import com.org.carder.utill.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    private final Logger logger = LoggerFactory.getLogger(LeaveService.class);

    private final LeaveRepository leaveRepository;
    private final UserService userService;

    @Autowired
    public LeaveService(LeaveRepository leaveRepository, UserService userService) {
        this.leaveRepository = leaveRepository;
        this.userService = userService;
        ;
    }

    /**
     * save and update leaves
     * @param leaveDTO
     * @return
     */
    @Transactional
    public CommonResponse saveUpdate(LeaveInsertDTO leaveDTO){
        CommonResponse commonResponse = new CommonResponse();
        Leave leave;
        Leave existingLeave;
        try {
            List<String> validateList = validateLeave(leaveDTO);
            if (validateList.isEmpty()){
                existingLeave  = leaveRepository.getLeaveByLeaveUserAndFromDate(
                        Long.parseLong(leaveDTO.getLeaveEmpNumber()),
                        DateTimeUtil.getFormattedDate(leaveDTO.getLeaveDate()));
                if (existingLeave == null){
                    leave = getEntityByLeaveInsertDTO(leaveDTO);
                    leave = leaveRepository.save(leave);
                    commonResponse.setStatus(true);
                    commonResponse.setPayload(Collections.singletonList(getLeaveDTO(leave)));
                }
                else{
                    leave = getEntityByLeaveInsertDTO(leaveDTO);
                    existingLeave.setLeaveUser(leave.getLeaveUser());
                    existingLeave.setReason(leave.getReason());
                    existingLeave.setCommonStatus(leave.getCommonStatus());
                    existingLeave.setLeaveDate(leave.getLeaveDate());
                    leave = leaveRepository.save(existingLeave);
                    commonResponse.setStatus(true);
                    commonResponse.setPayload(Collections.singletonList(getLeaveDTO(leave)));
                }
            }else
                commonResponse.setErrorMessages(validateList);
        }catch (Exception e){logger.warn("/******** Exception in LeaveService -> saveUpdate() :" + e); }
        return commonResponse;
    }

    /**
     *
     *
     * @return
     */
    public CommonResponse getTodayLeave(){
        CommonResponse commonResponse = new CommonResponse();
        try {
            Predicate<Leave> filterOnStatus = leave -> leave.getCommonStatus() != CommonStatus.DELETED;
            List<LeaveDTO> leaveDTOS = leaveRepository.getLeavesByLeaveDate(DateTimeUtil.getSriLankaDate())
                    .stream()
                    .filter(filterOnStatus)
                    .map(this::getLeaveDTO)
                    .collect(Collectors.toList());

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(leaveDTOS));
        }catch (Exception e){logger.warn("/******** Exception in LeaveService -> getTodayLeave() :" + e); }
        return  commonResponse;
    }

    /**
     * get all leaves
     * @return
     */
    @Transactional
    public CommonResponse getAllLeave(){
        CommonResponse commonResponse =  new CommonResponse();
        try {
            Predicate<Leave> filterOnStatus = leave -> leave.getCommonStatus() != CommonStatus.DELETED;
            List<LeaveDTO> leaveDTOS = leaveRepository.findAll()
                    .stream()
                    .filter(filterOnStatus)
                    .map(this::getLeaveDTO)
                    .collect(Collectors.toList());

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(leaveDTOS));
        }catch (Exception e){ logger.warn("/******** Exception in LeaveService -> getAllLeave() :" + e); }
        return commonResponse;
    }

    /**
     * leave advance search
     * @param leaveDate
     * @param empNumber
     * @return
     */
    @Transactional
    public CommonResponse advanceSearch(String leaveDate,String empNumber){
        CommonResponse commonResponse =  new CommonResponse();
        Leave leave = null;
        try {
                leave = getLeave(Long.parseLong(empNumber),DateTimeUtil.getFormattedDate(leaveDate));
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(getLeaveDTO(leave)));
        }catch (Exception e){ logger.warn("/******** Exception in LeaveService -> getAllLeave() :" + e); }
        return commonResponse;
    }

    /**
     * get leave entity
     * @param empNumber
     * @param date
     * @return
     */
    @Transactional
    public Leave getLeave(Long empNumber ,LocalDate date){
       return leaveRepository.getLeaveByLeaveUserAndFromDate(empNumber,date);
    }

    /**
     * validate holidayDTO entity
     * @param leaveDTO
     * @return
     */
    public List<String> validateLeave(LeaveInsertDTO leaveDTO){
        List<String> validateLeave = new ArrayList<>();
        if (CommonValidation.stringNullValidation(leaveDTO.getLeaveEmpNumber()))
            validateLeave.add(CommonMsg.ENTER_EMPLOYEE_NUMBER);
        else if (CommonValidation.stringNullValidation(leaveDTO.getReason()))
            validateLeave.add(CommonMsg.ENTER_REASON_LEAVE);
        else if(!userService.existByEmpNumber(Long.parseLong(leaveDTO.getLeaveEmpNumber())))
            validateLeave.add(CommonMsg.ENTER_EMPLOYEE_NUMBER);
        else if (leaveDTO.getCommonStatus() == null){
            validateLeave.add(CommonMsg.ENTER_STATUS);
        }

        return validateLeave;
    }


    /**
     * convert leaveDTO to Leave entity
     * @param leaveDTO
     * @return
     */
    @Transactional
    public Leave getEntityByLeaveInsertDTO(LeaveInsertDTO leaveDTO){
        return new Leave(
                leaveDTO.getId() == null ? null : Long.parseLong(leaveDTO.getId()),
                userService.getUserByEmpNum(Long.parseLong(leaveDTO.getLeaveEmpNumber())),
                leaveDTO.getReason(),
                leaveDTO.getCommonStatus(),
                DateTimeUtil.getFormattedDate(leaveDTO.getLeaveDate())

        );
    }

    /**
     * convert leaveDTO to Leave entity
     * @param leaveDTO
     * @return
     */
    @Transactional
    public Leave getLeaveEntity(LeaveDTO leaveDTO){
        return new Leave(
                leaveDTO.getId() == null ? null : Long.parseLong(leaveDTO.getId()),
                userService.getUserByEmpNum(Long.parseLong(leaveDTO.getLeaveUser().getEmpNumber())),
                leaveDTO.getReason(),
                leaveDTO.getCommonStatus(),
                DateTimeUtil.getFormattedDate(leaveDTO.getLeaveDate())
        );
    }

    /**
     * convert leave entity to leave DTO
     * @param leave
     * @return
     */
    @Transactional
    public LeaveDTO getLeaveDTO(Leave leave){
        return new LeaveDTO(
          leave.getId().toString(),
          userService.getUserDTO(leave.getLeaveUser()),
          leave.getReason(),
          leave.getCommonStatus(),
          leave.getLeaveDate().toString()
        );
    }

}
