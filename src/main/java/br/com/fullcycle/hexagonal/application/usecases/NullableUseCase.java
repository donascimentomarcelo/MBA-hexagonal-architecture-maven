package br.com.fullcycle.hexagonal.application.usecases;

public abstract class NullableUseCase<OUTPUT> {

    public abstract OUTPUT execute();
}
