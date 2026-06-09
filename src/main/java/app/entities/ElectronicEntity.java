package app.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("ELECTRONIC")
public abstract class ElectronicEntity extends  ProductEntity {
    private int warrantyMonths;
    private String brand;
    private Double price;

    public ElectronicEntity() {
        setWarrantyMonths(12);
        setBrand("Electronic");
        setPrice(0.0);
    }

    public ElectronicEntity(int warrantyMonths, String brand, Double price) {
        this.warrantyMonths = warrantyMonths;
        this.brand = brand;
        this.price = price;
    }

    // Getters and Setters
    public String getBrand() {return brand;}
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }
    public void setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public Double getPrice() {return price;}
    public void setPrice(Double price) {this.price = price;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ElectronicEntity that = (ElectronicEntity) o;
        return getWarrantyMonths() == that.getWarrantyMonths() && Double.compare(getPrice(), that.getPrice()) == 0 && Objects.equals(getBrand(), that.getBrand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWarrantyMonths(), getBrand(), getPrice());
    }

    @Override
    public String toString() {
        return "ElectronicEntity{" +
                "warrantyMonths=" + warrantyMonths +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                "} " + super.toString();
    }
}