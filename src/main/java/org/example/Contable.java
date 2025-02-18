package org.example;

public class Contable extends Empleados {

    private double plusSalario = 200.0;

     Contable() {
        super();
    }
    
    //al sobreescribir el metodo de la clase superior no hace falta llamar a este en el constructor
    @Override
    public void pedirDatos() {
        super.pedirDatos();
    }
    public void contabilizar() {
        System.out.println("Estoy contabilizando...");
    }

    @Override
    public double calcularSueldo() {
        return super.calcularSueldo() + plusSalario;
    }
}

