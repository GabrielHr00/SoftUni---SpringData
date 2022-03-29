package exam.model.dtos;

import exam.model.entities.WarrantyType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class LaptopsImportDto {
    @Size(min = 8)
    private String macAddress;

    @Positive
    private float cpuSpeed;

    @Min(8)
    @Max(128)
    private int ram;

    @Min(128)
    @Max(1024)
    private int storage;

    @Size(min = 10)
    private String description;

    @Positive
    private BigDecimal price;

    private WarrantyType warrantyType;

    private LaptopShopDto shop;

    public LaptopsImportDto() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public float getCpuSpeed() {
        return cpuSpeed;
    }

    public int getRam() {
        return ram;
    }

    public int getStorage() {
        return storage;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public LaptopShopDto getShop() {
        return shop;
    }
}
