package org.example.api;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String website;
    private ArrayList<Post> posts = new ArrayList<>();

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public ArrayList<Post> getPosts() { return posts; }
    public void setPosts(ArrayList<Post> posts) { this.posts = posts;}
}

