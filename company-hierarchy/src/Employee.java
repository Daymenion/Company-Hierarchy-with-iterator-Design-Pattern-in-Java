import util.IEmployee;

public class Employee implements IEmployee {
    protected String name;
    protected double pay;

    /*
    basit bit sınıf yapısı
     */

    public Employee(String name, double pay) {
        this.name = name;
        this.pay = pay;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPay() {
        return this.pay;
    }

}
