package org.example;

public class Director extends Empleados {
    private int numDespacho;
    private double plusSalario = 400.0;

     Director() {
        super();
        this.setPrecioHorasExtra(20);
    }

        //al sobreescribir el metodo de la clase superior no hace falta llamar a este en el constructor
    @Override
    public void pedirDatos() {
        super.pedirDatos();
        System.out.print("Numero del despacho: ");
        this.numDespacho = entrada.nextInt();
    }

    public void analizarDatos() {
        System.out.println("Estoy analizando muchos datos...");
    }

    @Override
    public double calcularSueldo() {
        return super.calcularSueldo() + plusSalario;
    }

    @Override
    public String toString() {
        return super.toString() + "Numero de despacho: " + numDespacho + "\n";
    }
}

