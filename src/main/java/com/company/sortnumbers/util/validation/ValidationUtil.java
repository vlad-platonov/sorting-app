package com.company.sortnumbers.util.validation;

import com.company.sortnumbers.util.constant.ConstMessages.Messages;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    public Optional<String> validate(String text) {
        if (text.isEmpty()) {
            return Optional.of(Messages.EMPTY_FIELD_ERROR);
        }

        int number;
        try {
            number = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return Optional.of(Messages.NOT_INTEGER_ERROR);
        }

        if (number <= 0) {
            return Optional.of(Messages.NOT_POSITIVE_ERROR);
        }

        return Optional.empty();
    }
}
