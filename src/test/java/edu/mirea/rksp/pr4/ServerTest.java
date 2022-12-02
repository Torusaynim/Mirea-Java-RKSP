package edu.mirea.rksp.pr4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServerTest {

    Client client = new Client();
    Server server = new Server();

    @Test
    @DisplayName("Should always pass")
    public void uselessTest() {
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Correct output result")
    public void outputTest() {
        // тест
    }

}
