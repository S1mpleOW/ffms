package s1mple.dlowji.ffms_refactor.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignInForm {
	private String username;
	private String password;
}
