package com.iris.ficha.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

	private SpecificationUtils() {
	}

	public static boolean hasText(String value) {
		return value != null && !value.trim().isEmpty();
	}

	public static <T> Specification<T> containsIgnoreCase(String attribute, String value) {
		if (!hasText(value)) {
			return null;
		}
		return (root, query, cb) -> cb.like(cb.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
	}

	public static <T> Specification<T> equalsUuid(String attribute, String value) {
		if (!hasText(value)) {
			return null;
		}
		UUID parsed = UUID.fromString(value);
		return (root, query, cb) -> cb.equal(root.get(attribute), parsed);
	}

	public static <T> Specification<T> andAll(List<Specification<T>> specifications) {
		Specification<T> result = null;
		for (Specification<T> specification : specifications) {
			if (specification == null) {
				continue;
			}
			result = result == null ? Specification.where(specification) : result.and(specification);
		}
		return result;
	}
}
