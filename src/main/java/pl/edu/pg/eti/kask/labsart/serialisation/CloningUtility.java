package pl.edu.pg.eti.kask.labsart.serialisation;

import lombok.extern.java.Log;

import java.io.*;
import java.util.logging.Level;

// Not obvious
// When we get something from DataStore, we get a reference to it - it's Java
// We can use serialisation to easily make a deep copy of the object instead
@Log
public class CloningUtility {

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T object) {
        try {
            //Create outputs
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            //Write out target to the outputs
            oos.writeObject(object);
            //Now we read it back
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            //And a deep copy is made
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            throw new IllegalStateException(ex);
        }
    }

}
