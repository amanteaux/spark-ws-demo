package org.devteam.ws;

import dagger.Component;

import org.devteam.services.ServicesModule;
import org.devteam.ws.user.UserWs;

import javax.inject.Singleton;

/**
 * Created by amanteaux on 18/04/15.
 */
@Component(modules = ServicesModule.class)
@Singleton
public interface Ws {
    UserWs user();
}
