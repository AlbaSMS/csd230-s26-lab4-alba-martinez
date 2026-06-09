package app.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("PHONE")
public class PhoneEntity extends ElectronicEntity {
    private boolean supports5G;
    private int cameraMP;

    public PhoneEntity() {}

    public PhoneEntity(boolean supports5G, int cameraMP, int warrantyMonths, String brand, Double price) {
        this.supports5G = supports5G;
        this.cameraMP = cameraMP;
        this.setWarrantyMonths(warrantyMonths);
        this.setBrand(brand);
        this.setPrice(price);
    }

    // Getters and Setters

    public boolean isSupports5G() {return supports5G;}
    public void setSupports5G(boolean supports5G) {this.supports5G = supports5G;}

    public int getCameraMP() {return cameraMP;}
    public void setCameraMP(int cameraMP) {this.cameraMP = cameraMP;}

    @Override
    public void sellItem() {IO.println("Selling Phone");}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PhoneEntity phone = (PhoneEntity) o;
        return isSupports5G() == phone.isSupports5G() && getCameraMP() == phone.getCameraMP();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isSupports5G(), getCameraMP());
    }

    @Override
    public String toString() {
        return "PhoneEntity{" +
                "supports5G=" + supports5G +
                ", cameraMP=" + cameraMP +
                "} " + super.toString();
    }
}