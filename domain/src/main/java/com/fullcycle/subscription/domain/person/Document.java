package com.fullcycle.subscription.domain.person;

import com.fullcycle.subscription.domain.ValueObject;

public sealed interface Document extends ValueObject {
    static Document create(final String documentNumber, final String documentType) {
        return DocumentFactory.create(documentNumber, documentType);
    }

    String value();

    String type();

    record Cpf(String value) implements Document {
        public static final String TYPE = "cpf";

        public Cpf {
            this.assertArgumentNotEmpty(value, "'cpf' should not be empty");
            this.assertArgumentLength(value, 11, "'cpf' is invalid");
        }

        @Override
        public String type() {
            return TYPE;
        }
    }

    record Cnpj(String value) implements Document {
        public static final String TYPE = "cnpj";

        public Cnpj {
            this.assertArgumentNotEmpty(value, "'cnpj' should not be empty");
            this.assertArgumentLength(value, 14, "'cnpj' is invalid");
        }

        @Override
        public String type() {
            return TYPE;
        }
    }
}
