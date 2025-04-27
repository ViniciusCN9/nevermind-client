module com.nevermind.client {
    requires lombok;

    requires javafx.controls;
    requires javafx.fxml;

    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.web;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;

    opens com.nevermind.client to javafx.fxml, spring.core, spring.beans, spring.context;
    opens com.nevermind.client.controller to javafx.fxml, spring.core, spring.beans, spring.context;
    opens com.nevermind.client.service to spring.core, spring.beans, spring.context;
    opens com.nevermind.client.config to spring.core, spring.beans, spring.context;
    opens com.nevermind.client.config.exception to spring.beans, spring.context, spring.core;

    exports com.nevermind.client;
    exports com.nevermind.client.controller;
    exports com.nevermind.client.service;
    exports com.nevermind.client.model;
    exports com.nevermind.client.config.exception;
}