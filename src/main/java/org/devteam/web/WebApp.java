package org.devteam.web;

import javax.inject.Singleton;

import org.devteam.services.ServicesModule;

import dagger.Component;

@Component(modules = ServicesModule.class)
@Singleton
public interface WebApp {
	Router router();
}
