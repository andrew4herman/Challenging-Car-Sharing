package carsharing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Car {
    private int id;
    private String name;
    private int companyId;
    @Setter
    private boolean rented;
}
