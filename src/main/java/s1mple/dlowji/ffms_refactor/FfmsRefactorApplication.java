package s1mple.dlowji.ffms_refactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.convert.Jsr310Converters;
import s1mple.dlowji.ffms_refactor.entities.converters.SexConverter;

@SpringBootApplication
@EntityScan(basePackageClasses = { FfmsRefactorApplication.class,
Jsr310Converters.class })

public class FfmsRefactorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FfmsRefactorApplication.class, args);
	}

}
