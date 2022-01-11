package carsharing.util;

import carsharing.model.Entity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@UtilityClass
public class ChooserUtils {

    public <T extends Entity> void outputEntities(List<T> list) {
        IntStream.iterate(0, i -> i + 1)
                .limit(list.size())
                .forEach(i -> System.out.printf("%d. %s%n", i + 1, list.get(i).getName()));
    }

    public <T extends Entity> Optional<T> chooseEntityFrom(List<T> list, int option) {
        if (option > 0 && option <= list.size()) {
            return Optional.of(list.get(option - 1));
        } else if (option == 0) {
            return Optional.empty();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
