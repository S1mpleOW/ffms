package s1mple.dlowji.ffms_refactor.entities.converters;

import s1mple.dlowji.ffms_refactor.entities.enums.SexType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SexConverter implements AttributeConverter<SexType, String> {
	@Override
	public String convertToDatabaseColumn(SexType sexType) {
		System.out.println("convert to db " + sexType);
		if(sexType == null) {
			return null;
		}
		return sexType.toString();
	}

	@Override
	public SexType convertToEntityAttribute(String s) {
		System.out.println("from db to entity " + s);
		if (s == null) {
			return null;
		}
		SexType sex = Stream.of(SexType.values())
		.filter(c -> c.toString().equalsIgnoreCase(s))
		.findFirst().get();
		System.out.println(sex);
		return sex;
	}
}
