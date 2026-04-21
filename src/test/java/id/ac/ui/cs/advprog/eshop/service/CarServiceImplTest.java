package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    @Mock
    private CarRepositoryInterface carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void testCreate() {
        Car car = new Car();
        carService.create(car);
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        List<Car> carList = new ArrayList<>();
        carList.add(new Car());
        Iterator<Car> iterator = carList.iterator();
        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testFindById() {
        Car car = new Car();
        when(carRepository.findById("c1")).thenReturn(car);
        Car result = carService.findById("c1");
        assertEquals(car, result);
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        carService.update("c1", car);
        verify(carRepository).update("c1", car);
    }

    @Test
    void testDelete() {
        carService.deleteCarById("c1");
        verify(carRepository).delete("c1");
    }
}