package Castorus.skyscube.me.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class PostController {
    private static final String DISCORD_WEBHOOK_URL = "https://discord.com/api/webhooks/1367115535367540819/BUy-KXCHMfxKrytX3KMhfqffSR1MNRq-7nhmrNEXFX2zC42uwOxrRSycR161rYN_fAyo";

    @PostMapping("/send")
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
}
