package Castorus.skyscube.me.DBManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketsService {
    @Autowired
    private TicketsRepository TicketsRepository;

    public List<Tickets> getAllTicketss() {
        return TicketsRepository.findAll();
    }

    public Tickets getTicketsById(Long id) {
        return TicketsRepository.findById(id).orElse(null);
    }

    public Tickets save(Tickets Tickets) {
        return TicketsRepository.save(Tickets);
    }
    public List<String> getAllDescription(Tickets Tickets) {
        try{
            return Files.readAllLines(Paths.get(Tickets.getDescription()));
        } catch (IOException e){
            e.printStackTrace();

        }
        return new ArrayList<>();
    }
}
