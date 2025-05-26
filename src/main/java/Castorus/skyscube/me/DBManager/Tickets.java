package Castorus.skyscube.me.DBManager;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Generated;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resume;
    private String description;
    private String state;
    private String urgence;

    private static final String LOG_DIR = "tickets/ticket";

    public void setDescription(String description) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            description =  timestamp + " : " + description + "\n";
            String path = LOG_DIR+this.id+".log";
            Files.write(Paths.get(path), description.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            if (this.description == null || !this.description.equals(path)) {
                this.description = path;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getDescription() {return LOG_DIR+this.id+".log";}
}
