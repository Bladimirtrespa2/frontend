package com.example.frontend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class FrontendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false"); // <<<<<< AQUI
		SpringApplication.run(FrontendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SwingUtilities.invokeLater(() -> {

			JFrame frame = new JFrame("Frontend Swing");
			frame.setSize(600, 400);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

		});
	}
}
