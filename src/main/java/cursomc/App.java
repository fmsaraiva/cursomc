package cursomc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	//M�todo usuado para instanciar classes quando a aplica��o inicializa (CommandLineRunner)
	public void run(String... args) throws Exception {						
	}

}
