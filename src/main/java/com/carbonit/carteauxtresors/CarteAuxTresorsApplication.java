package com.carbonit.carteauxtresors;

import com.carbonit.carteauxtresors.dto.AdventureDTO;
import com.carbonit.carteauxtresors.entity.Adventurer;
import com.carbonit.carteauxtresors.entity.Position;
import com.carbonit.carteauxtresors.entity.Treasure;
import com.carbonit.carteauxtresors.enums.Action;
import com.carbonit.carteauxtresors.enums.Orientation;
import com.carbonit.carteauxtresors.mapper.AdventureDTOMapper;
import com.carbonit.carteauxtresors.service.ActionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.util.Pair;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.util.EnumUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Long.parseLong;

@Slf4j
@AllArgsConstructor
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class CarteAuxTresorsApplication {

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
		if(file.canRead()) {
			try(BufferedReader br = new BufferedReader(new FileReader(file))) {
				for(String line; (line = br.readLine()) != null; ) {
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

		log.info("New adventure ready to begin {}", adventureDTO);
		AdventureDTO finishedAdventure = actionService.handleAdventurersTurns(adventureDTO);
		log.info("Adventure has finished {}", finishedAdventure);
	}
}
