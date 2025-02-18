package org.example.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String POSTS_FILE = "public/posts.json";
    private static final String USERS_FILE = "public/users.json";
    public static ArrayList<Post> posts = new ArrayList<Post>();
    public static ArrayList<User> users = new ArrayList<User>();

    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Cargar los 50 primeros posts");
            System.out.println("2. Cargar usuarios");
            System.out.println("3. Guardar datos en archivos JSON");
            System.out.println("4. Mostrar los posts de un usuario especifico");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    posts();
                    usuarios();
                    break;
                case 2:
                    usuarios();
                    break;
                case 3:
                    relacionarPostsConUsuarios();
                    guardar();
                    break;
                case 4:
                    mostrarPostsDeUsuario();
                    break;
                    case 5:
                    cargarJson();

                        break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        while (option != 0);

        scanner.close();
    }

    public static void posts() {
        try {
            posts = new ArrayList<>(fetchPosts());
            System.out.println("Posts cargados: " + posts.size());
        } catch (IOException e) {
            System.err.println("Error al cargar los posts: " + e.getMessage());
        }
    }

    public static void usuarios() {
        try {
            users = new ArrayList<>(fetchUsers());
            System.out.println("Usuarios cargados: " + users.size());
        } catch (IOException e) {
            System.err.println("Error al cargar los usuarios: " + e.getMessage());
        }
    }

    public static void cargarJson() {
        try {
            posts = new ArrayList<>(fetchPosts());
            System.out.println("Posts cargados: " + posts.size());
            users = new ArrayList<>(fetchUsers());
            System.out.println("Usuarios cargados: " + users.size());
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
        }
    }

    public static void relacionarPostsConUsuarios() {
        for (User user : users) {
            user.getPosts().clear(); // Clear existing posts to avoid duplication
            for (Post post : posts) {
                if (post.getUserId() == user.getId()) {
                    post.setUser(user); // Set the user reference in the post
                    user.getPosts().add(post); // Add the post to the user's list
                }
            }
        }
    }

    public static void mostrarPostsDeUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del usuario: ");
        int userId = scanner.nextInt();
        for (User user : users) {
            if (user.getId() == userId) {
                System.out.println("Posts de " + user.getName() + ":");
                for (Post post : user.getPosts()) {
                    System.out.println(post.getId());
                }
                return;
            }
        }
        System.out.println("Usuario no encontrado.");
    }

        public static void guardar(){
        try {
            List<Post> posts = fetchPosts();
            saveToJsonFile(posts, POSTS_FILE);
            System.out.println("Posts guardados en " + POSTS_FILE);

            List<User> users = fetchUsers();
            saveToJsonFile(users, USERS_FILE);
            System.out.println("Usuarios guardados en " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    private static List<Post> fetchPosts() throws IOException {
        Request request = new Request.Builder().url(POSTS_URL).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();
            Type listType = new TypeToken<List<Post>>() {}.getType();
            return gson.fromJson(responseBody, listType);
        }
    }

    private static List<User> fetchUsers() throws IOException {
        Request request = new Request.Builder().url(USERS_URL).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();
            Type listType = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(responseBody, listType);
        }
    }

    private static void saveToJsonFile(Object data, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/* use this as an example to make the relation between users and posts class to store posts on their respective users

    public static void insertarAutores() {
        int i = 1;
        while (i <= 2) {
            Autor autor = new Autor(i);
            System.out.println("Nombre del autor: ");
            autor.setNombre(biblioteca.entrada.next());
            System.out.println("Nif del autor: ");
            autor.setNif(biblioteca.entrada.nextInt());
            biblioteca.LAutores.add(autor);
            i++;
        }
    }
public static void insertarLibros() {
        int i = 10;
        while (i <= 11) {
            // el i (pk) se genera automaticax
            Libro libro = new Libro(i);
            System.out.println("Autor del libro: ");
            int nif = biblioteca.entrada.nextInt(); // Primary key del libro
            // Busca ese nif en la lista de autores y devuelve el autor
            Autor autor = biblioteca.buscarAutor(nif); // busca
            libro.setAutor(autor);
            autor.setLibros(libro); // al autor le paso el libro del que es autor
            biblioteca.LLibros.add(libro);// añade ese libro al array de libros
            i++;
        }
    }*/