package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateWithNullId() {
        Car car = new Car();
        car.setCarName("Tesla");
        Car savedCar = carRepository.create(car);
        assertNotNull(savedCar.getCarId()); // Memastikan UUID digenerate (Branch if null)
    }

    @Test
    void testCreateWithExistingId() {
        Car car = new Car();
        car.setCarId("manual-id-123");
        Car savedCar = carRepository.create(car);
        assertEquals("manual-id-123", savedCar.getCarId()); // Branch if not null
    }

    @Test
    void testUpdateSuccessful() {
        Car car = new Car();
        car.setCarId("c1");
        carRepository.create(car);

        Car updatedInfo = new Car();
        updatedInfo.setCarName("New Name");
        updatedInfo.setCarColor("Red");
        updatedInfo.setCarQuantity(10);

        Car result = carRepository.update("c1", updatedInfo);
        assertEquals("New Name", result.getCarName());
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarId("c1");
        carRepository.create(car);
        carRepository.delete("c1");
        assertNull(carRepository.findById("c1"));
    }

    @Test
    void testFindByIdNotFound() {
        Car result = carRepository.findById("id-ngasal");
        assertNull(result);
    }

    @Test
    void testUpdateNotFound() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Honda");
        Car result = carRepository.update("id-ngasal", updatedCar);
        assertNull(result);
    }

    @Test
    void testDeleteWithNonExistentId() {
        carRepository.delete("id-ngasal");
        assertNull(carRepository.findById("id-ngasal"));
    }
}