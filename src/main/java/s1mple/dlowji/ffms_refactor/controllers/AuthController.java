package s1mple.dlowji.ffms_refactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1mple.dlowji.ffms_refactor.dto.request.SignInForm;
import s1mple.dlowji.ffms_refactor.dto.request.SignUpForm;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseJwt;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseMessage;
import s1mple.dlowji.ffms_refactor.entities.Account;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.Role;
import s1mple.dlowji.ffms_refactor.entities.enums.RoleName;
import s1mple.dlowji.ffms_refactor.helper.JwtHelper;
import s1mple.dlowji.ffms_refactor.security.userprincipal.UserPrincipal;
import s1mple.dlowji.ffms_refactor.services.IAccountService;
import s1mple.dlowji.ffms_refactor.services.impl.ICustomerServiceImpl;
import s1mple.dlowji.ffms_refactor.services.impl.IRoleServiceImpl;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private IAccountService iAccountService;

	@Autowired
	private IRoleServiceImpl iRoleService;

	@Autowired
	private ICustomerServiceImpl iCustomerService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtHelper jwtHelper;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
		try {
			if (iAccountService.existsByUsername(signUpForm.getUsername())) {
				return new ResponseEntity<>(new ResponseMessage("The username is " +
				"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}
			if (iAccountService.existsByEmail(signUpForm.getEmail())) {
				return new ResponseEntity<>(new ResponseMessage("The email is " +
				"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}
			if (iAccountService.existsByPhone(signUpForm.getPhone())) {
				return new ResponseEntity<>(new ResponseMessage("The phone number is " +
				"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}
			Set<Role> roles = new HashSet<>();
			Optional<Role> userRole = iRoleService.findByName(RoleName.USER);
			roles.add(userRole.get());
			Account account =
			Account.builder()
			.email(signUpForm.getEmail())
			.phone(signUpForm.getPhone())
			.sex(signUpForm.getSex())
			.dob(signUpForm.getDob())
			.address(signUpForm.getAddress())
			.fullName(signUpForm.getFullName())
			.username(signUpForm.getUsername())
			.password(passwordEncoder.encode(signUpForm.getPassword()))
			.build();

			account.setRoles(roles);
			Customer customer = Customer.builder().rewardPoint(0L).account(account).build();
			iAccountService.save(account);
			iCustomerService.save(customer);
			return new ResponseEntity<>(new ResponseMessage("Create success",
			HttpStatus.OK.value()),
			HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ResponseMessage(e.getMessage(),
			HttpStatus.INTERNAL_SERVER_ERROR.value()),
			HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInForm.getUsername(),
		signInForm.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtHelper.createToken(authentication);
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		List<String > roles =
		userPrincipal.getRoles().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		System.out.println(roles);

		return ResponseEntity.ok(ResponseJwt.builder().token(token).name(userPrincipal.getFullName()).roles(roles).type("Bearer").build());
	}
}
