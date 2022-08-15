package com.org.carder.service;

import com.org.carder.DTOs.HolidayDTO;
import com.org.carder.constant.CommonMsg;
import com.org.carder.constant.CommonStatus;
import com.org.carder.entity.Holiday;
import com.org.carder.repository.HolidayRepository;
import com.org.carder.utill.CommonResponse;
import com.org.carder.utill.CommonValidation;
import com.org.carder.utill.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class HolidayService {

    private final Logger logger = LoggerFactory.getLogger(HolidayService.class);

    private final HolidayRepository holidayRepository;

    @Autowired
    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    /**
     * save update holiday details
     * @param holidayDTO
     * @return
     */
    @Transactional
    public CommonResponse saveUpdate(HolidayDTO holidayDTO){
        CommonResponse commonResponse = new CommonResponse();
        Holiday holiday;
        try{


            List<String> validateList = validateHoliday(holidayDTO);
            if (validateList.isEmpty()){
                Boolean isExistByDate = holidayRepository.existsByHoliday(DateTimeUtil.getFormattedDate(holidayDTO.getHoliday()));
                if (!isExistByDate){
                    holiday = getHolidayEntity(holidayDTO);
                    holiday = holidayRepository.save(holiday);
                    commonResponse.setStatus(true);
                    commonResponse.setPayload(Collections.singletonList(getHolidayDTO(holiday)));
                }else{
                    holiday = getHolidayByDate(holidayDTO.getHoliday());
                    Holiday newHolidayDetail = getHolidayEntity(holidayDTO);
                    holiday.setReason(newHolidayDetail.getReason());
                    holiday.setCommonStatus(newHolidayDetail.getCommonStatus());
                    holiday.setHoliday(newHolidayDetail.getHoliday());
                    newHolidayDetail = holidayRepository.save(holiday);
                    commonResponse.setStatus(true);
                    commonResponse.setPayload(Collections.singletonList(getHolidayDTO(newHolidayDetail)));
                }
            }else
                commonResponse.setErrorMessages(validateList);

        }catch (Exception e){ logger.warn("/******** Exception in HolidayService -> saveUpdate() :" + e);
        }
        return commonResponse;
    }

    /**
     * get holiday using date
     * @param date
     * @return CommonResponse
     */
    @Transactional
    public CommonResponse findByDate(String date) {
        CommonResponse commonResponse = new CommonResponse();
        Holiday holiday;
        try{
            holiday = this.getHolidayByDate(date);
            commonResponse.setStatus(true);
            commonResponse.setPayload(new ArrayList<>(Collections.singletonList(getHolidayDTO(holiday))));
        }catch (Exception ex){ logger.warn("/******** Exception in HolidayService -> findByDate() :" + ex);
        }
        return commonResponse;
    }

    /**
     * delete holiday using date
     * @param date
     * @return CommonResponse
     */
    @Transactional
    public CommonResponse deleteByDate(String date) {
        CommonResponse commonResponse = new CommonResponse();
        Holiday holiday;
        try {
            holiday = this.getHolidayByDate(date);
            holiday.setCommonStatus(CommonStatus.DELETED);
            holidayRepository.save(holiday);
            commonResponse.setStatus(true);
        }catch (Exception ex){logger.warn("/******** Exception in HolidayService -> deleteByDate() :" + ex);
        }
        return commonResponse;
    }

    /**
     * find all
     * @return CommonResponse
     */
    @Transactional
    public CommonResponse findAll() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            Predicate<Holiday> filterOnStatus = holiday1 -> holiday1.getCommonStatus() != CommonStatus.DELETED;

            List<HolidayDTO> holidayDTOS = holidayRepository.findAll()
                    .stream()
                    .filter(filterOnStatus)
                    .map(this::getHolidayDTO)
                    .collect(Collectors.toList());
            commonResponse.setPayload(new ArrayList<>(Collections.singletonList(holidayDTOS)));
            commonResponse.setStatus(true);
        }catch (Exception e){logger.warn("/******** Exception in holidayService -> findAll() :" + e);
        }
        return commonResponse;
    }

    /**
     * validate holidayDTO entity
     * @param holidayDTO
     * @return
     */
    @Transactional
    public List<String> validateHoliday(HolidayDTO holidayDTO){
        List<String> validateHoliday = new ArrayList<>();
        if (CommonValidation.stringNullValidation(holidayDTO.getReason()))
            validateHoliday.add(CommonMsg.ENTER_REASON);
        else if (CommonValidation.stringNullValidation(holidayDTO.getHoliday()))
            validateHoliday.add(CommonMsg.ENTER_DATE);
        else if (holidayDTO.getCommonStatus() == null){
            validateHoliday.add(CommonMsg.ENTER_STATUS);
        }
        return validateHoliday;
    }

    /**
     * get holiday by using date
     * @param date
     * @return
     */
    @Transactional
    public Holiday getHolidayByDate(String date){
       return holidayRepository.findByHoliday(DateTimeUtil.getFormattedDate(date));
    }

    /**
     * convert holidayDTO to HolidayEntity
     * @param holidayDTO
     * @return
     */
    @Transactional
    public Holiday getHolidayEntity(HolidayDTO holidayDTO){
        return new Holiday(
                holidayDTO.getId() == null ? null : Long.parseLong(holidayDTO.getId()),
                holidayDTO.getReason(),
                DateTimeUtil.getFormattedDate(holidayDTO.getHoliday()),
                holidayDTO.getCommonStatus()
        );
    }

    /**
     * convert holiday entity to holidayDTO
     * @param holiday
     * @return
     */
    public HolidayDTO getHolidayDTO(Holiday holiday){
        return new HolidayDTO(
                holiday.getId().toString(),
                holiday.getReason(),
                holiday.getHoliday().toString(),
                holiday.getCommonStatus()
        );
    }
}
