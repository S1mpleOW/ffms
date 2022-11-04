package s1mple.dlowji.ffms_refactor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.repositories.CustomerRepository;
import s1mple.dlowji.ffms_refactor.services.ICustomerService;

import java.util.Optional;

@Service
public class ICustomerServiceImpl implements ICustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	@Value("${spring.data.rest.default-page-size}")
	private int pageSize;
	@Override
	public Page<Customer> findAll() {
		Pageable pageable = PageRequest.of(0, pageSize);
		return customerRepository.findAll(pageable);
	}

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public boolean existsById(Long id) {
		return customerRepository.existsById(id);
	}

	@Override
	public Optional<Customer> findCustomerById(Long id) {
		return customerRepository.findById(id);
	}

	@Override
	public Customer deleteById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if(customer.isPresent()) {
			customerRepository.deleteById(id);
		}
		return customer.get();
	}

	@Override
	public Optional<Customer> findCustomerByAccountId(Long id) {
		return customerRepository.findCustomerByAccount_Id(id);
	}
}
