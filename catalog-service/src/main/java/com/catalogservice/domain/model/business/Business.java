package com.catalogservice.domain.model.business;

import com.catalogservice.domain.exception.BusinessAlreadyActiveException;
import com.catalogservice.domain.exception.BusinessAlreadyInactiveException;
import com.catalogservice.domain.exception.BusinessNotActiveException;
import com.catalogservice.domain.exception.InvalidBusinessDataException;
import com.catalogservice.domain.model.business.enums.BusinessCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Business {
    private static final Pattern PHONE_E164 = Pattern.compile("^\\+[1-9]\\d{1,14}$");

    private BusinessId id;
    private String ownerId;
    private String name;
    private String description;
    private BusinessCategory category;
    private Address address;
    private String phone;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    public static Business create(String ownerId, String name, String description, BusinessCategory category, Address address, String phone) {
        if (ownerId == null || ownerId.isBlank()) {
            throw new InvalidBusinessDataException("El ownerId no puede ser nulo.");
        }

        Instant now = Instant.now();


        return new Business(BusinessId.generate(), ownerId, validateName(name), validateDescription(description), requireNonNull(category, "Category"), requireNonNull(address, "Adress"), validatePhone(phone), true, now, now);
    }

    public void desactivateBusiness() {
        if (!active) {
            throw new BusinessAlreadyInactiveException(id);
        }
        active = false;
        updatedAt = Instant.now();
    }

    public void reactivate() {
        if (active) {
            throw new BusinessAlreadyActiveException(id);
        }
        active = true;
        updatedAt = Instant.now();
    }

    public void updateData(String newName, String newDescription, BusinessCategory newCategory, Address newAddress, String newPhone) {
        if (!active) {
            throw new BusinessNotActiveException();
        }
        String finalName = newName != null ? validateName(newName) : name;
        String finalDescription = newDescription != null ? validateDescription(newDescription) : description;
        BusinessCategory finalCategory = newCategory != null ? newCategory : category;
        Address finalAddress = newAddress != null ? newAddress : address;
        String finalPhone = newPhone != null ? validatePhone(newPhone) : phone;

        boolean changed = !Objects.equals(finalName, name)
                || !Objects.equals(finalDescription, description)
                || finalCategory != category
                || !Objects.equals(finalAddress, address)
                || !Objects.equals(finalPhone, phone);

        if (!changed) return;

        name = finalName;
        description = finalDescription;
        category = finalCategory;
        address = finalAddress;
        phone = finalPhone;
        updatedAt = Instant.now();
    }


    // Validaciones
    public static String validateName(String value) {
        if (value == null || value.isBlank()) throw new InvalidBusinessDataException("El nombre no puede estar nulo");

        String trimmed = value.trim();

        if (trimmed.length() < 3 || trimmed.length() > 150) {
            throw new InvalidBusinessDataException("La descripcion tiene que tener entre 3 y 150 caracteres");
        }

        return trimmed;
    }

    public static String validateDescription(String value) {
        if (value == null || value.isBlank())
            throw new InvalidBusinessDataException("La descripcion no puede estar nulo");

        String trimmed = value.trim();

        if (value.length() > 1000) {
            throw new InvalidBusinessDataException("La descripcion no puede tener mas de 1000 caracteres");
        }

        return trimmed;
    }

    public static String validatePhone(String value) {
        if (value == null || value.isBlank()) throw new InvalidBusinessDataException("El telefono no puede ser nulo");

        String trimmed = value.trim();

        if (!PHONE_E164.matcher(trimmed).matches()) {
            throw new InvalidBusinessDataException("phone debe tener formato E.164, ej. +525512345678");
        }

        return trimmed;
    }

    private static <T> T requireNonNull(T value, String field) {
        if (value == null) throw new IllegalArgumentException(field + " es obligatorio");
        return value;
    }
}
