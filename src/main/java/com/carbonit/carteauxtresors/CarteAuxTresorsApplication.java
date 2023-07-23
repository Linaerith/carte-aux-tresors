package com.carbonit.carteauxtresors;

import com.carbonit.carteauxtresors.dto.AdventureDTO;
import com.carbonit.carteauxtresors.mapper.AdventureDTOMapper;
import com.carbonit.carteauxtresors.service.ActionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Slf4j
@AllArgsConstructor
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class CarteAuxTresorsApplication {

    private static final String ABSOLUTE_PATH = "/home/lindsay/Documents/";

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CarteAuxTresorsApplication.class, args);
        ActionService actionService = applicationContext.getBean(ActionService.class);

        log.info("********************************************************");
        log.info("*        Application carte aux trésors démarrée        *");
        log.info("********************************************************");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter file path for the game initialization :");
        String path = scanner.next();
        AdventureDTO adventureDTO = AdventureDTO.builder().build();
        File file = new File(path);
        if (file.canRead()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                for (String line; (line = br.readLine()) != null; ) {
                    if (!line.trim().startsWith("#")) {
                        log.info("Processing line {}", line);
                        List<String> infos = List.of(line.split(" - "));
                        adventureDTO = AdventureDTOMapper.mapAdventureDTO(adventureDTO, infos);
                    }
                }
            } catch (Exception e) {
                log.error("Error while processing the file : {}", e.getMessage());
                scanner.close();
            }
        }
        scanner.close();

        log.info("New adventure ready to begin {}", adventureDTO);
        AdventureDTO finishedAdventure = actionService.handleAdventurersTurns(adventureDTO);

        log.info("Adventure has finished {}", finishedAdventure);

        String absoluteFilePath = String.format("%sAdventureResults-%s.txt", ABSOLUTE_PATH, LocalDateTime.now());

		List<String> results = AdventureDTOMapper.mapAdventureDTOToString(finishedAdventure);
        try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(absoluteFilePath, true));
			results.forEach(result -> {
				try {
					writer.append(result);
					writer.append("\n");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
			writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
