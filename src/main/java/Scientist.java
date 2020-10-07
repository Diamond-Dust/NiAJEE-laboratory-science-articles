import java.net.URL;

public class Scientist {
    private String    _name;
    private double    _hirschIndex;
    private URL       _website;
    private Education _education;

    //-----------------------------------------------

    public Scientist(Scientist scientist) {
        _name        = scientist._name;
        _hirschIndex = scientist._hirschIndex;
        _website     = scientist._website;
        _education   = scientist._education;
    }

    public Scientist(String name, double hirschIndex, URL website, Education education) {
        _name        = name;
        _hirschIndex = hirschIndex;
        _website     = website;
        _education   = education;
    }

    public Scientist() {
        this(null, 0.0, null, null);
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

    public String getName() {
        return _name;
    }

    public double getHirschIndex() {
        return _hirschIndex;
    }

    public URL getWebsite() {
        return _website;
    }

    public Education getEducation() {
        return _education;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setHirschIndex(double hirschIndex) {
        _hirschIndex = hirschIndex;
    }

    public void setWebsite(URL website) {
        _website = website;
    }

    public void setEducation(Education education) {
        _education = education;
    }

    //-----------------------------------------------

    @Override
    public String toString() {
        return
                "Name: "         + _name                 + "\n" +
                "Hirsch Index: " + _hirschIndex          + "\n" +
                "Website: "      + _website.toString()   + "\n" +
                "Education: "    + _education.toString()
        ;
    }
}
