package com.org.carder.service;

import com.org.carder.DTOs.ComplainDTO;
import com.org.carder.DTOs.ComplainInsertDTO;
import com.org.carder.DTOs.UserDTO;
import com.org.carder.constant.CommonMsg;
import com.org.carder.constant.CommonStatus;
import com.org.carder.constant.ComplainStatus;
import com.org.carder.entity.Complain;
import com.org.carder.entity.User;
import com.org.carder.repository.ComplainRepository;
import com.org.carder.utill.CommonResponse;
import com.org.carder.utill.CommonValidation;
import com.org.carder.utill.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ComplainService {

    private final Logger logger = LoggerFactory.getLogger(ComplainService.class);

    private final UserService userService;
    private final ComplainRepository complainRepository;
    private final DepartmentService departmentService;

    @Autowired
    public ComplainService(UserService userService , ComplainRepository complainRepository,DepartmentService departmentService) {
        this.userService = userService;
        this.complainRepository = complainRepository;
        this.departmentService = departmentService;
    }

    /**
     * fetchComplain into the DB
     * @param dto
     * @return
     */
    public CommonResponse fetchComplain(ComplainInsertDTO dto){
        CommonResponse commonResponse = new CommonResponse();
        Complain complain = new Complain();
        try {
            List<String> validations = validationComplain(dto);
            if (!CollectionUtils.isEmpty(validations)) {
                commonResponse.setErrorMessages(validations);
                return commonResponse;
            }
            complain = this.getComplainEntity(complain,dto);
            complainRepository.save(complain);
            commonResponse.setStatus(true);
        }catch (Exception e){
            logger.warn("/******** Exception in ComplainService -> fetchComplain() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     * set complain reply ComplainDTO using
     * @param dto
     * @return
     */
    public CommonResponse replayComplain(ComplainInsertDTO dto){
        CommonResponse commonResponse = new CommonResponse();
        try {
            Complain complain = complainRepository.getOne(Long.parseLong(dto.getId()));
            if (CommonValidation.stringNullValidation(dto.getComplainReply())) {
                commonResponse.setErrorMessages(new ArrayList<>(Collections.singletonList("Enter replay")));
                return commonResponse;
            }
            complain.setComplainReply(dto.getComplainReply());
            complain.setComplainStatus(ComplainStatus.REPLAY);
            complain.setRepliedUser(userService.getUserByEmpNum(Long.parseLong(dto.getRepliedUserEmpNum())));
            complain.setRepliedOn(LocalDateTime.now());
            complainRepository.save(complain);
            commonResponse.setStatus(true);
        }catch (Exception e){
            logger.warn("/******** Exception in ComplainService -> replayComplain() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     *find Complain By User EmpNum
     * @param userEmpNum
     * @return
     */
    public CommonResponse findComplainByUserEmpNum(String userEmpNum){
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<Complain> complains =this.getComplainByUserEmpNum(Long.parseLong(userEmpNum));
            commonResponse.setPayload(new ArrayList<>(Arrays.asList(getComplainDTOList(complains))));
        }catch (Exception e){
            logger.warn("/******** Exception in ComplainService -> findComplainByUserEmpNum() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     *
     * @return
     */
    public List<ComplainDTO> getAllComplain(){
        try {
            Predicate<Complain> filterOnStatus = c -> c.getCommonStatus() != CommonStatus.DELETED;
            return complainRepository
                    .findAll()
                    .stream()
                    .filter(filterOnStatus)
                    .map(this::getComplainDTO)
                    .collect(Collectors.toList());
        }catch (Exception e) {
            logger.warn("/******** Exception in UserService -> findAll() :" + e);
        }
        return null;
    }

    /**
     * get ComplainDTOS
     * @param userEmpNum
     * @return
     */
    public  List<ComplainDTO> getComplainDTOS(String userEmpNum){
        return getComplainDTOList(this.getComplainByUserEmpNum(Long.parseLong(userEmpNum)));
    }

    /**
     * get complain on filtering
     * @param depTitle
     * @param List<ComplainDTO>
     * @return
     */
    public List<ComplainDTO> getComplainByDepartment(String depTitle){
        try {
            Predicate<Complain> filterOnComplainByDep = c -> c.getDepartment().getDepartmentTitle().equals(depTitle);
            return complainRepository.findAll()
                                      .stream()
                                      .filter(filterOnComplainByDep)
                                      .map(this::getComplainDTO)
                                      .collect(Collectors.toList());

        }catch (Exception e){
            logger.warn("/******** Exception in ComplainService -> getUnreadComplainByDepartment() :" + e);

        }
        return null;
    }

    public CommonResponse findComplainById(String complainId){
        CommonResponse commonResponse = new CommonResponse();
        try {
            ComplainDTO complainDTO = this.getComplainById(complainId);
            commonResponse.setStatus(true);
            commonResponse.setPayload(new ArrayList<>(Collections.singletonList(complainDTO)));
        }catch (Exception e){
            logger.warn("/******** Exception in ComplainService -> getUnreadComplainByDepartment() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }


    /**
     * get Complain By User EmpNum
     * @param empNum
     * @return
     */
    public List<Complain> getComplainByUserEmpNum(Long empNum){
        return complainRepository.findComplainsByComplainUser(empNum);
    }

    public ComplainDTO getComplainById(String complainId){
        return getComplainDTO(complainRepository.getOne(Long.parseLong(complainId)));
    }

    public void  updateComplainStatus(String complainId){
        Complain complain = complainRepository.getOne(Long.parseLong(complainId));
        complain.setComplainStatus(ComplainStatus.READ);
        complainRepository.save(complain);
    }

    /**
     * get ComplainDTO List
     * @param complainList
     * @return
     */
    public List<ComplainDTO> getComplainDTOList(List<Complain> complainList){
        List<ComplainDTO> complainDTOS = new ArrayList<>();
        try {
            for (Complain complain: complainList) {
                complainDTOS.add(getComplainDTO(complain));
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.warn("/******** Exception in ComplainService -> getComplainDTOList() :" + e);
        }
        return complainDTOS;
    }

    /**
     * get Complain entity using ComplainDTO
     * @param complain
     * @param dto
     * @return
     */
    public Complain getComplainEntity(Complain complain,ComplainInsertDTO dto){
        complain.setComplainTitle(dto.getComplainTitle());
        complain.setComplainDescription(dto.getComplainDescription());
        complain.setComplainStatus(dto.getComplainStatus());
        complain.setCreatedOn(LocalDateTime.now());
        complain.setComplainUser(userService.getUserByEmpNum(Long.parseLong(dto.getComplainUserEmpNum())));
        complain.setDepartment(departmentService.getDepartmentByTitle(dto.getDepartmentTitle()));
        complain.setCommonStatus(dto.getCommonStatus());
        return complain;
    }

    /**
     * getComplainDTO using complain
     * @param complain
     * @return
     */
    public ComplainDTO getComplainDTO(Complain complain){
        UserDTO repliedUserDTO = null;
        User repliedUser = complain.getRepliedUser();
        if (repliedUser != null) {
            repliedUserDTO = userService.getUserDTO(complain.getRepliedUser());
        }
        return new ComplainDTO(
                complain.getId().toString(),
                complain.getComplainTitle(),
                complain.getComplainDescription(),
                complain.getComplainReply(),
                complain.getComplainStatus(),
                complain.getCommonStatus(),
                departmentService.getDepartmentDTO(complain.getDepartment().getDepartmentTitle()),
                complain.getCreatedOn(),
                complain.getRepliedOn(),
                userService.getUserDTO(complain.getComplainUser()),
               repliedUserDTO
        );
    }

    public long getComplainCount(){
        return complainRepository.count();
    }

    /**
     *validate complain DTO
     * @return
     */
    private List<String> validationComplain(ComplainInsertDTO dto){
        List<String> validations = new ArrayList<>();
        if (dto == null){
            validations.add(CommonMsg.CHECK_INPUT_DATA);
        }else if (CommonValidation.stringNullValidation(dto.getComplainTitle())){
            validations.add(CommonMsg.COMPLAIN_TITLE);
        }else if (CommonValidation.stringNullValidation(dto.getComplainDescription())){
            validations.add(CommonMsg.COMPLAIN_DESCRIPTION);
        }else if  (CommonValidation.stringNullValidation(dto.getDepartmentTitle())){
            validations.add(CommonMsg.ENTER_DEPARTMENT_TITLE);
        }
        if (!dto.getId().equals("")) {
            if (CommonValidation.stringNullValidation(dto.getComplainReply())) {
                validations.add(CommonMsg.COMPLAIN_REPLY);
            }
        }
        return validations;
    }
}
