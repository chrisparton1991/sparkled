package io.sparkled.model.validator;

import io.sparkled.model.entity.Sequence;
import io.sparkled.model.validator.exception.EntityValidationException;

public class SequenceValidator {

    private Sequence sequence;

    public SequenceValidator(Sequence sequence) {
        this.sequence = sequence;
    }

    public void validate() {
        if (sequence.getName() == null) {
            throw new EntityValidationException(Errors.NO_NAME);
        }
    }

    private static class Errors {
        static final String NO_NAME = "Sequence name must not be empty.";
    }
}
