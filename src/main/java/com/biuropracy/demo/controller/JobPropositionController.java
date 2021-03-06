package com.biuropracy.demo.controller;

import com.biuropracy.demo.DTO.JobPropositionDTO;
import com.biuropracy.demo.model.Employer;
import com.biuropracy.demo.model.JobProposition;
import com.biuropracy.demo.model.User;
import com.biuropracy.demo.repository.JobPropositionRepository;
import com.biuropracy.demo.service.EmployerService;
import com.biuropracy.demo.service.JobPropositionService;
import com.biuropracy.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class JobPropositionController {

    private Integer Userid;

    @Autowired
    JobPropositionService jobPropositionService;

    @Autowired
    UserService userService;

    @Autowired
    EmployerService employerService;

    @Autowired
    private JobPropositionRepository jobPropositionRepository;

    /**
     * tworzenie nowej propozycji pracy
     * @param modelAndView
     * @param jobProposition
     * @param id
     * @return
     */
    @PostMapping(path = {"/employer/userProfile/createJobProposition", "/employer/userProfile/createJobProposition/{id}"})
    public String createJobProposition(ModelAndView modelAndView, JobProposition jobProposition, @PathVariable("id") Integer id){
        User toUserId = userService.findUser(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findUserByEmail(userDetails.getUsername());
        Integer idUser = user.getIdUser();
        jobPropositionService.createJobProposition(jobProposition, toUserId, employerService.findEmployerByUser_id(idUser));
        modelAndView.addObject("jobProposition", new JobProposition());
        return "redirect:/employer/viewSelectedProfile/"+id;
    }

    /**
     * wyświetlenie otrzymanych ofert pracy
     * @param model
     * @param decision
     * @return
     */
    @GetMapping(path = "/user/getAllJobPropUser")
    public String getAllJPropByToUser(Model model, String decision){
        model.addAttribute("jobProposition", new JobProposition()); //do edycji w modalu
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findUserByEmail(userDetails.getUsername());
        Integer id = user.getIdUser();
        List<JobPropositionDTO> JobPropDto = jobPropositionRepository.getAllJPropByToUserId(id, decision);
        model.addAttribute("jobPropositions", JobPropDto);
        return "/all/jobProposition/allJobPropUser";
    }

    /**
     * wyświetlenie wysłanych ofert pracy
     * @param model
     * @param decision
     * @return
     */
    @GetMapping(path = "/employer/getAllJobPropEmployer")
    public String getAllJPropByFromUser(Model model, String decision){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findUserByEmail(userDetails.getUsername());
        Integer id = user.getIdUser();
        Employer employer = employerService.findEmployerByUser_id(id);
        List<JobPropositionDTO> JobPropDto = jobPropositionRepository.getAllJObPropEmployer(employer.getIdEmployer(), decision);
        model.addAttribute("jobPropositions", JobPropDto);
        return "/all/jobProposition/allJobPropEmployer";
    }

    /**
     * zmiana decyzji oferty pracy
     * @param jobProposition
     * @return
     */
    @PostMapping(path = "/user/JobProp/changeDecision")
    public String changeDecision(JobProposition jobProposition){
        jobPropositionService.updateJobProposition(jobProposition);
        return "redirect:/user/getAllJobPropUser";
    }

    /**
     * usuwanie propozycji pracy
     * @param id
     * @return
     */
    @GetMapping(path = "/employer/jobPropositionDelete")
    public String jobPropositionDelete(@RequestParam("id") Integer id){
        jobPropositionService.deleteJobProposition(id);
        return "redirect:/employer/getAllJobPropEmployer";
    }

    //admin

    /**
     * wyświetlenie propozycji pracy otrzymanych
     * @param model
     * @param id
     * @param decision
     * @return
     */
    @GetMapping(path = "/admin/getAllUserReceivedJProp/{id}")
    public String getAllUserReceivedJProp(Model model, @PathVariable("id") Integer id, String decision){
        Userid = id;
        model.addAttribute("currentId", id);
        model.addAttribute("jobProposition", new JobProposition());
        List<JobPropositionDTO> JobPropDto = jobPropositionRepository.getAllJPropByToUserId(id, decision);
        model.addAttribute("jobPropositions", JobPropDto);
        return "/all/jobProposition/userReceivedJobPropAdmin";
    }

    /**
     * edytowanie propozycji pracy przez admina
     * @param jobProposition
     * @return
     */
    @PostMapping(path = "/admin/getAllUserReceivedJProp/update")
    public String updateReceivedUserJobProp(JobProposition jobProposition){
        jobPropositionService.updateJobPropositionAdmin(jobProposition);
        return "redirect:/admin/getAllUserReceivedJProp/"+Userid;
    }

    /**
     * usuwanie propozycji pracy przez admina
     * @param id
     * @return
     */
    @GetMapping(path = "/admin/receivedJobPropDelete")
    public String userReceivedJobPropDelete(@RequestParam("id") Integer id){
        jobPropositionService.deleteJobProposition(id);
        return "redirect:/admin/getAllUserReceivedJProp/"+Userid;
    }
}
