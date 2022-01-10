package carsharing.model;

import lombok.Data;

@Data
public class Customer {
    private int id;
    private String name;
    private int rentedCarId;
}
