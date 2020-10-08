import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Citation {
    private String _source;
    private int    _pageNumber;

    //-----------------------------------------------

    public Citation(Citation citation) {
        _source     = citation._source;
        _pageNumber = citation._pageNumber;
    }

    public Citation(String source) {
        this(source, -1);
    }

    public Citation(int pageNumber) {
        this(null, pageNumber);
    }

    //-----------------------------------------------

    //-----------------------------------------------
}
