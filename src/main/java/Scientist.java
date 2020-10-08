import lombok.*;

import java.net.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Scientist {
    private String    _login;
    private double    _hirschIndex;
    private URL       _website;
    private Education _education;

    //-----------------------------------------------

    public Scientist(Scientist scientist) {
        _login       = scientist._login;
        _hirschIndex = scientist._hirschIndex;
        _website     = scientist._website;
        _education   = scientist._education;
    }

    public Scientist(String name) {
        this(name, 0.0, null, null);
    }

    public Scientist(String name, Education education) {
        this(name, 0.0, null, education);
    }

    public Scientist(String name, URL website, Education education) {
        this(name, 0.0, website, education);
    }

    public Scientist(String name, double hirschIndex, Education education) {
        this(name, hirschIndex, null, education);
    }

    //-----------------------------------------------

    //-----------------------------------------------
}
