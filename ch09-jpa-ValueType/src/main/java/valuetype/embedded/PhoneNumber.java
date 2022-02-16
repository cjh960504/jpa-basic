package valuetype.embedded;

import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Embeddable
public class PhoneNumber {


    String localNumber;
    String areaCode;

    /*임베디드 타입은 값 타입은 뿐만 아니라, 엔티티를 참조할 수 있다.*/
    @ManyToOne
    PhoneServiceProvider provider;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLocalNumber() {
        return localNumber;
    }

    public void setLocalNumber(String localNumber) {
        this.localNumber = localNumber;
    }

    public PhoneServiceProvider getProvider() {
        return provider;
    }

    public void setProvider(PhoneServiceProvider provider) {
        this.provider = provider;
    }
}
