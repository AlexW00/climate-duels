package com.example.climateduels.database;

import com.example.climateduels.BuildConfig;

import java.sql.Connection;
import java.sql.DriverManager;

// Code adopted from: https://medium.com/cyber-explorer/how-to-connect-an-android-project-to-a-postgresql-database-663cb0f5ba19

public class Database {

    public Connection connection;

    private final String host = BuildConfig.HOST;
    private final String database = BuildConfig.DATABASE;
    private final int port = Integer.parseInt(BuildConfig.PORT);
    private final String user = BuildConfig.USER;
    private final String pass = BuildConfig.PASSWORD;
    private String url = "jdbc:postgresql://%s:%d/%s";

    private boolean status;

    public Database()
    {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
    }

    private void connect()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    System.out.println("connected:" + status);
                }
                catch (Exception e)
                {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }
    }
}