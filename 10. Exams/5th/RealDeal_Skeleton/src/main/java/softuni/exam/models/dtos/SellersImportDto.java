package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sellers")
public class SellersImportDto {
    @XmlElement(name = "seller")
    List<SellerImportDto> sellers;

    public SellersImportDto() {
    }

    public List<SellerImportDto> getSellers() {
        return sellers;
    }
}
