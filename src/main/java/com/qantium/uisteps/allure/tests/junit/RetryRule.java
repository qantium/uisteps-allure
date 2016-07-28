package com.qantium.uisteps.allure.tests.junit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static com.qantium.uisteps.core.properties.UIStepsProperties.getProperty;
import static com.qantium.uisteps.core.properties.UIStepsProperty.RETRY_ATTEMPTS;
import static com.qantium.uisteps.core.properties.UIStepsProperty.RETRY_DELAY;


/**
 * Created by Anton Solyankin
 */
public class RetryRule implements TestRule {

    private int attempts;
    private int delay;
    private JUnitTest test;
    private int counter = 0;

    public RetryRule(JUnitTest test) {
        this.test = test;
        attempts = Integer.parseInt(getProperty(RETRY_ATTEMPTS));
        delay = Integer.parseInt(getProperty(RETRY_DELAY));
    }

    public RetryRule(int attempts, int delay, JUnitTest test) {
        if (attempts < 0) {
            attempts = 0;
        }
        this.attempts = attempts;
        this.delay = delay;
        this.test = test;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                while (counter <= attempts) {
                    try {
                        if (counter > 0) {
                            test.retryTest(counter);
                        }

                        test.closeAllBrowsers();
                        base.evaluate();
                        break;
                    } catch (Throwable ex) {
                        counter++;
                        delay();
                    }
                }
            }
        };
    }

    private void delay() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}



