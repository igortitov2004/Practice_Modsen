package com.example.practice_modsen_shop;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

@RestController
public class TestController {

    @GetMapping("/test/arithmetic-exception")
    public void throwArithmeticException() {
        throw new ArithmeticException("division by zero");
    }

    @GetMapping("/test/array_index_out_of_bounds_exception")
    public void throwArrayIndexOutOfBoundsException() {
        throw new ArrayIndexOutOfBoundsException("index goes beyond the array boundary");
    }

    //Other errors by analogy

    @GetMapping("/test/constraint-violation-exception")
    public void throwConstraintViolationException(@RequestParam @NotBlank String field) {
        if ("invalid".equals(field)) {
            throw new ConstraintViolationException("Validation error",
                    Set.of(new ConstraintViolationImpl("field", "error")));
        }
    }

    private static class ConstraintViolationImpl implements ConstraintViolation<Object> {
        private final String propertyPath;
        private final String message;

        public ConstraintViolationImpl(String propertyPath, String message) {
            this.propertyPath = propertyPath;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String getMessageTemplate() {
            return null;
        }

        @Override
        public Object getRootBean() {
            return null;
        }

        @Override
        public Class<Object> getRootBeanClass() {
            return Object.class;
        }

        @Override
        public Object getLeafBean() {
            return null;
        }

        @Override
        public Object[] getExecutableParameters() {
            return new Object[0];
        }

        @Override
        public Object getExecutableReturnValue() {
            return null;
        }

        @Override
        public Path getPropertyPath() {
            return new Path() {
                @Override
                public Iterator<Node> iterator() {
                    return Collections.emptyIterator();
                }

                @Override
                public String toString() {
                    return propertyPath;
                }
            };
        }

        @Override
        public Object getInvalidValue() {
            return null;
        }

        @Override
        public ConstraintDescriptor<?> getConstraintDescriptor() {
            return null;
        }

        @Override
        public <U> U unwrap(Class<U> type) {
            return null;
        }
    }

    @GetMapping("/test/method-argument-not-valid-exception")
    public void throwMethodArgumentNotValidException() throws Exception {
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(new FieldError("objectName", "field", "error"));

        throw new MethodArgumentNotValidException(null, bindingResult);
    }
}
