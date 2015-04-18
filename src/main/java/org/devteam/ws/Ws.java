package org.devteam.ws;

import dagger.Component;
import org.devteam.ws.user.UserWs;

import javax.inject.Singleton;

/**
 * Created by amanteaux on 18/04/15.
 */
@Component
@Singleton
public interface Ws {
    UserWs user();
}
