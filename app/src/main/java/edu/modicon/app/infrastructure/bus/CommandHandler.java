package edu.modicon.app.infrastructure.bus;

public interface CommandHandler<R, C extends Command<R>>{

    R handle(C command);

}
