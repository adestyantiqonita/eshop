package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    Car car;

    @BeforeEach
    void setUp() {
        this.car = new Car();
        this.car.setCarId("c0b12345-6789-4abc-def0-1234567890ab");
        this.car.setCarName("Toyota Supra");
        this.car.setCarColor("Midnight Blue");
        this.car.setCarQuantity(5);
    }

    @Test
    void testGetCarId() {
        assertEquals("c0b12345-6789-4abc-def0-1234567890ab", this.car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Toyota Supra", this.car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Midnight Blue", this.car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(5, this.car.getCarQuantity());
    }

    @Test
    void testSetCarName() {
        this.car.setCarName("Nissan GTR");
        assertEquals("Nissan GTR", this.car.getCarName());
    }
}