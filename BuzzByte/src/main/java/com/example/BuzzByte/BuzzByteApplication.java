package com.example.BuzzByte;

import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.Role;
import com.example.BuzzByte.model.Tag;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.repository.*;
import com.example.BuzzByte.security.config.RsaKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BuzzByteApplication implements CommandLineRunner {
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TagRepository tagRepository;

	public static void main(String[] args) {
		SpringApplication.run(BuzzByteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		setup();
	}

	public void addTagsInDB(){
		/*
		const categories = [
        "JavaScript", "Python", "Java", "C#", "C++",
        "Ruby", "Go", "Rust", "TypeScript", "PHP",
        "Swift", "Kotlin", "Dart", "HTML & CSS",
        "React", "Angular", "Vue.js", "Svelte", "Ember.js",
        "Node.js", "Express.js", "Laravel", "Django", "Flask",
        "Spring Boot", "ASP.NET",
        "SQL", "NoSQL", "MongoDB", "PostgreSQL", "MySQL",
        "Firebase", "GraphQL",
        "AWS (Amazon Web Services)", "Google Cloud", "Azure",
        "Docker", "Kubernetes", "Serverless Architecture",
        "Continuous Integration (CI/CD)", "DevOps",
        "Progressive Web Apps (PWAs)", "REST APIs",
        "Microservices", "HTTP/HTTPS", "WebAssembly",
        "Agile Methodology", "Scrum", "Kanban",
        "Test-Driven Development (TDD)", "Pair Programming",
        "Machine Learning", "Deep Learning", "Neural Networks",
        "Natural Language Processing (NLP)", "Computer Vision",
        "Reinforcement Learning",
        "Data Science", "Data Visualization", "Big Data",
        "Business Intelligence", "Predictive Analytics"
    ];
		 */
		// add the above only if they don't exist
		List<String> tags = Arrays.asList("JavaScript", "Python", "Java", "C#", "C++",
				"Ruby", "Go", "Rust", "TypeScript", "PHP",
				"Swift", "Kotlin", "Dart", "HTML & CSS",
				"React", "Angular", "Vue.js", "Svelte", "Ember.js",
				"Node.js", "Express.js", "Laravel", "Django", "Flask",
				"Spring Boot", "ASP.NET",
				"SQL", "NoSQL", "MongoDB", "PostgreSQL", "MySQL",
				"Firebase", "GraphQL",
				"AWS (Amazon Web Services)", "Google Cloud", "Azure",
				"Docker", "Kubernetes", "Serverless Architecture",
				"Continuous Integration (CI/CD)", "DevOps",
				"Progressive Web Apps (PWAs)", "REST APIs",
				"Microservices", "HTTP/HTTPS", "WebAssembly",
				"Agile Methodology", "Scrum", "Kanban",
				"Test-Driven Development (TDD)", "Pair Programming",
				"Machine Learning", "Deep Learning", "Neural Networks",
				"Natural Language Processing (NLP)", "Computer Vision",
				"Reinforcement Learning",
				"Data Science", "Data Visualization", "Big Data",
				"Business Intelligence", "Predictive Analytics");
		for (String string : tags) {
			Tag tag = new Tag();
			tag.setName(string);
			if(tagRepository.findByName(string).isEmpty()){
				tagRepository.save(tag);
			}
		}
	}

	public void setup(){
		addTagsInDB();
	}

	public void demo() {
		// Ensure User entity with id 1 exists
		User user = new User();
		user.setUsername("username");
		user.setEmail("email@example.com");
		user.setHashedPassword("pwd");
		user.setRole(Role.ADMIN);
		user.setUniqueKey(UUID.randomUUID());
		user = userRepository.save(user);

		Tag tag = new Tag();
		tag.setName("tagNew2");
		List<Tag> tags = new ArrayList<>();
		tags.add(tag);
		tagRepository.save(tag);


		Post post = new Post();
		post.setTitle("titleNew");
		post.setDescription("desc");
		post.setContent("content");
		post.setTags(tags);
		post.setUser(user);
		post.setImage("image");
		post.setComments(new ArrayList<>());
		post.setLikes(0L);
		postRepository.save(post);

		System.out.println("Post and User have been saved successfully.");
	}
}