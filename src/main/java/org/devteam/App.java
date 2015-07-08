package org.devteam;

import org.devteam.web.DaggerWebApp;

public class App {

	public static void main(String[] args) {
		DaggerWebApp
				.create()
				.router()
				.start();
	}

}
