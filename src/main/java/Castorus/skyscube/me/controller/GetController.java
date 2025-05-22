package Castorus.skyscube.me.controller;

import Castorus.skyscube.me.DBManager.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetController {
    @Autowired
    private TicketsService ticketsService;

    @GetMapping("/")
    public String home(ModelMap modelMap) {
        modelMap.addAttribute("table", ticketsService.getAllTicketss());
        return "index";
    }


}
