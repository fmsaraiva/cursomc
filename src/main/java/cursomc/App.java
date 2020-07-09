package cursomc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	//Método usuado para instanciar classes quando a aplicação inicializa (CommandLineRunner)
	public void run(String... args) throws Exception {						
	}

}
