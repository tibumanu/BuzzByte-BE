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
		demo();
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