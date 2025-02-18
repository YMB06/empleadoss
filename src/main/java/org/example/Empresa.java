package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.*;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class Empresa {
    static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Empleados> LEmpleados = new ArrayList<>();
    private static ArrayList<Contable> LContables = new ArrayList<>();
    private static ArrayList<Director> LDirectores = new ArrayList<>();

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("********************");
            System.out.println("0. Cargar datos");
            System.out.println("1. Añadir empleado");
            System.out.println("2. Añadir contable");
            System.out.println("3. Añadir director");
            System.out.println("4. Informe");
            System.out.println("5. Guardar");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 0:
                    cargarEmpleados();
                    break;
                case 1:
                    nuevoEmpleado();
                    break;
                case 2:
                    nuevoContable();
                    break;
                case 3:
                    nuevoDirector();
                    break;
                case 4:
                    informe();
                    // Implement report generation logic here
                    break;
                case 5:
                    guardarEmpleados();
                    break;
                case 6:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    public static void nuevoEmpleado() {
        System.out.println("Introduce los datos del empleado:");
        Empleados empleado = new Empleados();
        empleado.pedirDatos();
        LEmpleados.add(empleado);
        guardarEmpleados();

        System.out.println(empleado);
        System.out.println("Sueldo: " + empleado.calcularSueldo() + "€");
    }

    public static void nuevoContable() {
        System.out.println("\nIntroduce los datos del contable:");
        Contable contable = new Contable();
        contable.pedirDatos();
        LContables.add(contable);
        LEmpleados.add(contable);
        guardarEmpleados();

        System.out.println(contable);
        System.out.println("Sueldo: " + contable.calcularSueldo() + "€");
    }

    public static void nuevoDirector() {
        System.out.println("\nIntroduce los datos del director:");
        Director director = new Director();
        director.pedirDatos();
        LDirectores.add(director);
        LEmpleados.add(director);
        guardarEmpleados();
        System.out.println(director);
        System.out.println("Sueldo: " + director.calcularSueldo() + "€");
    }

        /* 4.mostrar informes: que tenga el numero total de empleados, contables y directores saldoTotal, horasextraTotal de (D, E, C) y que se guarde en informe.json*/

    public static void informe() {
        int totalEmpleados = LEmpleados.size();
        int totalContables = LContables.size();
        int totalDirectores = LDirectores.size();
        double saldoTotal = 0;
        int horasExtraTotal = 0;
        for (Empleados e : LEmpleados) {
            saldoTotal += e.calcularSueldo();
            horasExtraTotal += e.getHorasExtra();
        }
        System.out.println("Total de empleados: " + totalEmpleados);
        System.out.println("Total de contables: " + totalContables);
        System.out.println("Total de directores: " + totalDirectores);
        System.out.println("Saldo total: " + saldoTotal);
        System.out.println("Horas extra total: " + horasExtraTotal);
    }




    public static void guardarEmpleados() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String ruta = "C:\\Users\\USER\\Documents\\empleados\\public";
        try (FileWriter writer = new FileWriter(ruta + "\\empleados.json")) {
            gson.toJson(LEmpleados, writer);
        } catch (IOException e) {
            System.out.println("No se ha podido guardar el archivo");
        }
    }

    public static void cargarEmpleados() {
        Type type = new TypeToken<ArrayList<Empleados>>() {}.getType();
        String ruta = "C:\\Users\\USER\\Documents\\empleados\\public";
        try (FileReader reader = new FileReader(ruta + "\\empleados.json")) {
            Gson gson = new Gson();
            ArrayList<Empleados> empleadosCargados = gson.fromJson(reader, type);
            for (Empleados e : empleadosCargados) {
                if (e instanceof Contable) {
                    LContables.add((Contable) e);
                } else if (e instanceof Director) {
                    LDirectores.add((Director) e);
                }
                LEmpleados.add(e);
            }
        } catch (IOException e) {
            System.out.println("No se ha podido leer el archivo");
        }
    }
}



