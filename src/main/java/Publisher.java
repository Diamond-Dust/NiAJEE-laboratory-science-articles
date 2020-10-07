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

    public Publisher(String name, Specialisation spec, boolean predatory, double impactFactor) {
        _name         = name;
        _spec         = spec;
        _predatory    = predatory;
        _impactFactor = impactFactor;
    }

    public Publisher() {
        this(null, null, false, 0.0);
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

    public String getName() {
        return _name;
    }

    public Specialisation getSpecialisation() {
        return _spec;
    }

    public boolean getPredatory() {
        return _predatory;
    }

    public double getImpactFactor() {
        return _impactFactor;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setSpecialisation(Specialisation spec) {
        _spec = spec;
    }

    public void setPredatory(boolean predatory) {
        _predatory = predatory;
    }

    public void setImpactFactor(double impactFactor) {
        _impactFactor = impactFactor;
    }

    //-----------------------------------------------

    @Override
    public String toString() {
        return
                "Name: "           + _name            + "\n" +
                "Specialisation: " + _spec.toString() + "\n" +
                "Predatory: "      + _predatory       + "\n" +
                "Impact factor: "  + _impactFactor
        ;
    }
}
