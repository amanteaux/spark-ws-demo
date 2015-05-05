package org.devteam;

import org.devteam.web.DaggerWeb;

public class App {

    public static void main( String[] args ) {
    	DaggerWeb
    		.create()
    		.router()
    		.configureRoutes();
    }

}
