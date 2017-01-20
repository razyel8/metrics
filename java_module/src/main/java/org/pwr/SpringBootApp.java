package org.pwr;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.pwr.runners.CommandLineExecutor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			List<String> commitHashes = mockCommitHashes();

			// checkout for each commit in list, currently mocked
			for (String commitHash : commitHashes) {
				CommandLineExecutor.runGitCheckout(commitHash);
			}

			// run rscript file(s?)
			CommandLineExecutor.runRScriptFromPath();

		};
	}

	public List<String> mockCommitHashes() {
		List<String> commitHashes = new ArrayList<String>();
		commitHashes.add("cfdb8e01ebecb92abf87b3b68abd3e4c5dfb4d03");
		commitHashes.add("b447d7dbe644dc095dd1afc99c4d3fd31e9732f3");
		commitHashes.add("b3e94dc747715b468775312458ac0c9eef6f89f8");
		return commitHashes;
	}

}
