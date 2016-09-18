package au.com.riosoftware.firstapp;

import au.com.riosoftware.firstapp.domain.Post;
import au.com.riosoftware.firstapp.domain.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private PostRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... arg0) throws Exception {
		for (int i = 0; i < 5; i++) {
			repository.save(new Post("My post number #" + (i + 1)));
		}
	}

}
