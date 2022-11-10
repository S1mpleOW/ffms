package s1mple.dlowji.ffms_refactor.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class ItemOrderForm {
	@Length(min = 10, max = 10 , message = "Phone number must be 10 characters")
	private String phone;

	@NotNull(message = "Items must not be null")
	private List<HashMap<String, Long>> items;

}
