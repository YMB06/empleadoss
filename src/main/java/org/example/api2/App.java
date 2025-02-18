package org.example.api2;

import java.io.FileReader;
import java.io.FileWriter;
//java
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Type;

//terceros
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

//para hacer peticiones HTTP (get, post ...)  a una servidor web
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.api.Post;
import org.example.api.User;

public class App {
    static Scanner scanner = new Scanner(System.in);

    public static ArrayList<Post> LPost = new ArrayList<>();
    public static ArrayList<User> LUser = new ArrayList<>();
    public static Type postListType = new TypeToken<List<Post>>() {}.getType();
    public static Type userListType = new TypeToken<List<User>>() {}.getType();
    //prepara una instancia para realizar lecturas de consola
    public static  Scanner entrada = new Scanner(System.in);
    //prepara la una conexión inicial HTTP (a un servidor web Apache/nginx/Tomcat/iis/Express)
    public static OkHttpClient client = new OkHttpClient();
    //url base de la API
    public static String urlRoot = "https://jsonplaceholder.typicode.com/";
    //ruta del fichero pòst.json
    public static String ruta = "C:\\Users\\USER\\Documents\\empleadosss\\public\\";

    public static void main( String[] args )
    {
        Menu();
    }

    public static void relacionarPostsConUsuarios(){
        for (User user : LUser) {
            for (Post post : LPost) {
                if (post.getUserId() == user.getId()) {
                    user.getPosts().add(post);
                    post.setUser(user);
                }
            }
        }
    }

    public static void CargarPost(){
        //Crea la REQUEST Http de tipo Get para listar todos los Posts
        Request request = new Request.Builder()
                .url(urlRoot + "posts")
                .build();
        //Ejecutar la solicitud --> send
        System.out.println("Enviando ..."+ urlRoot + "posts");
        //client.newCall(request).execute(): Lanza la peticion (request) hacia la api
        //Api  :  (servidor web + sgbd) --> servidor fisico con su ip (dns)
        //La Api recibe una REQUEST (petición) y devuelve una RESPONSE (respuesta JSON)
        //me
        try (Response response = client.newCall(request).execute() ) {
            if (!response.isSuccessful()){
                System.out.println("Response Fallido");
                throw new IOException("Unexpected code "+ response);
            }
            System.out.println("Conexión Establedida y Response ok");
            String responseBody = response.body().string();
            Gson gson = new Gson();
            //genera la estructura del Post.json en base a la estructura de la clase Post
            postListType = new TypeToken<List<Post>>() {}.getType();
            //fromJson: transforma la respesta en formato json con su tipo (estructura)
            //y genera un arraylist de java (lista de obejtos)
            LPost = gson.fromJson(responseBody, postListType);
            for (Post post : LPost) {
                System.out.println(post.getTitle());
            }
        }catch (IOException e){ //IOException captura errores HTTP
            e.printStackTrace(); //mensaje generico tipo "Ayuda SysAdmin"
        }
    }

    public static void cargarUser(){
        //Crea la REQUEST Http de tipo Get para listar todos los Posts
        Request request = new Request.Builder()
                .url(urlRoot + "users")
                .build();
        //Ejecutar la solicitud --> send
        System.out.println("Enviando ..."+ urlRoot + "users");
        //client.newCall(request).execute(): Lanza la peticion (request) hacia la api
        //Api  :  (servidor web + sgbd) --> servidor fisico con su ip (dns)
        //La Api recibe una REQUEST (petición) y devuelve una RESPONSE (respuesta JSON)
        //me
        try (Response response = client.newCall(request).execute() ) {
            if (!response.isSuccessful()){
                System.out.println("Response Fallido");
                throw new IOException("Unexpected code "+ response);
            }
            System.out.println("Conexión Establedida y Response ok");
            String responseBody = response.body().string();
            Gson gson = new Gson();
            //genera la estructura del Post.json en base a la estructura de la clase Post
            postListType = new TypeToken<List<User>>() {}.getType();
            //fromJson: transforma la respesta en formato json con su tipo (estructura)
            //y genera un arraylist de java (lista de obejtos)
            LUser = gson.fromJson(responseBody, userListType);
            for (User user : LUser) {
                System.out.println(user.getName());

            }
        }catch (IOException e){ //IOException captura errores HTTP
            e.printStackTrace(); //mensaje generico tipo "Ayuda SysAdmin"
        }
    }

    public static void cargarJson(){
        try {
            Gson gson = new Gson();
            FileReader filePost = new FileReader(ruta + "/post2.json");
            FileReader fileUser = new FileReader(ruta + "/user2.json");
            LPost = gson.fromJson(filePost, postListType);
            LUser = gson.fromJson(fileUser, userListType);
            filePost.close();
            fileUser.close();
            System.out.println("Cargado desde " + ruta);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void guardar() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonPost = gson.toJson(LPost);
            String jsonUser = gson.toJson(LUser);
            FileWriter filePost = new FileWriter(ruta + "/post2.json");
            FileWriter fileUser = new FileWriter(ruta + "/user2.json");
            filePost.write(jsonPost);
            fileUser.write(jsonUser);
            filePost.close();
            fileUser.close();
            System.out.println("Guardado en " + ruta);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void buscarPalabra() {
        /**tiene que sacar los posts que tienen esa palabra y el usuario que la ha escrito*/
        System.out.println("Introduce la palabra a buscar: ");
        String palabra = scanner.nextLine();
        for (Post post : LPost){

            if (post.getTitle().contains(palabra)){
                System.out.println("Post: " + post.getTitle());
                System.out.println("Usuario: " + post.getUser().getName());
            }
        }
    }

    public static void mostrarPostsdeUnUsuario(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del usuario: ");
        int userId = scanner.nextInt();
        for (User user : LUser) {
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

    public static void Menu(){
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- Menú de Empleados ---");
            System.out.println("1. Cargar los Post");
            System.out.println("2. Mostrar posts de un usuario");
            System.out.println("3. Cargar Datos");
            System.out.println("4. Usuario que hablan de");
            System.out.println("5. Guardar Datos");

            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {
                case 1:
                    CargarPost();
                    cargarUser();
                    relacionarPostsConUsuarios();
                    System.out.println("termino");
                    break;

                case 2:
                    mostrarPostsdeUnUsuario();
                    System.out.println("termino");
                    break;

                case 3:
                    cargarJson();
                    System.out.println("Cargando...");
                    break;
                case 4:
                    buscarPalabra();
                    break;
                case 5:
                    guardar();
                    System.out.println("Guardando...");
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
            }
        }
    }
}

