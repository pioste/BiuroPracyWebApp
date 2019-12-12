package com.biuropracy.demo.controller;

import com.biuropracy.demo.DTO.JobOfferDTO;
import com.biuropracy.demo.model.JobOffer;
import com.biuropracy.demo.model.User;
import com.biuropracy.demo.repository.JobOfferRepository;
import com.biuropracy.demo.service.JobOfferService;
import com.biuropracy.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class JobOfferController {

    @Autowired
    JobOfferService jobOfferService;

    @Autowired
    UserService userService;

    @Autowired
    private JobOfferRepository jobOfferRepository;

    // dla wszystkich
    @GetMapping("/all/jobOffers")
    public String getAllJobOffers(Model model, String title, String location, String category, String contractType, String workingTime, String positionLevel) {
        List<JobOfferDTO> JobList = jobOfferRepository.getJobOfferFiltered(title, category, location, contractType, workingTime, positionLevel);
        model.addAttribute("jobOffers", JobList);
        return "/all/jobOffers/viewJobs";
    }

    @RequestMapping(path = "/user/jobOffers/delete/{id}")
    public String deleteJobOfferById(Model model, @PathVariable("id") Integer id) throws RuntimeException {
        jobOfferService.deleteJobOfferById(id);
        return "redirect:/user/jobOffers";
    }


    //dla użytkownika

    @RequestMapping(path = {"/user/jobOffers"}, method = RequestMethod.GET)
    public String getAllJobOffersUser(Model model) {
        List<JobOffer> List = jobOfferService.getAllJobOffers();
        model.addAttribute("jobOffers", List);
        return "/all/jobOffers/viewJobsLoginUser";
    }


    @GetMapping(path = {"/all/jobOffers/editOffer", "/all/jobOffers/editOffer/{id}"})
    public String editJobOffer(Model model, @PathVariable("id") Optional<Integer> id) {
        JobOffer jobOffer = jobOfferService.getJobOfferById(id.get());
        model.addAttribute("jobOffer", jobOffer);
        return "/all/jobOffers/edit-jobOffer";
    }

    @GetMapping(path = {"/all/jobOffers/viewSelectedJobOffer", "/all/jobOffers/viewSelectedJobOffer/{id}"})
    public String viewSelected(Model model,@PathVariable("id") Optional<Integer> id) {
        List<JobOfferDTO> JobOfferList = jobOfferRepository.getSelectedJobOffer(id.get());
        model.addAttribute("jobOffers", JobOfferList);
        return "/all/jobOffers/selectedJobOffer";
    }

    @PostMapping(path = "/user/jobOffers/updateJobOfferPost")
    public String updateJobOffer(@Valid JobOffer jobOffer, BindingResult bindingResult, ModelMap modelMap){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Popraw błędy w formularzu");
            modelMap.addAttribute("bindingResult", bindingResult);
        } else {
            jobOfferService.updateJobffer(jobOffer);
        }
        return "redirect:/user/jobOffers";
    }

    @GetMapping(path = "/user/jobOffers/createNew")
    public String addNewJobOffer(Model model){
        model.addAttribute("jobOffer", new JobOffer());
        return "/all/jobOffers/add-jobOffer";
    }

    @PostMapping(path = "/user/jobOffers/createJobOfferPost")
    public String createJobOffer(@Valid JobOffer jobOffer, BindingResult bindingResult, ModelMap modelMap){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Popraw błędy w formularzu");
            modelMap.addAttribute("bindingResult", bindingResult);
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            jobOfferService.createJobOffer(jobOffer, userService.findUserByEmail(authentication.getName()));
            modelAndView.addObject("successMessage", "Ogłoszenie zostało pomyślnie dodane.");
        }
        modelAndView.addObject("jobOffer", new JobOffer());
        return "/all/jobOffers/add-jobOffer";
    }
}