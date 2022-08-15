package com.org.carder.controller.viewController;

import com.org.carder.DTOs.ComplainDTO;
import com.org.carder.DTOs.DepartmentDTO;
import com.org.carder.DTOs.UserDTO;
import com.org.carder.service.ComplainService;
import com.org.carder.service.DepartmentService;
import com.org.carder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class ViewController {

    private final DepartmentService departmentService;
    private final UserService userService;
    private final ComplainService complainService;

    @Autowired
    public ViewController(DepartmentService departmentService, UserService userService, ComplainService complainService) {
        this.departmentService = departmentService;
        this.userService = userService;
        this.complainService = complainService;
    }

    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/reg")
    public String register(Model model) {
        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        model.addAttribute("departments", departmentDTOS);
        return "register";
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/dash")
    public String dash(Model model) {
        model.addAttribute("userCount", String.valueOf(userService.getUserCount()));
        model.addAttribute("complainCount", String.valueOf(complainService.getComplainCount()));
        model.addAttribute("departmentCount", String.valueOf(departmentService.getDepartmentCount()));
        return "dashbord";
    }

    @GetMapping("/uReg")
    public String userReg(Model model) {
        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        model.addAttribute("departments", departmentDTOS);
        return "userRegistration";
    }

    @GetMapping("/dReg")
    public String depReg() {
        return "department";
    }

    @GetMapping({"/comReplay/{id}", "/comReplay"})
    public String comReplay(Model model,@PathVariable(required = false) String id) {
        if (!StringUtils.isEmpty(id)) {
            ComplainDTO complainDTO = complainService.getComplainById(id);
            complainService.updateComplainStatus(id);
            model.addAttribute("complainDTO", complainDTO);
        }
        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        model.addAttribute("departments", departmentDTOS);
        return "complainReply";
    }


    @GetMapping("/complain")
    public String complain(Model model) {
        List<ComplainDTO> complainDTOS = complainService.getAllComplain();
        model.addAttribute("complainDTOS" , complainDTOS);
        return "complain";
    }

    @GetMapping("/userCom")
    public String UserComplain( Model model) {
        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        model.addAttribute("departments", departmentDTOS);
        return "u_complain";
    }

    @GetMapping("/userViewCom/{id}")
    public String UserViewComplain(@PathVariable("id") String id,Model model) {
        List<ComplainDTO> complainDTOS = complainService.getComplainDTOS(id);
        model.addAttribute("complains",complainDTOS);
        return "u_viewComplain";
    }


    @GetMapping("/depView")
    public String depView(Model model) {
        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        model.addAttribute("departments", departmentDTOS);
        return "departmentView";
    }

    @GetMapping("/userView")
    public String userView(Model model) {
        List<UserDTO> userDTOS = userService.getAll();
        model.addAttribute("userDTOS", userDTOS);
        return "userView";
    }

    @GetMapping("/mComplain/{depTit}")
    public String mComplain(Model model,@PathVariable String depTit) {
        List<ComplainDTO> complainDTOS = complainService.getComplainByDepartment(depTit);
        model.addAttribute("complainDTOS" , complainDTOS);
        return "m_complain";
    }

    @GetMapping({"/mComReplay/{id}", "/mComReplay"})
    public String mComReplay(Model model,@PathVariable(required = false) String id) {
        if (!StringUtils.isEmpty(id)) {
            ComplainDTO complainDTO = complainService.getComplainById(id);
            complainService.updateComplainStatus(id);
            model.addAttribute("complainDTO", complainDTO);
        }
        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        model.addAttribute("departments", departmentDTOS);
        return "m_complainReply";
    }

    @GetMapping("/mUserReg")
    public String mUserReg(Model model) {
        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        model.addAttribute("departments", departmentDTOS);
        return "m_userRegistration";
    }

    @GetMapping("/mUserView")
    public String mUserView(Model model) {
        List<UserDTO> userDTOS = userService.getAll();
        model.addAttribute("userDTOS", userDTOS);
        return "m_userView";
    }

}
