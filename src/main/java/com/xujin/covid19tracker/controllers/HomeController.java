package com.xujin.covid19tracker.controllers;

import com.xujin.covid19tracker.services.COVIDDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {


    COVIDDataService covidDataService;

    @Autowired
    public HomeController(COVIDDataService covidDataService) {
        this.covidDataService = covidDataService;
    }

    @GetMapping("/home")
    public String home(Model model){

        model.addAttribute("locationStats",covidDataService.getData());
        model.addAttribute("totalReportedCases",covidDataService.getData()
                .stream()
                .mapToInt(num -> num.getLatestTotalCases())
                .sum());
        model.addAttribute("totalNewCases",covidDataService.getData()
                .stream()
                .mapToInt(num -> num.getDiffFromPrevDay())
                .sum());
        return "home";
    }
}
