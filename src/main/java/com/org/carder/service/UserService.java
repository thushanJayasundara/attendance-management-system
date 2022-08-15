package com.org.carder.service;


import com.org.carder.DTOs.UserDTO;
import com.org.carder.constant.CommonMsg;
import com.org.carder.constant.CommonStatus;
import com.org.carder.entity.User;
import com.org.carder.repository.UserRepository;
import com.org.carder.utill.CommonResponse;
import com.org.carder.utill.CommonValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * user service
 */
@Service
public  class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final DepartmentService departmentService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, DepartmentService departmentService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.departmentService = departmentService;
        this.passwordEncoder = encoder;
    }

    /**
     * save and updare user
     * @param dto
     * @return
     */
    public CommonResponse saveUpdate (final UserDTO dto){
        CommonResponse commonResponse =  new CommonResponse();
        User user;
        try {

            User existingUser = this.getUserByEmpNum(Long.valueOf(dto.getEmpNumber()));

            if (dto.getId().isEmpty() || dto.getId() == null){
                List<String> validations = validateUserAccountDto(existingUser,dto);
                if (!CollectionUtils.isEmpty(validations)) {
                    commonResponse.setErrorMessages(validations);
                    return commonResponse;
                }
                user = new User();
            } else {
                user = getUser(Long.parseLong(dto.getId()));
                List<String> validations = validateUserAccountDto(user,dto);
                if (!CollectionUtils.isEmpty(validations)) {
                    commonResponse.setErrorMessages(validations);
                    return commonResponse;
                }
            }
            user = getUserEntity(user,dto);
            user = userRepository.save(user);
            commonResponse.setStatus(true);
            commonResponse.setPayload(new ArrayList<>(Arrays.asList(getUserDTO(user))));
        }catch (Exception e){
            logger.warn("/******** Exception in UserService -> saveUpdate() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     * get user by emp number
     * @param empNumber
     * @return
     */
    public CommonResponse findByEmpNum(final String empNumber){
        CommonResponse commonResponse =  new CommonResponse();
        User user;
        try {
            user=this.getUserByEmpNum(Long.parseLong(empNumber));
            commonResponse.setStatus(true);
            commonResponse.setPayload(new ArrayList<>(Arrays.asList(getUserDTO(user))));
        }
       catch (Exception e){
            logger.warn("/******** Exception in UserService -> findByEmpNum() :" + e);
           commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     * delete user by using emp number
     * @param empNumber
     * @return
     */
    public CommonResponse deleteUserByEmpNum(final String empNumber){
        CommonResponse commonResponse =  new CommonResponse();
        User user;
        try {
            user = this.getUserByEmpNum(Long.parseLong(empNumber));
            user.setCommonStatus(CommonStatus.DELETED);
            userRepository.save(user);
            commonResponse.setStatus(true);
        }catch (Exception e){
            logger.warn("/******** Exception in UserService -> deleteUserByEmpNum() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     * get all active and inactive users
     * @return
     */
    public CommonResponse findAll(){
        CommonResponse commonResponse =  new CommonResponse();
        try {
            Predicate<User> filterOnStatus = user -> user.getCommonStatus() != CommonStatus.DELETED;
            List<UserDTO> userDTOS = userRepository.findAll()
                                                        .stream()
                                                          .filter(filterOnStatus)
                                                             .map(user -> getUserDTO(user))
                                                                .collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(new ArrayList<Object>(userDTOS));
        }catch (Exception e){
            logger.warn("/******** Exception in UserService -> findAll() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     * get all active and inactive users
     * @return
     */
    public  List<UserDTO> getAll(){
        try {
            Predicate<User> filterOnStatus = user -> user.getCommonStatus() != CommonStatus.DELETED;
            List<UserDTO> userDTOS = userRepository.findAll()
                    .stream()
                    .filter(filterOnStatus)
                    .map(user -> getUserDTO(user))
                    .collect(Collectors.toList());
          return userDTOS;
        }catch (Exception e){
            logger.warn("/******** Exception in UserService -> findAll() :" + e);
           e.printStackTrace();
        }
        return null;
    }

    /**
     * get all user count
     * @return
     */
    public long getUserCount(){
         return userRepository.count();
    }

    /**
     * get user by user id
     * @param userId
     * @return
     */
    public User getUser(Long userId){
        return userRepository.getOne(userId);
    }

    /**
     * get user by emp number
     * @param eNumber
     * @return
     */
    public User getUserByEmpNum(Long eNumber){
        return userRepository.findByEmpNumber(eNumber);
    }

    /**
     * check exist user by emp number
     * @param eNumber
     * @return
     */
    public Boolean existByEmpNumber(Long eNumber){
        return userRepository.existsByEmpNumber(eNumber);
    }


    /**
     * get userDTO by emp number
     * @param eNumber
     * @return
     */
    public UserDTO getUserDTOByEmpNum(Long eNumber){
       User user =  userRepository.findByEmpNumber(eNumber);
        return getUserDTO(user);
    }

    /**
     * get user entity
     * @param user
     * @param dto
     * @return
     */
    public User getUserEntity(User user,UserDTO dto){
        user.setEmpNumber(Long.parseLong(dto.getEmpNumber()));
        user.setName(dto.getName());
        user.setNic(dto.getNic());
        user.setMobile(dto.getMobile());
        user.setUserRole(dto.getUserRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCommonStatus(dto.getCommonStatus());
        user.setDepartment(departmentService.getDepartmentByTitle(dto.getDepartmentDTO().getDepartmentTitle()));
        if (!dto.getId().isEmpty() && dto.getId() != null) {
            user.setPassword(user.getPassword());
        }
        return user;
    }

    /**
     * get user DTO
     * @param user
     * @return
     */
    public UserDTO getUserDTO(User user){
        return new UserDTO(String.valueOf(user.getId()),
                            String.valueOf(user.getEmpNumber()),
                            user.getName(), user.getNic(),
                            user.getPassword(),
                            user.getMobile(),
                            user.getCommonStatus(),
                            user.getUserRole(),
                            departmentService.getDepartmentDTO(user.getDepartment())
        );
    }

    /**
     * using validation purpose
     * @param user
     * @param dto
     * @return
     */
    private List<String> validateUserAccountDto(User user, UserDTO dto) {
        List<String> validations = new ArrayList<>();
        if (dto == null) {
            validations.add(CommonMsg.CHECK_INPUT_DATA);
        } else if (CommonValidation.stringNullValidation(dto.getName())) {
            validations.add(CommonMsg.ENTER_YOUR_NAME);
        } else if (CommonValidation.stringNullValidation(dto.getId()) && user != null) {
            validations.add(CommonMsg.EMP_NUMBER_EXIST);
        } else if (CommonValidation.validPassword(dto.getPassword())) {
            validations.add(CommonMsg.ENTER_PASSWORD);
        }else if (CommonValidation.stringNullValidation(dto.getMobile())) {
            validations.add(CommonMsg.ENTER_MOBILE_NUMBER);
        } else if (CommonValidation.isValidMobile(dto.getMobile())){
            validations.add(CommonMsg.ENTER_MOBILE_NUMBER);
        }else if (dto.getUserRole() == null) {
            validations.add(CommonMsg.ENTER_ROLE);
        } else if (CommonValidation.stringNullValidation(dto.getNic())){
            validations.add(CommonMsg.ENTER_YOUR_NIC);
        }else if(CommonValidation.validNic(dto.getNic())) {
            validations.add(CommonMsg.INVALID_NIC);
        }else if (CommonValidation.isNumber(dto.getEmpNumber())) {
            validations.add(CommonMsg.ENTER_EMPLOYEE_NUMBER);
        }else if ((dto.getId().equals("") && dto.getId()== null)  && (user.getNic().equals(dto.getNic()))){
                validations.add(CommonMsg.NIC_NUMBER_EXIST);

        }
        return validations;
    }
}
