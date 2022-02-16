package valuetype.embedded;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PhoneServiceProvider {
    @Id String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
