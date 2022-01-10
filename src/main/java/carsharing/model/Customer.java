package carsharing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Customer {
    private int id;
    private String name;
    @Setter
    private int rentedCarId;
}
