package org.example;


import java.util.Scanner;

public class Empleados {
    private String DNI;
    private String nombre;
    private String email;
    private double salarioBase = 1000.0;
    private int horasExtra = 0;
    private double precioHorasExtra = 10.0;
    public static Scanner entrada = new Scanner(System.in);

    Empleados() {

    }

    public void pedirDatos() {
        System.out.print("DNI: ");
        this.DNI = entrada.next();
        System.out.print("Nombre: ");
        this.nombre = entrada.next();
        System.out.print("Email: ");
        this.email = entrada.next();
        System.out.print("Horas extra: "); 
        this.horasExtra = entrada.nextInt();
    }

    public int getHorasExtra() {
        System.out.println(horasExtra);
        return 0;
    }
    public void setPrecioHorasExtra(double precioHorasExtra) {
        this.precioHorasExtra = precioHorasExtra;
    }
    public double calcularSueldo() {
        return salarioBase + (horasExtra * precioHorasExtra);
    }

    public String toString() {
        return "Nombre: " + nombre + "\nDNI: " + DNI + "\nEmail: " + email + "\n";
    }




}

