package org.devteam.web.ws;

import spark.Request;
import spark.Response;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SupervisionWs {

	@Inject
	public SupervisionWs() {
	}

	public ServerStatus serverStatus(Request request, Response response) {
		return new ServerStatus(true);
	}

	public static class ServerStatus {
		private final boolean isAlive;

		public ServerStatus(boolean isAlive) {
			this.isAlive = isAlive;
		}

		public boolean isAlive() {
			return isAlive;
		}
	}

}
