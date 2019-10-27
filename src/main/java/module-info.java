module mvnCryptMachine {
    requires javafx.fxml;
    requires javafx.controls;
    requires kernel;
    requires layout;
    requires commons.email;
    requires java.json;
    requires opencsv;
    requires java.xml;


    opens apps;
    opens apps.utils;
    opens apps.controllers;
}