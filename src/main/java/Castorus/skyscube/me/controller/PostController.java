package Castorus.skyscube.me.controller;

import Castorus.skyscube.me.DBManager.Tickets;
import Castorus.skyscube.me.DBManager.TicketsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/api")
public class PostController {
    private static final String DISCORD_WEBHOOK_URL = "https://discord.com/api/webhooks/1375079496247677010/BShbatIHZmGCURg7OlJNFErlx6hcGdgyY4tphPNCzFv_CqgIZ76kZGcybtrkU_jBbz3s";
    private final TicketsService ticketsService;

    public PostController(TicketsService ticketsService) {
        this.ticketsService = ticketsService;
    }

    @PostMapping("/emotion/send")
    public String sendToDiscord(@RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        String content = String.format("""
                        Emotions reçus :
                        - Joie : %s
                        - Stress : %s
                        - Fatigue : %s
                        - Manque d'amour : %s
                        - Triste : %s
                        - Tracas : %s
                        - Message : %s""",
                params.get("joie"), params.get("stress"), params.get("fatigue"), params.get("amour"), params.get("triste"), params.get("tracas"),params.get("message"));

        Map<String, String> payload = Map.of("content", content);
        new RestTemplate().postForObject(DISCORD_WEBHOOK_URL, payload, String.class);

        redirectAttributes.addFlashAttribute("message", "Message envoyé à Discord !");
        return "redirect:/";
    }

    @PostMapping("/ticket/send/")
    public String ticket(@ModelAttribute Tickets ticket,@RequestParam("description") String description, RedirectAttributes redirectAttributes) {
        String resume = description.length() > 15 ? description.substring(0, 15) : description;
        String content = String.format("""
                Nouveau ticket !
                - Urgence : %s
                - Corp du ticket : %s
                """,ticket.getUrgence(),ticket.getResume());
        Map<String, String> payload = Map.of("content", content);
        new RestTemplate().postForObject(DISCORD_WEBHOOK_URL, payload, String.class);
        ticket.setState("Ouvert");

        if (Objects.equals(ticket.getUrgence(), "")){
            ticket.setUrgence("Faible");
        }
        ticket.setResume(resume);
        ticketsService.save(ticket);
        ticket.setDescription(description);
        redirectAttributes.addFlashAttribute("message", "Message envoyé à Discord !");
        return "redirect:/";

    }

    @PostMapping("/ticket/add/description")
    public String add_desc(@RequestParam("id") int id,@RequestParam("description") String description, RedirectAttributes redirectAttributes) {
        System.out.println(description);
        Tickets ticket = ticketsService.getTicketsById((long) id);
        ticket.setDescription(description);
        ticketsService.save(ticket);
        return "redirect:/ticket?id=" + ticket.getId();
    }

    @PostMapping("/ticket/update/state")
    public String update_state(@RequestParam("id") int id,@RequestParam("etat") String etat, RedirectAttributes redirectAttributes) {
        System.out.println("Nouvel état : " + etat);
        System.out.println("ID ticket : " + id);

        Tickets ticket = ticketsService.getTicketsById((long) id);
        ticket.setState(etat); // ou ticket.setEtat(state); selon ton attribut
        ticketsService.save(ticket);

        return "redirect:/ticket?id=" + id;
    }
}
