package tech42.sathish.inventorymanagement;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by lenovo on 2/2/17.
 */

public class EmailValidatorTest {

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.com"));
    }
}
