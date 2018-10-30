package core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    @XmlElement(name="id")
    public String id;

    public Integer getId() {
        return Integer.parseInt(this.id);
    }
}