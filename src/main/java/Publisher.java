import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Publisher {
    private String         _name;
    private Specialisation _spec;
    private boolean        _predatory;
    private double         _impactFactor;

    //-----------------------------------------------

    public Publisher(Publisher publisher) {
        _name         = publisher._name;
        _spec         = publisher._spec;
        _predatory    = publisher._predatory;
        _impactFactor = publisher._impactFactor;
    }

    public Publisher(String name) {
        this(name, null, false, 0.0);
    }

    public Publisher(String name, Specialisation spec) {
        this(name, spec, false, 0.0);
    }

    public Publisher(String name, Specialisation spec, double impactFactor) {
        this(name, spec, false, impactFactor);
    }

    //-----------------------------------------------

    //-----------------------------------------------
}
