package app.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("LAPTOP")
public class LaptopEntity extends ElectronicEntity {
    private double screenSizeInches;
    private int ramGB;

    public LaptopEntity() {}

    public LaptopEntity(double screenSizeInches, int ramGB, int warrantyMonths, String brand, Double price) {
        this.screenSizeInches = screenSizeInches;
        this.ramGB = ramGB;
        this.setWarrantyMonths(warrantyMonths);
        this.setBrand(brand);
        this.setPrice(price);
    }

    // Getters and Setters
    public double getScreenSizeInches() {return screenSizeInches;}
    public void setScreenSizeInches(double screenSizeInches) {this.screenSizeInches = screenSizeInches;}

    public int getRamGB() {return ramGB;}
    public void setRamGB(int ramGB) {this.ramGB = ramGB;}

    @Override
    public void sellItem() {IO.println("Selling Laptop");}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LaptopEntity laptop = (LaptopEntity) o;
        return Double.compare(getScreenSizeInches(), laptop.getScreenSizeInches()) == 0 && getRamGB() == laptop.getRamGB();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getScreenSizeInches(), getRamGB());
    }

    @Override
    public String toString() {
        return "LaptopEntity{" +
                "screenSizeInches=" + screenSizeInches +
                ", ramGB=" + ramGB +
                "} " + super.toString();
    }
}