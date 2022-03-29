package exam.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "laptops")
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "mac_address", nullable = false, unique = true)
    private String macAddress;

    @Column(name = "cpu_speed", nullable = false)
    private float cpuSpeed;

    @Column(nullable = false)
    private int ram;

    @Column(nullable = false)
    private int storage;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "warranty_type")
    private WarrantyType warrantyType;

    @ManyToOne
    private Shop shop;

    public Laptop() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public float getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(float cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Laptop laptop = (Laptop) o;
        return id == laptop.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Laptop - %s\n*Cpu speed - %.2f\n**Ram - %d\n***Storage - %d\n****Price - %.2f\n#Shop name - %s\n##Town - %s\n"
        , macAddress, cpuSpeed, ram, storage, price, getShop().getName(), getShop().getTown().getName());

    }
}
