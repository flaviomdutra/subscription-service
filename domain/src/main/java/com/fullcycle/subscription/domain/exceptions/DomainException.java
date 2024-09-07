package com.fullcycle.subscription.domain.exceptions;

import com.fullcycle.subscription.domain.AggregateRoot;
import com.fullcycle.subscription.domain.Identifier;
import com.fullcycle.subscription.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {

    protected final List<Error> errors;

    protected DomainException(final String aMessage, List<Error> anErrors) {
        super(aMessage);
        this.errors = anErrors;
    }

    public static DomainException with(final String aMessage) {
        return new DomainException(aMessage, List.of(new Error(aMessage)));
    }

    public static DomainException with(final Error anError) {
        return new DomainException(anError.message(), List.of(anError));
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException("", anErrors);
    }

    public static RuntimeException notFound(Class<? extends AggregateRoot<?>> aggClass, Identifier id) {
        return DomainException.with("%s with id %s was not found".formatted(aggClass.getCanonicalName(), id.value()));
    }

    public List<Error> getErrors() {
        return errors;
    }
}
