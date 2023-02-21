package co.develhope.crud.controllers;

import co.develhope.crud.entities.Car;
import co.develhope.crud.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    /**
     * This method use a POST request to create a new object car
     * @param car
     * @return
     */
    @PostMapping("/newcar")
    public Car createCar(@RequestBody Car car){
        return carRepository.save(car);
    }

    /**
     * This method use a GET request to return the list of all cars
     * @return
     */
    @GetMapping("/carslist")
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    /**
     * This method use a GET request to return a car sorted by its id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Car getACar(@PathVariable long id){
        return carRepository.existsById(id)
                ? carRepository.getById(id)
                : new Car();
    }

    /**
     * This method use an UPDATE request to modify an existing car object
     * @param id
     * @param type
     * @return
     */
    @PutMapping("/{id}")
    public Car updateCar(@PathVariable long id, @RequestParam String type){
        Car car;
        if (carRepository.existsById(id)){
            car = carRepository.getById(id);
            car.setType(type);
            car = carRepository.saveAndFlush(car);
        }else{
            car = new Car();
        }
        return car;
    }

    /**
     * This method use a DELETE request to remove an existing object car from DB
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    public void deleteSingle(@PathVariable long id, HttpServletResponse response){
        if (carRepository.existsById(id))
            carRepository.deleteById(id);
        else
            response.setStatus(409);
    }

    /**
     * This method use a DELETE request to remove all the cars objects from DB
     */
    @DeleteMapping("/removeallcars")
    public void deleteAll(){
        carRepository.deleteAll();
    }

}
