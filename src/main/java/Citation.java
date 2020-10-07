public class Citation {
    private String _source;
    private int    _pageNumber;

    //-----------------------------------------------

    public Citation(Citation citation) {
        _source     = citation._source;
        _pageNumber = citation._pageNumber;
    }

    public Citation(String source, int pageNumber) {
        _source     = source;
        _pageNumber = pageNumber;
    }

    public Citation() {
        this(null, -1);
    }

    public Citation(String source) {
        this(source, -1);
    }

    public Citation(int pageNumber) {
        this(null, pageNumber);
    }

    //-----------------------------------------------



    //-----------------------------------------------

    public String getSource() {
        return _source;
    }

    public int getPageNumber() {
        return _pageNumber;
    }

    public void setSource(String source) {
        _source = source;
    }

    public void setPageNumber(int pageNumber) {
        _pageNumber = pageNumber;
    }

    //-----------------------------------------------

    @Override
    public String toString() {
        return " ("           + _source + ", " + _pageNumber + ") ";
    }
}
