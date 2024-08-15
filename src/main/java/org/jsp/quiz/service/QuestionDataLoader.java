package org.jsp.quiz.service;
import org.slf4j.Logger;
import org.jsp.quiz.entity.Question;
import org.jsp.quiz.repository.QuestionRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Component
public class QuestionDataLoader implements CommandLineRunner {

    @Autowired
    private QuestionRepository questionRepository;
    
    private final Logger logger = LoggerFactory.getLogger(QuestionDataLoader.class);

    @Override
    public void run(String... args) throws Exception {
    	
        List<Question> questions;
		try {
			Optional<Question> isExists = questionRepository.findById(1000L);
			if(isExists.isEmpty()) {
				ObjectMapper mapper = new ObjectMapper();
				questions = mapper.readValue(new File("questions.json"),
						mapper.getTypeFactory().constructCollectionType(List.class, Question.class));
				questionRepository.saveAll(questions);
			}
		} catch (Exception e) {
			logger.error("Error : {}", e);
		}

    }
}
