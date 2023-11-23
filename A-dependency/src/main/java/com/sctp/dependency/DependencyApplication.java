package com.sctp.dependency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DependencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DependencyApplication.class, args);

		Service service = new ServiceB();
		Client client = new ClientA(service);

		client.doSomething();

		// Question:
		// What if I had another client (ClientA)
		// but this client needs to implement ServiceC

		Service anotherService = new ServiceC();
		Client anotherClient = new ClientA(anotherService);

		anotherClient.doSomething();
	}

}
