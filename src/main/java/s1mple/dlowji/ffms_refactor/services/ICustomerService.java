package s1mple.dlowji.ffms_refactor.services;

import s1mple.dlowji.ffms_refactor.entities.Customer;

import java.util.List;

public interface ICustomerService {
	List<Customer> findAll();

	Customer save(Customer customer);
}
