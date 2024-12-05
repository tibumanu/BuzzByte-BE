package com.example.BuzzByte;

import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.Role;
import com.example.BuzzByte.model.Tag;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.model.comments.PostComment;
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

	@Autowired
	private PostCommentRepository postCommentRepository;

	public static void main(String[] args) {
		SpringApplication.run(BuzzByteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		setup();
		//demoOnCleanDb();
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
		demoOnCleanDb();
	}

	public void demoOnCleanDb() {
		// if db is not empty, this will not run

		if(!userRepository.findAll().isEmpty() || !postRepository.findAll().isEmpty()){
			return;
		}

		User user = new User();
		user.setUsername("notch");
		user.setEmail("notch@gmail.com");
		user.setHashedPassword("hashedPassword");
		user.setRole(Role.BASIC);
		user.setUniqueKey(UUID.randomUUID());
		user = userRepository.save(user);
		var userId = user.getId();


		Post post = new Post();
		post.setTitle("Need help with Java rendering");
		post.setContent("Essentially, we have blocks that we want to render in 3D. We have the coordinates of the blocks and we want to render them in 3D. " +
				"We are using Java for this project. This is my rendering approach so far: I shoot rays out of my eyes like I'm Superman. Blah. Blah java something blah Blah. " +
				"Blah blah blah, blah blah blah. It doesn't work and the cubes look mushed together. Blah. Blah java something blah Blah. Blah blah blah, blah blah blah. Blah. Blah java something blah Blah. " +
				"Blah blah blah, blah blah blah. Blah. Blah java something blah Blah. Blah blah blah, blah blah blah. Blah. Blah java something blah Blah. " +
				"Blah blah blah, blah blah blah. Blah. Blah java something blah Blah. Blah blah blah, blah blah blah. Blah. Blah java something blah " +
				"Blah. Blah blah blah, blah blah blah. Blah. Blah java something blah Blah.");
		post.setUser(user);
		post.setLikes(14L);

		Tag tag1 = tagRepository.findByName("Java").get();
		post.setTags(new ArrayList<>(List.of(tag1)));
		// save post after setting tags, since PostComment needs this post to exist
		postRepository.save(post);

		// PostComment
		User userComment = new User();
		userComment.setUsername("steve");
		userComment.setEmail("steve@cube.com");
		userComment.setHashedPassword("hashedPassword");
		userComment.setRole(Role.BASIC);
		userComment.setUniqueKey(UUID.randomUUID());
		userComment = userRepository.save(userComment);
		var userCommentId = userComment.getId();
		PostComment postComment = new PostComment();
		postComment.setContent("I can help you with that. I have experience with Java rendering and I think I had the exact same issue." +
				"The issue is that your rendering does not check for overlapping blocks. You need to check for overlapping blocks and render them accordingly.");
		postComment.setPost(post);
		postComment.setUserId(userCommentId);
		postComment = postCommentRepository.save(postComment);

		User userComment2 = new User();
		userComment2.setUsername("alex");
		userComment2.setEmail("alex@cube.com");
		userComment2.setHashedPassword("hashedPassword");
		userComment2.setRole(Role.BASIC);
		userComment2.setUniqueKey(UUID.randomUUID());
		userComment2 = userRepository.save(userComment2);
		var userCommentId2 = userComment2.getId();
		PostComment postComment2 = new PostComment();
		postComment2.setContent("what a noob. your code makes no sense. its obvious that u r using ClosedAI to generate this code. " +
				"i can tell by the way you write. you should be ashamed of yourself. you are a disgrace to the programming community.");
		postComment2.setPost(post);
		postComment2.setUserId(userCommentId2);
		postComment2 = postCommentRepository.save(postComment2);

		post.setComments(new ArrayList<>(List.of(postComment, postComment2)));
		post = postRepository.save(post);


		// Some other post
		user = new User();
		user.setUsername("jeff_da_basilisk1");
		user.setEmail("jeff1@amazon.com");
		user.setHashedPassword("hashedPassword");
		user.setRole(Role.BASIC);
		user.setUniqueKey(UUID.randomUUID());
		user = userRepository.save(user);


		post = new Post();
		post.setTitle("Why is AWS so good? I'm in love with it");
		post.setContent("I love AWS more than my wife. I love AWS more than my kids. I love AWS more than my dog. I love AWS more than my cat.\n" +
				"I can't believe such companies exist. Amazon is the best. AWS is the best. Jeff Bezos is the best.\n" +
				"Also, he's not bald. He's just aerodynamic. He's not bald. He's just aerodynamic. He's not bald. He's just aerodynamic. ");
		post.setUser(user);
		post.setLikes(-8L);

		tag1 = tagRepository.findByName("Java").get();
		Tag tag2 = tagRepository.findByName("Docker").get();
		Tag tag3 = tagRepository.findByName("Kubernetes").get();
		post.setTags(new ArrayList<>(List.of(tag1, tag2, tag3)));
		// save post after setting tags, since PostComment needs this post to exist
		postRepository.save(post);

		// PostComment for this
		userComment = new User();
		userComment.setUsername("elon");
		userComment.setEmail("musk@x.com");
		userComment.setHashedPassword("hashedPassword");
		userComment.setRole(Role.BASIC);
		userComment.setUniqueKey(UUID.randomUUID());
		userComment = userRepository.save(userComment);
		userCommentId = userComment.getId();

		postComment = new PostComment();
		postComment.setContent("I think you are a bit too obsessed with AWS. I think you should take a break and go outside. " +
				"Maybe take a walk in the park. Maybe go to the beach. Maybe go to the mountains. Maybe go to the desert. " +
				"Maybe go to the moon. Maybe go to Mars. Maybe go to the sun. I could help with that.");
		postComment.setPost(post);
		postComment.setUserId(userCommentId);
		postComment = postCommentRepository.save(postComment);
		post.setComments(new ArrayList<>(List.of(postComment)));
		post = postRepository.save(post);


		// Some other post
		user = new User();
		user.setUsername("gatesBillyBill");
		user.setEmail("bill@gates.com");
		user.setHashedPassword("hashedPassword");
		user.setRole(Role.BASIC);
		user.setUniqueKey(UUID.randomUUID());
		user = userRepository.save(user);


		post = new Post();
		post.setTitle("Microsoft's overall brilliance - am I right?");
		post.setContent("i can't believe it. the new windows update" +
				"is phenomenal. it's the best thing since sliced bread. it's the best thing since the wheel. it's the best thing since the internet. \n" +
				"i love how the new windows update is so user-friendly. it's so easy to use. it's so easy to install. it's so easy to uninstall. \n" +
				"the AI feature being added to windows is so cool. it's so smart. it's so intelligent. it's so intuitive. it's so helpful. \n" +
				"it's also so secure and private. yeah sure it's installed a keylogger on your system but it's for your own good.");
		post.setUser(user);
		post.setLikes(17L);

		tag1 = tagRepository.findByName("Azure").get();
		tag2 = tagRepository.findByName("Business Intelligence").get();
		tag3 = tagRepository.findByName("Predictive Analytics").get();
		Tag tag4 = tagRepository.findByName("Natural Language Processing (NLP)").get();
		post.setTags(new ArrayList<>(List.of(tag1, tag2, tag3, tag4)));
		// save post after setting tags, since PostComment needs this post to exist
		postRepository.save(post);

		// PostComment for this
		userComment = new User();
		userComment.setUsername("linus");
		userComment.setEmail("linus@linux.com");
		userComment.setHashedPassword("hashedPassword");
		userComment.setRole(Role.BASIC);
		userComment.setUniqueKey(UUID.randomUUID());
		userComment = userRepository.save(userComment);
		userCommentId = userComment.getId();

		postComment = new PostComment();
		postComment.setContent("another fanboy and bootlicker.\n" +
				"we all know that windows is a piece of garbage. it's *traaaash*. \n" +
				"it's only good cuz my grandma can use it. the sheeple love it. \n" +
				"i'm a linux user and i'm better than you. that's just how it is. \n");
		postComment.setPost(post);
		postComment.setUserId(userCommentId);
		postComment = postCommentRepository.save(postComment);
		post.setComments(new ArrayList<>(List.of(postComment)));
		post = postRepository.save(post);


		// other post
		user = new User();
		user.setUsername("zuck_the_duck");
		user.setEmail("zuck@meta.com");
		user.setHashedPassword("hashedPassword");
		user.setRole(Role.BASIC);
		user.setUniqueKey(UUID.randomUUID());
		user = userRepository.save(user);


		post = new Post();
		post.setTitle("Why Meta Should Monitor Your Every Move");
		post.setContent("Let's face it, Meta knowing everything about you is a good thing. Imagine a world where every ad you see is exactly what you need, " +
				"even before you know you need it! Who cares about privacy when you can have convenience? " +
				"After all, if you have nothing to hide, what's the problem?");
		post.setUser(user);
		post.setLikes(3L);

		tag1 = tagRepository.findByName("Machine Learning").get();
		tag2 = tagRepository.findByName("Data Science").get();
		post.setTags(new ArrayList<>(List.of(tag1, tag2)));
		// save post after setting tags, since PostComment needs this post to exist
		postRepository.save(post);

		// PostComment for this
		userComment = new User();
		userComment.setUsername("meta_fanboy");
		userComment.setEmail("fanboy@meta.com");
		userComment.setHashedPassword("hashedPassword");
		userComment.setRole(Role.BASIC);
		userComment.setUniqueKey(UUID.randomUUID());
		userComment = userRepository.save(userComment);
		userCommentId = userComment.getId();

		postComment = new PostComment();
		postComment.setContent("Absolutely agree! I can't wait for the day Meta controls everything in my life. It's the future we deserve!");
		postComment.setPost(post);
		postComment.setUserId(userCommentId);
		postComment = postCommentRepository.save(postComment);

		userComment2 = new User();
		userComment2.setUsername("privacy_warrior");
		userComment2.setEmail("privacy@nowhere.com");
		userComment2.setHashedPassword("hashedPassword");
		userComment2.setRole(Role.BASIC);
		userComment2.setUniqueKey(UUID.randomUUID());
		userComment2 = userRepository.save(userComment2);
		userCommentId2 = userComment2.getId();

		postComment2 = new PostComment();
		postComment2.setContent("this is insane. dystopian nightmare. you guys are crazy. i'm out of here. hope u poop ur pants.");
		postComment2.setPost(post);
		postComment2.setUserId(userCommentId2);
		postComment2 = postCommentRepository.save(postComment2);

		post.setComments(new ArrayList<>(List.of(postComment, postComment2)));
		post = postRepository.save(post);

		// other post
		user = new User();
		user.setUsername("tim_cook");
		user.setEmail("tim@apple.com");
		user.setHashedPassword("hashedPassword");
		user.setRole(Role.BASIC);
		user.setUniqueKey(UUID.randomUUID());
		user = userRepository.save(user);



		post = new Post();
		post.setTitle("Why Being Late to the Party is Apple's Secret Sauce");
		post.setContent("Apple doesn't rush into new features like the other companies. We wait, observe, and then perfect. " +
				"Why introduce a feature early when you can wait and make it 'magical' later? Our users love it because it just works, eventually.");
		post.setUser(user);
		post.setLikes(23L);

		tag1 = tagRepository.findByName("Business Intelligence").get();
		post.setTags(new ArrayList<>(List.of(tag1)));
		// save post after setting tags, since PostComment needs this post to exist
		postRepository.save(post);

		// PostComment for this
		userComment = new User();
		userComment.setUsername("apple_fan");
		userComment.setEmail("fan@apple.com");
		userComment.setHashedPassword("hashedPassword");
		userComment.setRole(Role.BASIC);
		userComment.setUniqueKey(UUID.randomUUID());
		userComment = userRepository.save(userComment);
		userCommentId = userComment.getId();

		postComment = new PostComment();
		postComment.setContent("Exactly! I don't mind waiting for years. When Apple does it, it's always worth it. Can't wait for the Apple car in 2050!");
		postComment.setPost(post);
		postComment.setUserId(userCommentId);
		postComment = postCommentRepository.save(postComment);

		userComment2 = new User();
		userComment2.setUsername("android_advocate");
		userComment2.setEmail("android@open.com");
		userComment2.setHashedPassword("hashedPassword");
		userComment2.setRole(Role.BASIC);
		userComment2.setUniqueKey(UUID.randomUUID());
		userComment2 = userRepository.save(userComment2);
		userCommentId2 = userComment2.getId();

		postComment2 = new PostComment();
		postComment2.setContent("You realize Android had these features years ago, right? Enjoy your overpriced delays lol.");
		postComment2.setPost(post);
		postComment2.setUserId(userCommentId2);
		postComment2 = postCommentRepository.save(postComment2);

		post.setComments(new ArrayList<>(List.of(postComment, postComment2)));
		post = postRepository.save(post);


		// other post
		user = new User();
		user.setUsername("jeff_da_basilisk2");
		user.setEmail("jeff2@amazon.com");
		user.setHashedPassword("hashedPassword");
		user.setRole(Role.BASIC);
		user.setUniqueKey(UUID.randomUUID());
		user = userRepository.save(user);



		post = new Post();
		post.setTitle("Why Amazon Drones Should Deliver Everything, Even Your Kids");
		post.setContent("Imagine a world where Amazon drones deliver everything. Groceries, gadgets, and even your kids to school! " +
				"Think about the efficiency, the speed, the convenience. Why drive when a drone can just drop your kid off at the schoolyard?");
		post.setUser(user);
		post.setLikes(2L);

		tag1 = tagRepository.findByName("Business Intelligence").get();
		post.setTags(new ArrayList<>(List.of(tag1)));
		// save post after setting tags, since PostComment needs this post to exist
		postRepository.save(post);

		// PostComment for this
		userComment = new User();
		userComment.setUsername("drone_fanatic");
		userComment.setEmail("fanatic@drones.com");
		userComment.setHashedPassword("hashedPassword");
		userComment.setRole(Role.BASIC);
		userComment.setUniqueKey(UUID.randomUUID());
		userComment = userRepository.save(userComment);
		userCommentId = userComment.getId();

		postComment = new PostComment();
		postComment.setContent("Yes! Let's drone-ify everything! I can't wait to see drones flying everywhere, delivering my pizza and my kids!");
		postComment.setPost(post);
		postComment.setUserId(userCommentId);
		postComment = postCommentRepository.save(postComment);

		userComment2 = new User();
		userComment2.setUsername("sane_human");
		userComment2.setEmail("sane@earth.com");
		userComment2.setHashedPassword("hashedPassword");
		userComment2.setRole(Role.BASIC);
		userComment2.setUniqueKey(UUID.randomUUID());
		userComment2 = userRepository.save(userComment2);
		userCommentId2 = userComment2.getId();

		postComment2 = new PostComment();
		postComment2.setContent("sigh. i'm disappointed that internet is so cheap that ppl like u have it. deliver kids? really? inb4 u want robots to raise them too.\n" +
				"oh wait u probably do.");
		postComment2.setPost(post);
		postComment2.setUserId(userCommentId2);
		postComment2 = postCommentRepository.save(postComment2);

		post.setComments(new ArrayList<>(List.of(postComment, postComment2)));
		post = postRepository.save(post);



	}
}