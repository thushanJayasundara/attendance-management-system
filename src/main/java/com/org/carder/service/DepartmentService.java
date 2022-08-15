package com.org.carder.service;

import com.org.carder.DTOs.DepartmentDTO;
import com.org.carder.constant.CommonMsg;
import com.org.carder.constant.CommonStatus;
import com.org.carder.entity.Department;
import com.org.carder.repository.DepartmentRepository;
import com.org.carder.utill.CommonResponse;
import com.org.carder.utill.CommonValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * department service
 */
@Service
public class DepartmentService {

    private final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;
    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * department save update
     * @param dto
     * @return
     */
    public CommonResponse saveUpdate(DepartmentDTO dto){
        CommonResponse commonResponse = new CommonResponse();
        Department department;
        try {
            Department existingDepartment = this.getDepartmentByTitle(dto.getDepartmentTitle());
            if (dto.getId().isEmpty() || dto.getId() == null){
                List<String> validations = validateDepartmentDTO(existingDepartment,dto);
                if (!CollectionUtils.isEmpty(validations)) {
                    commonResponse.setErrorMessages(validations);
                    return commonResponse;
                }
                department = new Department();
            }else {
                department = this.getDepartment(Long.parseLong(dto.getId()));
                List<String> validations = validateDepartmentDTO(department,dto);
                if (!CollectionUtils.isEmpty(validations)) {
                    commonResponse.setErrorMessages(validations);
                    return commonResponse;
                }
            }
            department = getDepartmentEntity(department,dto);
            department=departmentRepository.save(department);
            commonResponse.setStatus(true);
            commonResponse.setPayload(new ArrayList<>(Arrays.asList(getDepartmentDTO(department))));
        }catch (Exception e){
            logger.warn("/******** Exception in DepartmentService -> saveUpdate() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     * get department using department title
     * @param departmentTitle
     * @return
     */
    public CommonResponse findDepartmentByTitle(String departmentTitle){
        CommonResponse commonResponse = new CommonResponse();
        Department department;
        try {
            department = this.getDepartmentByTitle(departmentTitle);
            commonResponse.setStatus(true);
            commonResponse.setPayload(new ArrayList<>(Arrays.asList(getDepartmentDTO(department))));
        }catch (Exception e){
            logger.warn("/******** Exception in DepartmentService -> findDepartmentByTitle() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     * delete department by department title
     * @param departmentTitle
     * @return
     */
    public CommonResponse deleteDepartmentByTitle(String departmentTitle){
        CommonResponse commonResponse = new CommonResponse();
        Department department;
        try {
            department = this.getDepartmentByTitle(departmentTitle);
            department.setCommonStatus(CommonStatus.DELETED);
            departmentRepository.save(department);
            commonResponse.setStatus(true);
        }catch (Exception e){
            logger.warn("/******** Exception in DepartmentService -> deleteDepartmentByTitle() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     * find all
     * @return
     */
    public CommonResponse findAll(){
        CommonResponse commonResponse = new CommonResponse();
        Department department;
        try {
            Predicate<Department> filterOnStatus = dep -> dep.getCommonStatus() != CommonStatus.DELETED;

            List<DepartmentDTO> departmentDTOS = departmentRepository.findAll()
                                                                        .stream()
                                                                            .filter(filterOnStatus)
                                                                                .map(dep -> getDepartmentDTO(dep) )
                                                                                    .collect(Collectors.toList());
            commonResponse.setPayload(new ArrayList<>(Arrays.asList(departmentDTOS)));
            commonResponse.setStatus(true);
        }catch (Exception e){
            logger.warn("/******** Exception in DepartmentService -> findAll() :" + e);
            commonResponse.getErrorMessages().add(e.getMessage());
        }
        return commonResponse;
    }

    /**
     *
     * @return
     */
    public List<DepartmentDTO> getAll(){
        try {
            Predicate<Department> filterOnStatus = dep -> dep.getCommonStatus() != CommonStatus.DELETED;

            List<DepartmentDTO> departmentDTOS = departmentRepository.findAll()
                    .stream()
                    .filter(filterOnStatus)
                    .map(dep -> getDepartmentDTO(dep) )
                    .collect(Collectors.toList());
            return departmentDTOS;
        }catch (Exception e){
            logger.warn("/******** Exception in DepartmentService -> getAll() :" + e);
        }
       return null;
    }

    public DepartmentDTO getDepartmentDTO(String depTitle){
        return getDepartmentDTO(this.getDepartmentByTitle(depTitle));
    }
    /**
     * get department entity using department title
     * @param departmentTitle
     * @return
     */
    public Department getDepartmentByTitle(String departmentTitle){
        return departmentRepository.findByDepartmentTitle(departmentTitle);
    }

    /**
     * get department entity using department id
     * @param departmentId
     * @return
     */
    public Department getDepartment(Long departmentId){
        return departmentRepository.getOne(departmentId);
    }


    /**
     * get department TO using department entity
     * @param department
     * @return
     */
    public DepartmentDTO getDepartmentDTO(Department department){
        return new DepartmentDTO(
                department.getId().toString(),
                department.getDepartmentTitle(),
                department.getDepartmentDescription(),
                department.getDepartmentContactNumber(),
                department.getCommonStatus()
        );
    }

    /**
     * get department entity using department DTO
     * @param department
     * @param dto
     * @return
     */
    public Department getDepartmentEntity(Department department,DepartmentDTO dto){
        department.setDepartmentTitle(dto.getDepartmentTitle());
        department.setDepartmentDescription(dto.getDepartmentDescription());
        department.setDepartmentContactNumber(dto.getDepartmentContactNumber());
        department.setCommonStatus(dto.getCommonStatus());
        return department;
    }

    public long getDepartmentCount(){
        return departmentRepository.count();
    }
    /**
     * validation department dto
     * @param department
     * @param dto
     * @return
     */
    private List<String> validateDepartmentDTO(Department department,DepartmentDTO dto){
        List<String> validations = new ArrayList<>();
        if (dto == null) {
            validations.add(CommonMsg.CHECK_INPUT_DATA);
        }else if (department != null && CommonValidation.stringNullValidation(dto.getId())){
            validations.add(CommonMsg.DEPARTMENT_IS_EXIST);
        }else if (CommonValidation.stringNullValidation(dto.getDepartmentTitle())){
            validations.add(CommonMsg.ENTER_DEPARTMENT_TITLE);
        }else if (CommonValidation.stringNullValidation(dto.getDepartmentDescription())){
            validations.add(CommonMsg.ENTER_DEPARTMENT_DESCRIPTION);
        }else if (CommonValidation.stringNullValidation(dto.getDepartmentContactNumber())){
            validations.add(CommonMsg.ENTER_DEPARTMENT_CONTACT);
        }else if (CommonValidation.isValidMobile(dto.getDepartmentContactNumber())){
            validations.add(CommonMsg.ENTER_VALID_CONTACT_NUMBER);
        }
        return validations;
    }

}
