package Castorus.skyscube.me.controller;

import Castorus.skyscube.me.DBManager.Tickets;
import Castorus.skyscube.me.DBManager.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GetController {
    @Autowired
    private TicketsService ticketsService;

    @GetMapping("/")
    public String home(ModelMap modelMap) {
        modelMap.addAttribute("table", ticketsService.getAllTicketss());
        modelMap.addAttribute("ticket", new Tickets());
        String id = String.valueOf(ticketsService.getAllTicketss().size() + 1);
        modelMap.addAttribute("id", id);
        return "index";
    }

    @GetMapping("/ticket")
    public String ticket(ModelMap modelMap, @RequestParam("id") int id) {
        modelMap.addAttribute("ticket", ticketsService.getTicketsById((long) id));
        modelMap.addAttribute("description", ticketsService.getAllDescription(ticketsService.getTicketsById((long) id)));
        return "ticket";
    }


}
