package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver; // Import ini penting!

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        // Menambahkan ViewResolver untuk mencegah Circular View Path error
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(carController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("createCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        mockMvc.perform(post("/car/createCar")
                        .flashAttr("car", new Car())) // Mengirimkan objek Car agar tidak null
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));
    }

    @Test
    void testCarListPage() throws Exception {
        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void testEditCarPage() throws Exception {
        Car car = new Car();
        car.setCarId("c1");
        when(carService.findById("c1")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/c1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testEditCarPost() throws Exception {
        mockMvc.perform(post("/car/editCar")
                        .flashAttr("car", new Car()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));
    }

    @Test
    void testDeleteCarPost() throws Exception {
        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "c1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));
    }
}