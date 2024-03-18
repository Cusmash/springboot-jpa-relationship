package com.cush.curso.springboot.jpa.springbootjparelationship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.cush.curso.springboot.jpa.springbootjparelationship.entities.Address;
import com.cush.curso.springboot.jpa.springbootjparelationship.entities.Client;
import com.cush.curso.springboot.jpa.springbootjparelationship.entities.Invoice;
import com.cush.curso.springboot.jpa.springbootjparelationship.repositories.ClientRepository;
import com.cush.curso.springboot.jpa.springbootjparelationship.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		oneToManyBidireccionalFindById();
	}

	@Transactional
	public void oneToManyBidireccionalFindById(){
		Optional<Client> optionalClient = clientRepository.findOneWithInvoices(1L);

		optionalClient.ifPresent(client -> {
	
			Invoice invoice1 = new Invoice("compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("compras de oficina", 8000L);
	
			client.addInvoice(invoice1).addInvoice(invoice2);
	
			clientRepository.save(client);
			System.out.println(client);

		});
	}

	@Transactional
	public void oneToManyBidireccional(){
		Client client = new Client("Fran", "Moras");

		Invoice invoice1 = new Invoice("compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("compras de oficina", 8000L);

		client.addInvoice(invoice1).addInvoice(invoice2);

		clientRepository.save(client);
		System.out.println(client);
	}

	@Transactional
	public void removeAddressFindById(){
		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de Gama", 9875);

			client.setAddresses(Arrays.asList(address1, address2));

			clientRepository.save(client);

			System.out.println(client);

			Optional<Client> optionalClient2 = clientRepository.findOneWithAddresses(2L);
			optionalClient2.ifPresent(c -> {
				c.getAddresses().remove(address2);
				clientRepository.save(c);
				System.out.println(c);
			});
		});

	}

	@Transactional
	public void removeAddress(){
		Client client = new Client("Fran", "Moras");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);

		Optional<Client> optionalClient = clientRepository.findById(3L);
		optionalClient.ifPresent(c -> {
			c.getAddresses().remove(address1);
			clientRepository.save(c);
			System.out.println(c);
		});
	}

	@Transactional
	public void OneToManyFindById(){
		Optional<Client> optionaClient = clientRepository.findById(2L);
		optionaClient.ifPresent(client -> {
			Address addres1 = new Address("El verjel", 1234);
			Address addres2 = new Address("Vasco de Gama", 9875);

			client.setAddresses(Arrays.asList(addres1, addres2));

			clientRepository.save(client);

			System.out.println(client);
		});

	}


	@Transactional
	public void OneToMany(){
		Client client = new Client("Fran", "Moras");

		Address addres1 = new Address("El verjel", 1234);
		Address addres2 = new Address("Vasco de Gama", 9875);

		client.getAddresses().add(addres1);
		client.getAddresses().add(addres2);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void maniToOne(){
		Client client = new Client("John", "Doe");
		clientRepository.save(client);

		Invoice invoice = new Invoice("compras de oficina", 2000L);
		invoice.setClient(client);
		Invoice invoiceDB = invoiceRepository.save(invoice);
		System.out.println(invoiceDB);
	}

	@Transactional
	public void maniToOneFindByIdClient(){
		Optional<Client> optionalClient = clientRepository.findById(1L);

		if(optionalClient.isPresent()){
			Client client = optionalClient.orElseThrow();

			Invoice invoice = new Invoice("compras de oficina", 2000L);
			invoice.setClient(client);
			Invoice invoiceDB = invoiceRepository.save(invoice);
			System.out.println(invoiceDB);
		}

	}
}
