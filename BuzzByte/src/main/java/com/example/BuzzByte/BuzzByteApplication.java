package com.example.BuzzByte;

import model.Post;
import model.Tag;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import repository.PostCommentRepository;
import repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class BuzzByteApplication {

	public static void main(String[] args) {


		SpringApplication.run(BuzzByteApplication.class, args);
		@Autowired
		PostRepository postRepository;
		PostCommentRepository postCommentRepository;
		Tag tag = new Tag(1L, "tag");
		List<Tag> tags = new ArrayList<>();
		tags.add(tag);
		// random UUID
		UUID uuid = UUID.randomUUID();
		User user = new User(1L, "username", "email", "pwd", "sdf", uuid, "role");
		/*
		@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments;
    private Long likes = 0L;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
		 */
		Post post = new Post(1L, "title", "desc", "content", tags, user, "image", new ArrayList<>(), 0L, null);
		postRepository.save(post);

	}

	@Bean
	public CommandLineRunner demo(PostRepository postRepository, PostCommentRepository postCommentRepository) {
		return (args) -> {
			Tag tag = new Tag(1L, "tag");
			List<Tag> tags = new ArrayList<>();
			tags.add(tag);

			// Random UUID
			UUID uuid = UUID.randomUUID();
			User user = new User(1L, "username", "email", "pwd", "sdf", uuid, "role");

			Post post = new Post(1L, "title", "desc", "content", tags, user, "image", new ArrayList<>(), 0L, null);
			postRepository.save(post);

			// Verify that the post is saved
			System.out.println("Post saved with ID: " + post.getId());
		};
	}

}
