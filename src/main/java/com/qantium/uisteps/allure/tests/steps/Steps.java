package com.qantium.uisteps.allure.tests.steps;

import ru.yandex.qatools.allure.annotations.Step;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Steps {

    protected final String desc;

    private Steps(String desc) {
        this.desc = desc;
    }

    public static void step(String desc, Runnable step) {
        new SimpleStep(desc, step).run();
    }

    public static <T, R> R step(String desc, T param, Function<T, R> step) {
        return new FunctionStep<>(desc, step).apply(param);
    }

    public static <T> T step(String desc, Supplier<T> step) {
        return new SupplierStep<>(desc, step).get();
    }

    private static class SupplierStep<T> extends Steps {

        private final Supplier<T> step;

        private SupplierStep(String desc, Supplier<T> step) {
            super(desc);
            this.step = step;
        }

        public T get() {
            return step(desc);
        }

        @Step("{0}")
        private T step(String desc) {
            return step.get();
        }
    }


    private static class FunctionStep<T, R> extends Steps {

        private final Function<T, R> step;

        private FunctionStep(String desc, Function<T, R> step) {
            super(desc);
            this.step = step;
        }

        public R apply(T param) {
            return step(desc, param);
        }

        @Step("{0}")
        private R step(String desc, T param) {
            return step.apply(param);
        }
    }

    private static class SimpleStep extends Steps {
        private final Runnable step;

        private SimpleStep(String desc, Runnable step) {
            super(desc);
            this.step = step;
        }

        public void run() {
            step(desc);
        }

        @Step("{0}")
        private void step(String desc) {
            step.run();
        }
    }
}
