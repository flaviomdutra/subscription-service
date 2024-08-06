package com.fullcycle.subscription.application;

import java.util.function.Function;

public interface Presenter<UC_OUTPUT, NEW_OUT> extends Function<UC_OUTPUT, NEW_OUT> {
}
