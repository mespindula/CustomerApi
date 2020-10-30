package com.composablebit.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dozer.CustomConverter;

import com.composablebit.dto.CustomerResponseDTO;
import com.composablebit.model.Customer;

public class CustomerCustomConverter implements CustomConverter {

	@Override
	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		if (sourceFieldValue == null)
            return null;
		if (sourceFieldValue instanceof Customer) {
			Customer cus = (Customer) sourceFieldValue;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String isoDateBirthDate = cus.getBirthDate() != null ? format.format(cus.getBirthDate()) : null;
            String isoDateCreatedAt = cus.getCreatedAt() != null ? cus.getCreatedAt().toString() : null;
            String isoDateUpdatedAt = cus.getUpdateAt() != null ? cus.getUpdateAt().toString() : null;
            CustomerResponseDTO dto = new CustomerResponseDTO();
            dto.setBirthDate(isoDateBirthDate);
            dto.setCreatedAt(isoDateCreatedAt);
            dto.setUpdateAt(isoDateUpdatedAt);
            dto.setCpf(formatCPF(cus.getCpf()));
            dto.setEmail(cus.getEmail());
            dto.setGender(cus.getGender());
            dto.setId(cus.getId());
            return dto;
        } else {
        	return null;
        }
		
	}
	
	public static String formatCPF(String cpf) { 
		Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})"); 
		Matcher matcher = pattern.matcher(cpf); 
		if (matcher.matches()) cpf = matcher.replaceAll("$1.$2.$3-$4"); 
		return cpf; 
	}

}
